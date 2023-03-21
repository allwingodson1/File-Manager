package servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.DbManager;

@WebServlet("/myapp/setFavourite")
public class Favourite extends HttpServlet {
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		String username = (String) req.getAttribute("username");
		boolean value = Boolean.valueOf(req.getParameter("value"));
		res.getWriter().write(DbManager.setFavourite(id,username,value));
	}
}
