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

import utils.DbManager;
import utils.Tools;

public class FileAuthorization extends HttpFilter {
	public void doFilter(ServletRequest req,ServletResponse res,FilterChain chain) throws IOException,ServletException {
		int id = Integer.parseInt(req.getParameter("id"));
		String username = (String) req.getAttribute("username");
		boolean authorization = false;
		try {
			PreparedStatement stmt = Tools.DB.prepareStatement("select * from file where id = ?"); 
			stmt.setInt(1, id);
			ResultSet set = stmt.executeQuery();
			if(set.next()) {
				authorization  = set.getString(5).equals((String)req.getAttribute("username"));
			}
			else {
				res.getWriter().write("File not found");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		if(authorization||DbManager.haveAccess(id,username)) {
			chain.doFilter(req, res);
		}
		else {
			res.getWriter().write("authorization failed");
		}
	}
}
