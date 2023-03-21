package servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

import utils.DbManager;

@WebServlet("/myapp/getsharedUser")
public class GetSharedUsers extends HttpServlet {
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		String username = ""+req.getAttribute("username");
		JSONArray arr = new JSONArray();
		DbManager.getSharedWith(id,username).forEach(n->{
			arr.add(n+"");
		});
		res.getWriter().write(arr.toString());
	}
}
