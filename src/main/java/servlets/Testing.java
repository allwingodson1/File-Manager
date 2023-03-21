package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.DbManager;

public class Testing extends HttpServlet{

	public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException {
		int id = Integer.parseInt(req.getParameter("id"));
		users.File Myfile = DbManager.getFile(id);
		if(Myfile.ContentType.startsWith("video")||Myfile.ContentType.startsWith("audio")) {
			System.out.println("Passed");
			req.setAttribute("data", Myfile);
			req.getRequestDispatcher("MyTest").forward(req, res);
			return;
		}
		res.setHeader("Content-Disposition", "inline");
		res.setContentType(Myfile.ContentType);
		File file = new File("/home/allwin-zstk292/myProjectFiles/"+id);
		FileInputStream in = new FileInputStream(file);
		ServletOutputStream out = res.getOutputStream();
		byte[] bytes = new byte[4096];
		int bytesRead ;
		while((bytesRead = in.read(bytes))!=-1) {
			out.write(bytes, 0, bytesRead);
		}
		in.close();
		out.close();
	}
}
