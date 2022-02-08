//Author: John
package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import model.Course;
import model.DatabaseManager;

/**
 * Servlet implementation class DeleteCourse
 */
public class DeleteCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		ObjectMapper mapper = new ObjectMapper();
		Course courseToDelete = mapper.readValue(request.getReader(), Course.class);
		DatabaseManager db = new DatabaseManager();
		db.connect();
		db.deleteCourse(courseToDelete);
		PrintWriter pw = response.getWriter();
		pw.print("1");
		db.disconect();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

}
