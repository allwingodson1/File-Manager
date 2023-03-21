package servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.DbManager;

@WebServlet("/myapp/delete")
public class Delete extends HttpServlet {
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		String username = (String)req.getAttribute("username");
		res.getWriter().write(DbManager.delete(id,username));
	}
}
