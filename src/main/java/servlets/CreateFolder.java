package servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import users.Folder;
import utils.DbManager;
import utils.Tools;

public class CreateFolder extends HttpServlet{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException {
		String folderName = req.getParameter("name");
		String username = (String)req.getAttribute("username");
		String session = (String) req.getAttribute("session");
		Folder folder = new Folder(++Tools.ID, folderName, new Date(), username, new Date(), username,false);
		DbManager.store(folder,session,username);
		res.getWriter().write(folder.getJSON().toString());
	}
}