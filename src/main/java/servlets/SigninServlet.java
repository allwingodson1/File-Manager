package servlets;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import users.User;
import utils.DbManager;

public class SigninServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		JSONObject resObj = new JSONObject();
		User user = DbManager.getUser(username);
		if(user == null) {
			resObj.put("StatusCode", 500);
			resObj.put("reason", "user not found");
		}
		else if(user.getPassword().equals(password)) {
			String sessionId = UUID.randomUUID().toString();
			DbManager.setSession(sessionId,username,user.getMyFolderId());
			Cookie cookie = new Cookie("SessionId",sessionId);
			res.addCookie(cookie);
			resObj.put("StatusCode", 200);
		}
		else {
			resObj.put("StatusCode", 500);
			resObj.put("reason", "incorrect password");
		}
		res.getWriter().write(resObj.toString());
	}
}
