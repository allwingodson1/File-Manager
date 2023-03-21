package filters;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;

import org.json.simple.JSONObject;

import utils.Tools;

public class SignupFilter extends HttpFilter {
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String number = req.getParameter("number");
		JSONObject resObj = new JSONObject();
		if (username.length() <= 2) {
			resObj.put("StatusCode", 500);
			resObj.put("errorMessage", "please enter a username of charecter atleast greater than 2");
			res.getWriter().write(resObj.toString());
		} else if (password.length() <= 7 && password.length() >= 25) {
			resObj.put("StatusCode", 500);
			resObj.put("errorMessage", "please enter a password with charecter between 7 and 25");
			res.getWriter().write(resObj.toString());
		} else {
			long num;
			try {
				num = Long.parseLong(number);
				if (("" + num).length() != 10) {
					resObj.put("StatusCode", 500);
					resObj.put("errorMessage", "number must contain 10 digits");
					res.getWriter().write(resObj.toString());
				} else {
					try {
						PreparedStatement st = Tools.DB.prepareStatement("select * from user where username = ?");
						st.setString(1, username);
						ResultSet set = st.executeQuery();
						if (set.next()) {
							resObj.put("StatusCode", 500);
							resObj.put("errorMessage", "User already exisists");
							res.getWriter().write(resObj.toString());
						} else {
							chain.doFilter(req, res);
						}
					} catch (SQLException e) {

					}
				}
			} catch (NumberFormatException e) {
				resObj.put("StatusCode", 500);
				resObj.put("errorMessage", "please enter a valid number");
				res.getWriter().write(resObj.toString());
				System.out.println(e.getMessage());
			}
		}
		
	}
}
