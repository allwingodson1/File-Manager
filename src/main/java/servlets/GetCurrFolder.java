package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import utils.DbManager;

public class GetCurrFolder extends HttpServlet {

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException {
		String session = (String) req.getAttribute("session");
		JSONObject obj = new JSONObject();
		JSONArray files = new JSONArray();
		JSONArray folders = new JSONArray();
		int id = DbManager.getCurrFolder(session);
		if(id == -1) {
			req.getRequestDispatcher("getShared").forward(req,res);
		}
		else if(id == -2) {
			req.getRequestDispatcher("getFavour").forward(req,res);
		}
		else if(id == -3) {
			System.out.println("kjdbshbdkjscdehbdej");
			req.getRequestDispatcher("getRecent").forward(req,res);
		}
		else {
			files.addAll(DbManager.getFiles(id).stream().map(n->n.getJSON()).toList());
			folders.addAll(DbManager.getFolders(id).stream().map(n->n.getJSON()).toList());
			obj.put("files", files);
			obj.put("folders", folders);
			res.getWriter().write(obj.toString());
		}
	}
}