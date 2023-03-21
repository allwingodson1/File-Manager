package utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import users.File;
import users.Folder;
import users.User;

public class DbManager {
	public static User getUser(String username) {
		PreparedStatement st;
		try {
			st = Tools.DB.prepareStatement("select * from user where username = ?");
			st.setString(1, username);
			ResultSet set = st.executeQuery();
			if(set.next()) {
				User user = new User(set.getString(1), set.getString(2), set.getString(3), set.getInt(4));
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<File> getFiles(int folderId){
		List<File> files = new ArrayList<>();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			PreparedStatement stmt = Tools.DB.prepareStatement("select file.* from childReference left join file on file.id = childId where childType = 'File' and ParentId = ?");
			stmt.setInt(1, folderId);
			ResultSet set = stmt.executeQuery();
			while(set.next()) {
				File file = new File(set.getInt(1), set.getString(2), df.parse(set.getString(3)), set.getString(4), (set.getString(5)),df.parse(set.getString(6)),set.getString(7),set.getString(8).equals("T"));
				files.add(file);
			}
		}
		catch(SQLException | ParseException e) {
			e.printStackTrace();
		}
		return files;
	}
	public static List<Folder> getFolders(int folderId){
		List<Folder> folders = new ArrayList<>();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			PreparedStatement stmt = Tools.DB.prepareStatement("select folder.* from childReference left join folder on folder.id = childId where childType = 'Folder' and ParentId = ?");
			stmt.setInt(1, folderId);
			ResultSet set = stmt.executeQuery();
			while(set.next()) {
				System.out.println(set.getString(1));
				System.out.println(set.getString(3));
				System.out.println(set.getString(5));
				Folder folder = new Folder(set.getInt(1), set.getString(2), df.parse(set.getString(3)), set.getString(4), df.parse(set.getString(5)),set.getString(6),set.getString(7).equals("T"));
				folders.add(folder);
			}
		}
		catch(SQLException | ParseException e) {
			e.printStackTrace();
		}
		return folders;
	}
	public static void store(File myFile,String session,String username) {
		// TODO Auto-generated method stub
		int currFolder = getCurrFolder(session);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		synchronized (Tools.DB) {
			try {
				PreparedStatement stmt = Tools.DB.prepareStatement("insert into file value(?,?,?,?,?,?,?,?)");
				stmt.setInt(1, myFile.id);
				stmt.setString(2, myFile.name);
				stmt.setString(3, df.format(myFile.dateOfCreation));
				stmt.setString(4, myFile.ContentType);
				stmt.setString(5, myFile.createdBy);
				stmt.setString(6, df.format(myFile.dateOfModification));
				stmt.setString(7, myFile.lastModifiedBy);
				stmt.setString(8, myFile.isFavourite?"T":"F");
				stmt.executeUpdate();
				stmt = Tools.DB.prepareStatement("insert into childReference value(?,?,?)");
				stmt.setInt(1, currFolder);
				stmt.setInt(2, myFile.id);
				stmt.setString(3, "File");
				stmt.executeUpdate();
				update(currFolder,username);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public static int getMyFolderId(String username) {
		try {
			PreparedStatement stmt = Tools.DB.prepareStatement("select myFolderId from user where username = ?");
			stmt.setString(1,username);
			ResultSet set = stmt.executeQuery();
			if(set.next()) {
				return set.getInt(1);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public static int getCurrFolder(String session) {
		try {
			PreparedStatement stmt = Tools.DB.prepareStatement("select lastFolder from sessions where SessionId = ?");
			stmt.setString(1, session);
			ResultSet set = stmt.executeQuery();
			if(set.next()) {
				return set.getInt(1);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public static int store(User user) {
		// TODO Auto-generated method stub
		try {
			synchronized (Tools.DB) {
				PreparedStatement stmt = Tools.DB.prepareStatement("insert into user value(?,?,?,?)");
				stmt.setString(1, user.getUsername());
				stmt.setString(2, user.getPassword());
				stmt.setString(3, user.getNumber());
				stmt.setInt(4, user.getMyFolderId());
				return stmt.executeUpdate();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	public static int setCurrFolder(int id,String session) {
		try {
			synchronized (Tools.DB) {
				PreparedStatement stmt = Tools.DB.prepareStatement("update sessions set lastFolder = ? where SessionId = ?");
				stmt.setInt(1, id);
				stmt.setString(2, session);
				return stmt.executeUpdate();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	public static int store(Folder folder) {
		// TODO Auto-generated method stub
		try {
			synchronized (Tools.DB) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				PreparedStatement stmt = Tools.DB.prepareStatement("insert into folder value(?,?,?,?,?,?,?)");
				stmt.setInt(1, folder.id);
				stmt.setString(2,folder.name);
				stmt.setString(3, df.format(folder.dateOfCreation));
				stmt.setString(4,folder.createdBy);
				stmt.setString(5, df.format(folder.dateOfModification));
				stmt.setString(6, folder.lastModifiedBy);
				stmt.setString(7, folder.isFavourite?"T":"F");
				return stmt.executeUpdate();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	public static void store(Folder folder, String session,String username) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			synchronized (Tools.DB) {
				PreparedStatement stmt = Tools.DB.prepareStatement("insert into folder value(?,?,?,?,?,?,?)");
				stmt.setInt(1, folder.id);
				stmt.setString(2, folder.name);
				stmt.setString(3, df.format(folder.dateOfCreation));
				stmt.setString(4, folder.createdBy);
				stmt.setString(5, df.format(folder.dateOfModification));
				stmt.setString(6, folder.lastModifiedBy);
				stmt.setString(7, folder.isFavourite?"T":"F");
				stmt.executeUpdate();
				stmt = Tools.DB.prepareStatement("insert into childReference value(?,?,?)");
				int curr = getCurrFolder(session);
				stmt.setInt(1, curr);
				stmt.setInt(2, folder.id);
				stmt.setString(3, "Folder");
				stmt.executeUpdate();
				update(curr,username);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void update(int folder, String username) {
		try {
			PreparedStatement stmt = Tools.DB.prepareStatement("update folder set dateOfModification = now(), lastModifiedBy = ? where id = ?");
			stmt.setString(1, username);
			stmt.setInt(2, folder);
			stmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void update(File file) {
		try {
			PreparedStatement stmt = Tools.DB.prepareStatement("update file set dateOfModification = now() where id = ?");
			stmt.setInt(1, file.id);
			stmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static File getFile(int id) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		File file = null;
		try {
			PreparedStatement stmt = Tools.DB.prepareStatement("select * from file where id = ?");
			stmt.setInt(1, id);
			ResultSet set = stmt.executeQuery();
			if(set.next()) {
				file = new File(id, set.getString(2), df.parse(set.getString(3)), set.getString(4), set.getString(5), df.parse(set.getString(6)), set.getString(7),set.getString(8).equals("T"));
			}
		}
		catch(SQLException | ParseException e) {
			e.printStackTrace();
		}
		return file;
	}
	public static void setId() {
		// TODO Auto-generated method stub
		Statement stmt;
		try {
			stmt = Tools.DB.createStatement();
			ResultSet set = stmt.executeQuery("select max(id) from file");
			ResultSet set1 = Tools.DB.createStatement().executeQuery("select max(id) from folder");
			int a = 0,b = 0;
			if(set.next()) {
				a = set.getInt(1);
			}
			if(set1.next()) {
				b = set1.getInt(1);
			}
			Tools.ID = Math.max(a, b);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void setSession(String sessionId, String username,int lastFolder) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement stmt = Tools.DB.prepareStatement("insert into sessions value(?,?,?)");
			stmt.setString(1, sessionId);
			stmt.setString(2, username);
			stmt.setInt(3, lastFolder);
			stmt.executeUpdate();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static List<File> getFiles(int id, String by) {
		// TODO Auto-generated method stub

		List<File> files = new ArrayList<>();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			by = by.equals("Name")?"name":by.equals("Created Date")?"dateOfCreation":"dateOfModification";
			PreparedStatement stmt = Tools.DB.prepareStatement("select file.* from childReference left join file on file.id = childId where childType = 'File' and ParentId = ? order by "+by);
			stmt.setInt(1, id);
			ResultSet set = stmt.executeQuery();
			while(set.next()) {
				File file = new File(set.getInt(1), set.getString(2), df.parse(set.getString(3)), set.getString(4), (set.getString(5)),df.parse(set.getString(6)),set.getString(7),set.getString(8).equals("T"));
				files.add(file);
			}
		}
		catch(SQLException | ParseException e) {
			e.printStackTrace();
		}
		return files;
	
	}
	public static List<Folder> getFolders(int folderId,String by){
		List<Folder> folders = new ArrayList<>();
		try {
			by = by.equals("Name")?"name":by.equals("Created Date")?"dateOfCreation":"dateOfModification";
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			PreparedStatement stmt = Tools.DB.prepareStatement("select folder.* from childReference left join folder on folder.id = childId where childType = 'Folder' and ParentId = ? order by "+by);
			stmt.setInt(1, folderId);
			ResultSet set = stmt.executeQuery();
			while(set.next()) {
				Folder folder = new Folder(set.getInt(1), set.getString(2), df.parse(set.getString(3)), set.getString(4), df.parse(set.getString(5)),set.getString(6),set.getString(8).equals("T"));
				folders.add(folder);
			}
		}
		catch(SQLException | ParseException e) {
			e.printStackTrace();
		}
		return folders;
	}
	public static List<String> getUsersLike(String name,String username) {
		// TODO Auto-generated method stub
		List<String> users = new ArrayList<>();
		try {
			PreparedStatement stmt = Tools.DB.prepareStatement("select * from user where username like ? and username != ?");
			stmt.setString(1,"%"+name+"%");
			stmt.setString(2, username);
			ResultSet set = stmt.executeQuery();
			while(set.next()) {
				users.add(set.getString(1));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	public static Object getFileOrFolder(int id) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			PreparedStatement stmt = Tools.DB.prepareStatement("select * from folder where id = ?");
			stmt.setInt(1, id);
			ResultSet set = stmt.executeQuery();
			if(set.next()) {
				return new Folder(set.getInt(1), set.getString(2), df.parse(set.getString(3)), set.getString(4), df.parse(set.getString(5)),set.getString(6),set.getString(7).equals("T"));
			}
			else {
				stmt = Tools.DB.prepareStatement("select * from file where id = ?");
				stmt.setInt(1, id);
				set = stmt.executeQuery();	
				if(set.next()) {
					return new File(set.getInt(1), set.getString(2), df.parse(set.getString(3)), set.getString(4), (set.getString(5)),df.parse(set.getString(6)),set.getString(7),set.getString(8).equals("T"));
				}
			}
		}
		catch(SQLException | ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
//	public static int makeAcopy(int folderId) {
//		// TODO Auto-generated method stub
//		
//	}
	public static List<Integer> getSharedId(String username){
		List<Integer> sharedContent = new ArrayList<>();
		try {
			PreparedStatement stmt = Tools.DB.prepareStatement("select sharedContent from sharedFolder where sharedTo = ?");
			stmt.setString(1, username);
			ResultSet set = stmt.executeQuery();
			while(set.next()) {
				sharedContent.add(set.getInt(1));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return sharedContent;
	}
	public static List<Object> getshared(String username){
		List sharedContent = new ArrayList<>();
		try {
			PreparedStatement stmt = Tools.DB.prepareStatement("select sharedContent from sharedFolder where sharedTo = ?");
			stmt.setString(1, username);
			ResultSet set = stmt.executeQuery();
			while(set.next()) {
				sharedContent.add(getFileOrFolder(set.getInt(1)));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return sharedContent;
	}
	public static void addInShare(String owner,String username, int folderId) {
		// TODO Auto-generated method stub
		try {
			synchronized (Tools.DB) {
				PreparedStatement stmt = Tools.DB.prepareStatement("insert into sharedFolder value(?,?,?)");
				stmt.setString(1, username);
				stmt.setString(2, owner);
				stmt.setInt(3, folderId);
				stmt.executeUpdate();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static int getParent(int id) {
		try {
			PreparedStatement stmt = Tools.DB.prepareStatement("select parentId from childReference where childId = ?");
			stmt.setInt(1, id);
			ResultSet set = stmt.executeQuery(); 
			if(set.next()) {
				return set.getInt(1);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public static boolean haveAccess(int id,String username) {
		// TODO Auto-generated method stub
		List<Integer> temp = getSharedId(username);
		while(id!=0) {
			if(temp.contains(id))return true;
			id = getParent(id);
		}
		return false;
	}
	public static void addInRecent(int id,String username) {
		try {
			PreparedStatement stmt = Tools.DB.prepareStatement("select * from recentlyOpened where id = ? and username = ?");
			stmt.setInt(1, id);
			stmt.setString(2, username);
			ResultSet set = stmt.executeQuery();
			if(set.next()) {
				synchronized (Tools.DB) {
					stmt = Tools.DB.prepareStatement("update recentlyOpened set time = now() where id = ? and username = ?");
					stmt.setInt(1, id);
					stmt.setString(2, username);
					stmt.executeUpdate();
				}
			}
			else {
				synchronized (Tools.DB) {
					stmt = Tools.DB.prepareStatement("insert into recentlyOpened value(?,?,now())");
					stmt.setString(1, username);
					stmt.setInt(2, id);
					stmt.executeUpdate();
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static String delete(int id, String username) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement stmt = Tools.DB.prepareStatement("delete from file where id = ? and createdBy = ?");
			stmt.setInt(1, id);
			stmt.setString(2, username);
			if(stmt.executeUpdate()!=0) {
				stmt = Tools.DB.prepareStatement("delete from sharedFolder where sharedContent = ?");
				stmt.setInt(1, id);
				stmt.executeUpdate();
				stmt = Tools.DB.prepareStatement("delete from childReference where parentId = ? or childId = ?");
				stmt.setInt(1, id);
				stmt.setInt(2, id);
				stmt.executeUpdate();
				java.io.File temp = new java.io.File("/home/allwin-zstk292/myProjectFiles/"+id);
				temp.delete();System.out.println("a");
				return "sucessfully deleted";
			}
			else {
				stmt = Tools.DB.prepareStatement("delete from folder where id = ? and createdBy = ?");
				stmt.setInt(1, id);
				stmt.setString(2, username);
				if(stmt.executeUpdate()!=0) {
					stmt = Tools.DB.prepareStatement("delete from sharedFolder where sharedContent = ?");
					stmt.setInt(1, id);
					stmt.executeUpdate();
					stmt = Tools.DB.prepareStatement("delete from childReference where parentId = ? or childId = ?");
					stmt.setInt(1, id);
					stmt.setInt(2, id);
					stmt.executeUpdate();
					return "sucessfully deleted";
				}
				else {
					return "you don't have access to delete this file or folder";
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			return "error occured";
		}
	}
	public static String rename(int id, String username,String value) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement stmt = Tools.DB.prepareStatement("update file set name = ? where id = ? and createdBy = ?");
			stmt.setString(1, value);
			stmt.setInt(2, id);
			stmt.setString(3, username);
			if(stmt.executeUpdate()!=0) {
				return "sucessfully renamed";
			}
			else {
				stmt = Tools.DB.prepareStatement("update folder set name = ? where id = ? and createdBy = ?");
				stmt.setString(1, value);
				stmt.setInt(2, id);
				stmt.setString(3, username);
				if(stmt.executeUpdate()!=0) {
					return "sucessfully renamed";
				}
				else {
					return "you don't have access to this file or folder";
				}
			}
		}
		catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return "error occured";
		}
	}
	public static String setFavourite(int id, String username, boolean value) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement stmt = Tools.DB.prepareStatement("update file set isFavourite = ? where id = ? and createdBy = ?");
			stmt.setString(1, value?"T":"F");
			stmt.setInt(2, id);
			stmt.setString(3, username);
			if(stmt.executeUpdate()!=0) {
				return "sucessfully changed";
			}
			else {
				stmt = Tools.DB.prepareStatement("update folder set isFavourite = ? where id = ? and createdBy = ?");
				stmt.setString(1, value?"T":"F");
				stmt.setInt(2, id);
				stmt.setString(3, username);
				if(stmt.executeUpdate()!=0) {
					return "sucessfully changed";
				}
				else {
					return "you don't have access to this file or folder";
				}
			}
		}
		catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return "error occured";
		}
	
	}
	public static List<Folder> getFavouriteFolders(String username) {
		// TODO Auto-generated method stub
		List<Folder> files = new ArrayList<>();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			PreparedStatement stmt = Tools.DB.prepareStatement("select * from folder where createdBy = ? and isFavourite = ?");
			stmt.setString(1, username);
			stmt.setString(2, "T");
			ResultSet set = stmt.executeQuery();
			while(set.next()) {
				files.add(new Folder(set.getInt(1), set.getString(2), df.parse(set.getString(3)), set.getString(4), df.parse(set.getString(5)),set.getString(6),set.getString(7).equals("T")));
			}
		}
		catch(SQLException | ParseException e) {
			e.printStackTrace();
		}
		return files;
	}
	public static List<File> getFavouriteFiles(String username) {
		// TODO Auto-generated method stub
		List<File> files = new ArrayList<>();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			PreparedStatement stmt = Tools.DB.prepareStatement("select * from file where createdBy = ? and isFavourite = ?");
			stmt.setString(1, username);
			stmt.setString(2, "T");
			ResultSet set = stmt.executeQuery();
			while(set.next()) {
				files.add(new File(set.getInt(1), set.getString(2), df.parse(set.getString(3)), set.getString(4), (set.getString(5)),df.parse(set.getString(6)),set.getString(7),set.getString(8).equals("T")));
			}
		}
		catch(SQLException | ParseException e) {
			e.printStackTrace();
		}
		return files;
	}
	public static List<File> getRecentFiles(String username) {
		// TODO Auto-generated method stub
		List<File> files = new ArrayList<>();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			PreparedStatement stmt = Tools.DB.prepareStatement("select file.* from recentlyOpened left join file on file.id = recentlyOpened.id where username = ? order by time desc");
			stmt.setString(1, username);
			ResultSet set = stmt.executeQuery();
			while(set.next()) {
				files.add(new File(set.getInt(1), set.getString(2), df.parse(set.getString(3)), set.getString(4), (set.getString(5)),df.parse(set.getString(6)),set.getString(7),set.getString(8).equals("T")));
			}
		}
		catch(SQLException | ParseException e) {
			e.printStackTrace();
		}
		return files;
	}
	public static List<String> getSharedWith(int id, String username) {
		// TODO Auto-generated method stub
		List<String> users = new ArrayList<>();
		try {
			PreparedStatement stmt = Tools.DB.prepareStatement("select * from sharedFolder where sharedBy = ? and sharedContent = ?");
			stmt.setString(1, username);
			stmt.setInt(2, id);
			ResultSet set = stmt.executeQuery();
			while(set.next()) {
				users.add(set.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	public static List<File> getSharedFiles(String username) {
		// TODO Auto-generated method stub
		List<File> files = new ArrayList<>();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			PreparedStatement stmt = Tools.DB.prepareStatement("select file.* from sharedFolder left join file on sharedContent = id where sharedBy = ? and id is not null");
			stmt.setString(1, username);
			ResultSet set = stmt.executeQuery();
			while(set.next()) {
				files.add(new File(set.getInt(1), set.getString(2), df.parse(set.getString(3)), set.getString(4), (set.getString(5)),df.parse(set.getString(6)),set.getString(7),set.getString(8).equals("T")));
			}
		}
		catch(SQLException | ParseException e) {
			e.printStackTrace();
		}
		return files;
	}
	public static List<Folder> getSharedFolders(String username) {
		List<Folder> files = new ArrayList<>();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			PreparedStatement stmt = Tools.DB.prepareStatement("select folder.* from sharedFolder left join folder on sharedContent = id where sharedBy = ? and id is not null");
			stmt.setString(1, username);
			ResultSet set = stmt.executeQuery();
			while(set.next()) {
				files.add(new Folder(set.getInt(1), set.getString(2), df.parse(set.getString(3)), set.getString(4), df.parse(set.getString(5)),set.getString(6),set.getString(7).equals("T")));
			}
		}
		catch(SQLException | ParseException e) {
			e.printStackTrace();
		}
		return files;
	}
}