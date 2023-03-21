package servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import utils.DbManager;
import utils.Tools;

@MultipartConfig(
		maxFileSize = (1024*1024*1024*2)-5
		)
public class StoreFile extends HttpServlet{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException, ServletException {
		Part part = req.getPart("file");
		String contentType = part.getContentType();
		InputStream in = part.getInputStream();
		String session = (String) req.getAttribute("session");
		String username = (String) req.getAttribute("username");
		String header = part.getHeader("Content-Disposition");
		String filename = header.substring(header.lastIndexOf("\"",header.length()-2)+1, header.lastIndexOf("\""));
		String path = "/home/allwin-zstk292/myProjectFiles/";
		long fileSize = Long.parseLong(req.getHeader("Content-Length"));
		int temp = ++Tools.ID;
		users.File myFile = new users.File(temp, filename, new Date(), contentType,username, new Date(), username,false);
		DbManager.store(myFile,session,username);
		System.out.println(fileSize);
		path += temp;
		File file = new File(path);
		FileOutputStream out = new FileOutputStream(file);
		int bytesRead = 0;
		byte[] arr = new byte[4096];
		long totalBytesRead = 0;
		while((bytesRead = in.read(arr))!=-1) {
			out.write(arr, 0, bytesRead);
			totalBytesRead += bytesRead;
            int percentSent = (int) ((totalBytesRead / (float) fileSize) * 100);
            res.getWriter().write("");
            res.flushBuffer();
		}
		out.flush();
		out.close();
		res.getWriter().write(myFile.getJSON().toString());
	}
}
