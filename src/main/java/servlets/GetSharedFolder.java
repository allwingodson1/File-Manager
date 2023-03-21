package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import users.File;
import users.Folder;
import utils.DbManager;

@WebServlet("/myapp/getShared")
public class GetSharedFolder extends HttpServlet {
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException {
		String username = (String) req.getAttribute("username");
		List sharedContent = DbManager.getshared(username);
		JSONArray a = new JSONArray(),b = new JSONArray();
		sharedContent.forEach(
					n->{
						if(n instanceof File) {
							a.add(((File)n).getJSON());
						}
						else {
							b.add(((Folder)n).getJSON());
						}
					}
				);
		JSONObject response = new JSONObject();
		response.put("files", a);
		response.put("folders", b);
		res.getWriter().write(response.toString());
	}
}
