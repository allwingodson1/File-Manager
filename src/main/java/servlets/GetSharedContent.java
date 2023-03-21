package servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import utils.DbManager;

@WebServlet("/myapp/GetSharedContent")
public class GetSharedContent extends HttpServlet{
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException {
		String username = (String) req.getAttribute("username");
		JSONArray arr1 = new JSONArray();
		JSONArray arr2 = new JSONArray();
		DbManager.getSharedFiles(username).forEach(n->{
			arr1.add(n.getJSON());
		});
		DbManager.getSharedFolders(username).forEach(n->{
			arr2.add(n.getJSON());
		});
		JSONObject obj = new JSONObject();
		obj.put("files", arr1);
		obj.put("folders", arr2);
		res.getWriter().write(obj.toString());
	}
}
