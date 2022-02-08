//Author: Therese

package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import model.DatabaseManager;
import model.Teacher;

/**
 * Servlet implementation class EditTeacher
 */
@WebServlet("/EditTeacher")
public class UpdateTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html");
		ObjectMapper mapper = new ObjectMapper();
		Teacher teacherToEdit = mapper.readValue(req.getReader(), Teacher.class);
		
		DatabaseManager db = new DatabaseManager();
		db.connect();
		db.updateTeacher(teacherToEdit.getEmail(), teacherToEdit.getName(), teacherToEdit.getSignature(), 
				teacherToEdit.getHistory(), teacherToEdit.getDevPlan(), teacherToEdit.getCourseOff(),
				teacherToEdit.getUsername(), teacherToEdit.getPassword());
		db.disconect();
		
		PrintWriter pw = res.getWriter();
		pw.print("1");
	}

}
