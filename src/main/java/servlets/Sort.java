package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import utils.DbManager;

@WebServlet("/myapp/Sort")
public class Sort extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = (String) req.getAttribute("username");
		String session = (String) req.getAttribute("session");
		JSONObject obj = new JSONObject();
		JSONArray files = new JSONArray();
		JSONArray folders = new JSONArray();
		int id = DbManager.getCurrFolder(session);
		files.addAll(DbManager.getFiles(id,req.getParameter("by")).stream().map(n->n.getJSON()).toList());
		folders.addAll(DbManager.getFolders(id,req.getParameter("by")).stream().map(n->n.getJSON()).toList());
		obj.put("files", files);
		obj.put("folders", folders);
		res.getWriter().write(obj.toString());	
	}

}
