package filters;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;

import utils.Tools;

public class Authentication extends HttpFilter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) req;
		JSONObject obj = new JSONObject();
		Cookie[] cookies = httpReq.getCookies();
		boolean authenticated = false;
		if (cookies != null)
			for (Cookie i : cookies) {
				if (i.getName().equals("SessionId")) {
					try {
						PreparedStatement st = Tools.DB.prepareStatement("select * from sessions where SessionId = ?");
						st.setString(1, i.getValue());
						ResultSet set = st.executeQuery();
						if (set.next()) {
							authenticated = !authenticated;
							req.setAttribute("username", set.getString(2));
							req.setAttribute("session", set.getString(1));
							chain.doFilter(req, res);
						}
					} catch (SQLException e) {
						System.out.println(e.getMessage());
					}
					break;
				}
			}
		if (!authenticated) {
			obj.put("StatusCode", 500);
			res.getWriter().write(obj.toString());
		}
	}
}
