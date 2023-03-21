package users;

public class User {
	private String username;
	private String password;
	private String number;
	private int myFolderId;
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getNumber() {
		return number;
	}
	public int getMyFolderId() {
		return myFolderId;
	}
	public User(String username, String password, String number, int myFolderId) {
		this.username = username;
		this.password = password;
		this.number = number;
		this.myFolderId = myFolderId;
	}
}