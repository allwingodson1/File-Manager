package servlets;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.Tools;

public class GetFileText extends HttpServlet{
	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		File file = new File("/home/allwin-zstk292/myProjectFiles/"+id);
		res.getWriter().write(Tools.getTextOfAFile(file));
	}
}
