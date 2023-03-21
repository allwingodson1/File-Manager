package servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.DbManager;

@WebServlet("/myapp/openedFile")
public class OpenFile extends HttpServlet {
	public void doGet(HttpServletRequest req,HttpServletResponse res) {
		int id = Integer.parseInt(req.getParameter("id"));
		String username = (String) req.getAttribute("username");
		DbManager.addInRecent(id, username);
	}
}
