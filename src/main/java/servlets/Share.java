package servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import utils.DbManager;

@WebServlet("/myapp/share")
public class Share extends HttpServlet {
	public void doPost(HttpServletRequest req,HttpServletResponse res) {
		int folderId = Integer.parseInt(req.getParameter("id"));
		String username = (String) req.getAttribute("username");
		try {
			JSONArray arr = (JSONArray) new JSONParser().parse(req.getParameter("users"));
			arr.forEach(
					n->{
						DbManager.addInShare(username,(String)n,folderId);
					}
					);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
