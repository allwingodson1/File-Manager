package servlets;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import users.Folder;
import users.User;
import utils.DbManager;
import utils.Tools;

public class SignupServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String number = req.getParameter("number");
		JSONObject resObj = new JSONObject();
		Folder folder = new Folder(++Tools.ID, "My Folder", new Date(), username, new Date(), username,false);
		User user = new User(username, password, number, folder.id);
		DbManager.store(user);
		DbManager.store(folder);
		resObj.put("StatusCode", 200);
		String sessionId = UUID.randomUUID().toString();
		DbManager.setSession(sessionId,username,folder.id);
		Cookie cookie = new Cookie("SessionId",sessionId);
		res.addCookie(cookie);
		res.getWriter().write(resObj.toString());
	}
}