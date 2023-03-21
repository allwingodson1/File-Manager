package servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import utils.DbManager;

@WebServlet("/myapp/user")
public class SearchUser extends HttpServlet {
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException {
		String name = req.getParameter("name");
		String username = (String) req.getAttribute("username");
		JSONArray arr = new JSONArray();
		arr.addAll(DbManager.getUsersLike(name,username));
		res.getWriter().write(arr.toString());
	}
}
