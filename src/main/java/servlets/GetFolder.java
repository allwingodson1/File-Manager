package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import utils.DbManager;

public class GetFolder extends HttpServlet {
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		JSONObject resObj = new JSONObject();
		var files = new JSONArray();
		var folders = new JSONArray();
		files.addAll(DbManager.getFiles(id).stream().map(n->n.getJSON()).toList());
		folders.addAll(DbManager.getFolders(id).stream().map(n->n.getJSON()).toList());
		resObj.put("files", files);
		resObj.put("folders", folders);
		res.getWriter().write(resObj.toString());
	}
}
