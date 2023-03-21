package servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import utils.DbManager;

@WebServlet("/myapp/getRecent")
public class GetRecentFiles extends HttpServlet {
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException {
		String username = (String)req.getAttribute("username");
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		JSONArray arr1 = new JSONArray();
		DbManager.getRecentFiles(username).forEach(n->arr.add(n.getJSON()));
		obj.put("files", arr);
		obj.put("folders", arr1);
		res.getWriter().write(obj.toString());
	}
}
