package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.DbManager;

public class SetCurrFolder extends HttpServlet {
	public void doGet(HttpServletRequest req,HttpServletResponse res) {
		int id = Integer.parseInt(req.getParameter("id"));
		if(id == 0) {
			id = DbManager.getMyFolderId((String)req.getAttribute("username"));
		}
		String session = (String) req.getAttribute("session");
		DbManager.setCurrFolder(id, session);
	}
}