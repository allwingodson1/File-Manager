package servlets;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MyTest")
public class MyTest extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String rangeHeader = request.getHeader("Range");
	    Integer id;
	    users.File file;
	    try {
	    	file = (users.File)request.getAttribute("data");
	    	id = Integer.parseInt(request.getParameter("id"));
	    }
	    catch(Exception e) {
	    	return;
	    }
	    long fileSize = new File("/home/allwin-zstk292/myProjectFiles/"+id).length();
	    long start = 0,end = fileSize -1;
	    String[] parts = null;
	    if(rangeHeader != null) {
	    	parts = rangeHeader.substring(6).split("-");
	    }
	    if(parts!=null) {
	    	try {
	    		if(parts.length == 2) {
		    		start = Long.parseLong(parts[0]);
		    		end = Long.parseLong(parts[1]);
		    	}
		    	else if(parts.length == 1) {
		    		start = Long.parseLong(parts[0]);
		    	}
		    	else {
		    		serveFile(request, response, start, end, file);
		    		return;
		    	}
	    	}
	    	catch(Exception e) {
	    		serveFile(request, response, start, end, file);
	    		return;
	    	}
	    }
	    else {
	    	serveFile(request, response, start, end, file);
	    	return;
	    }
	    if (start >= fileSize || end >= fileSize || start > end) {
	        response.setStatus(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
	        response.setHeader("Content-Range", "bytes */" + fileSize);
	    } else {
	        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
	        response.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + fileSize);
	        serveFile(request, response, start, end,file);
	    }
	}

	private void serveFile(HttpServletRequest request, HttpServletResponse response, long start, long end,users.File myFile) throws ServletException, IOException {
	    try (RandomAccessFile file = new RandomAccessFile("/home/allwin-zstk292/myProjectFiles/"+myFile.id, "r")) {
	        long fileSize = file.length();
	        if (end >= fileSize) {
	            end = fileSize - 1;
	        }
	        long length = end - start + 1;
	        byte[] buffer = new byte[4096];
	        response.setHeader("Accept-Ranges", "bytes");
	        response.setHeader("Content-Type", myFile.ContentType);
	        response.setHeader("Content-Length", length+"");
	        response.setHeader("Content-Disposition", "inline");
	        file.seek(start);
	        int read;
	        while (length > 0 && (read = file.read(buffer, 0, (int) Math.min(length, buffer.length))) != -1) {
	            response.getOutputStream().write(buffer, 0, read);
	            length -= read;
	        }
	    }
}
}

