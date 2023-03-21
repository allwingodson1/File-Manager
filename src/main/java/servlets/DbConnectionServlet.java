package servlets;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;

import utils.DbManager;
import utils.Tools;

public class DbConnectionServlet extends HttpServlet {
	public void init() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Tools.DB = DriverManager.getConnection("jdbc:mysql://localhost:3306/MyProjectDbAttempt1", "Allwin",
					"allwin@123");
			DbManager.setId();
			System.out.println(Tools.ID);
			System.out.println("Connected");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
