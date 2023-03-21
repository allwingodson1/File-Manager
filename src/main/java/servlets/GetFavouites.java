package servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import utils.DbManager;

@WebServlet("/myapp/getFavour")
public class GetFavouites extends HttpServlet{
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException {
		String username = (String) req.getAttribute("username");
		JSONObject json = new JSONObject();
		JSONArray arr = new JSONArray();
		DbManager.getFavouriteFiles(username).forEach(n->arr.add(n.getJSON()));
		json.put("files", arr);
		JSONArray arr1 = new JSONArray();
		DbManager.getFavouriteFolders(username).forEach(n->arr1.add(n.getJSON()));
		json.put("folders", arr1);
		res.getWriter().write(json.toString());
	}
}
