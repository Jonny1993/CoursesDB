//Author: Vagif / John / Therese

package model;


import static com.mongodb.client.model.Filters.regex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import model.Teacher.TeacherCourseOffering;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vagif
 */
public class DatabaseManager {
	MongoClient mongoClient; 
	MongoDatabase dataBase;
	MongoCollection<Document> courses, programs, teachers, courseInfo, courseOfferings,collections;


	public boolean connect() throws MongoException
	{
		mongoClient = new MongoClient(new MongoClientURI("mongodb://dbAdmin:HI1039GRUPPK@mthdb-shard-00-02-vzk6p.mongodb.net:27017,mthdb-shard-00-00-vzk6p.mongodb.net:27017,mthdb-shard-00-01-vzk6p.mongodb.net:27017/admin?ssl=true&replicaSet=MTHDB-shard-0&connectTimeoutMS=10000&authSource=admin&authMechanism=SCRAM-SHA-1&3t.uriVersion=3&3t.connection.name=MTHDB-shard-0&3t.databases=admin,test"));
		dataBase = mongoClient.getDatabase("UnivDB");
		courses = dataBase.getCollection("Courses");
		programs = dataBase.getCollection("Programs");
		courseInfo = dataBase.getCollection("Course Info");
		courseOfferings = dataBase.getCollection("Course Offerings");
		teachers = dataBase.getCollection("Teachers");
		return true;

	}

	public boolean connect(String usr, String pw) throws MongoException
	{
		mongoClient = new MongoClient(new MongoClientURI("mongodb://"+usr+":"+ pw+"@mthdb-shard-00-02-vzk6p.mongodb.net:27017,mthdb-shard-00-00-vzk6p.mongodb.net:27017,mthdb-shard-00-01-vzk6p.mongodb.net:27017/admin?ssl=true&replicaSet=MTHDB-shard-0&connectTimeoutMS=10000&authSource=admin&authMechanism=SCRAM-SHA-1&3t.uriVersion=3&3t.connection.name=MTHDB-shard-0&3t.databases=admin,test"));
		dataBase = mongoClient.getDatabase("UnivDB");
		courses = dataBase.getCollection("Courses");
		programs = dataBase.getCollection("Programs");
		courseInfo = dataBase.getCollection("Course Info");
		courseOfferings = dataBase.getCollection("Course Offerings");
		teachers = dataBase.getCollection("Teachers");
		return true;

	}

	public boolean disconect() throws MongoException
	{
		mongoClient.close();
		return true;
	}

	public Session findSession(String id) throws MongoException
	{
		connect();
		MongoCollection<Document> sessions = dataBase.getCollection("Sessions");

		MongoCursor<Document> cursor = null;  
		try
		{
			cursor = sessions.find(regex("id", id)).iterator(); 
			if(cursor.hasNext())
			{ 
				Document sessionDoc =  cursor.next();
				Session session = new Session(sessionDoc.getString("id") ,sessionDoc.getString("user") ,sessionDoc.getString("pw"));

				//Renew session
				//sessionDoc.replace("time",new Date());  //Doesn't work yet
				
				
				
				BasicDBObject newDoc = new BasicDBObject();
				newDoc.append("$set", new BasicDBObject()
						.append("time", new Date()));

				BasicDBObject searchQuery = new BasicDBObject().append("id", id);

				dataBase.getCollection("Sessions").updateOne(searchQuery, newDoc);
				
				
				

				return session;
			}
		}
		finally 
		{
			try
			{
				disconect();
				cursor.close(); 
			}
			catch(MongoException e)
			{
				throw e;
			}

		}
		return null;
	}
	public boolean correctLogIn(String providedUsername, String providedPassword) throws MongoException
	{
		connect();
		MongoCollection<Document> users = dataBase.getCollection("Teachers");

		MongoCursor<Document> cursor = null;  
		try
		{
			cursor = users.find(regex("Username", providedUsername)).iterator(); 
			if(cursor.hasNext())
			{ 

				Document userDoc =  cursor.next();

				if(providedPassword.equals(userDoc.getString("Password")))	//Password kan koverteras till hash om vi har tid
				{
					return true;
				}
			}
		}
		finally 
		{
			try
			{
				disconect();
				if(cursor!=null){
					cursor.close(); 
				}
			}
			catch(MongoException e)
			{
				throw e;
			}

		}
		return false;
	}

	public boolean addSession(String id, String username, String password) throws IOException, MongoException {
		this.connect();
		Document newSession = new Document();
		newSession.put("id", id);
		newSession.put("user", username);
		newSession.put("pw", password);
		newSession.put("time", new Date());
		dataBase.getCollection("Sessions").insertOne(newSession);
		disconect();
		return true;

	}

	public boolean deleteSession(String id) {
		Document sessionToBeDeleted = new Document().append("id", id);
		dataBase.getCollection("Sessions").deleteOne(sessionToBeDeleted);
		return true;
	}
	
	

	public boolean addProgram(Program newProgram) throws IOException, MongoException{
		Document newProgDoc = new Document();
		newProgDoc.put("Code", newProgram.getCode());
		newProgDoc.put("Name", newProgram.getName());
		newProgDoc.put("Branch", newProgram.getBranch());
		newProgDoc.put("Points", newProgram.getPoints());
		newProgDoc.put("Program co-ordinator", newProgram.getCoOrd());
		dataBase.getCollection("Programs").insertOne(newProgDoc);
		return true;
	}

	
	
	public boolean addCourse(Course newCourse) throws IOException, MongoException {
		connect();
		Document newCourseDoc = new Document();
		newCourseDoc.put("Code", newCourse.getCode());
		newCourseDoc.put("English Course Name", newCourse.getEnName());
		newCourseDoc.put("Swedish Course Name", newCourse.getSwName());
		newCourseDoc.put("Examiner Text", newCourse.getExaminer());
		newCourseDoc.put("Course Progress", newCourse.getProgress());
		newCourseDoc.put("Course co-ordinator text", newCourse.getCoOrd());
		newCourseDoc.put("Course Elements", insertElements(newCourse));
		newCourseDoc.put("Course Points", newCourse.getPoints());
		newCourseDoc.put("History Text", newCourse.getHistory());
		dataBase.getCollection("Courses").insertOne(newCourseDoc);
		if(!newCourse.getCourseOfferings().equals(null)) insertCourseOfferings(newCourse);
		if(!newCourse.getCourseInfo().equals(null)) insertCourseInfo(newCourse);
		addCourseOfferingsToTeachers(newCourse);
		return true;
	}

	private List<Document> insertElements(Course c) {
		if(c.getElements().size() > 0) {
			List<Document> elements = new ArrayList<Document>();
			for(int i=0; i < c.getElements().size(); i++) {
				elements.add(new Document().append("Name", c.getElements().get(i).getName())
						.append("Points", c.getElements().get(i).getPoints()));
			}
			return elements;
		}
		return new ArrayList<Document>();
	}

	private void insertCourseOfferings(Course c) {
		for(int i=0; i < c.getCourseOfferings().size(); i++) {
			Document offering = new Document();
			offering.put("Course Code", c.getCode());
			offering.put("Year", c.getCourseOfferings().get(i).getYear());
			offering.put("Expected Students", c.getCourseOfferings().get(i).getExpectedStu());
			offering.put("Reported Students", c.getCourseOfferings().get(i).getReportedStu());
			offering.put("Teachers", addTeachersToCourseOffering(c, i));
			dataBase.getCollection("Course Offerings").insertOne(offering);
		}
	}

	private List<Document> addTeachersToCourseOffering(Course c, int pos) {
		List<Document> teachers = new ArrayList<>();
		for(CourseTeacher t: c.getCourseOfferings().get(pos).getTeachers()) {
			teachers.add(new Document().append("Teacher", t.getTeacher())
					.append("Percentage", t.getPercentage()));
		}
		return teachers;
	}

	private void insertCourseInfo(Course c) {
		for(int i=0; i < c.getCourseInfo().size(); i++) {
			Document info = new Document();
			info.put("School Year", c.getCourseInfo().get(i).getYear());
			info.put("Course Code", c.getCode());
			info.put("Obligatory", c.getCourseInfo().get(i).isObligatory());
			info.put("Branch", c.getCourseInfo().get(i).getBranch());
			info.put("Program", c.getCourseInfo().get(i).getProgram());
			if(c.getCourseInfo().get(i).getPeriods().size() > 0) {
				List<Document> periods = new ArrayList<Document>();
				for(int j=0; j < c.getCourseInfo().get(i).getPeriods().size(); j++) {
					periods.add(new Document().append("Period", c.getCourseInfo()
							.get(i).getPeriods().get(j).getPeriod())
							.append("Points", c.getCourseInfo().get(i)
									.getPeriods().get(j).getPoints()));
				}
				info.put("Periods", periods);
			}
			dataBase.getCollection("Course Info").insertOne(info);
		}
	}
	
	public void insertCourseInfoForProgram(CourseInfo c) { 
        Document info = new Document(); 
        info.put("School Year", c.getYear()); 
        info.put("Course Code", c.getCode());
        info.put("Obligatory", c.isObligatory()); 
        info.put("Branch", c.getBranch()); 
        info.put("Program", c.getProgram()); 
        List<Document> periods = new ArrayList<Document>(); 
        for(int j=0; j < c.getPeriods().size(); j++) 
        {
            periods.add(
            new Document().append("Period", c.getPeriods().get(j).getPeriod()).
            append("Points", c.getPeriods().get(j).getPoints()));
        } 
        info.put("Periods", periods);                 
        dataBase.getCollection("Course Info").insertOne(info); 
    }

	private void addCourseOfferingsToTeachers(Course c) {
		List<Document> offerings = new ArrayList<>();
		for(int i=0; i < c.getCourseOfferings().size(); i++) {
			offerings.add(new Document().append("Course", c.getCode())
					.append("Year", c.getCourseOfferings().get(i).getYear()));
		}
		for(int i=0; i < offerings.size(); i++) {
			for(CourseTeacher t: c.getCourseOfferings().get(i).getTeachers()) { 
				dataBase.getCollection("Teachers").findOneAndUpdate(Filters.eq("Name", t.getTeacher())
						, Updates.pushEach("Course Offerings", offerings));
			}
		}
	}

	/*public void updateCourse(String code, String enName, String swName
            , String examiner, String progress, String coOrd
            , List<CourseElement> elements, String points, String history
            , List<CourseOffering> off, List<CourseInfo> inf) {

        BasicDBObject newDoc = new BasicDBObject();
        newDoc.append("$set", new BasicDBObject()
                .append("English Course Name", enName)
                .append("Swedish Course Name", swName)
                .append("Examiner Text", examiner)
                .append("Course Progress", progress)
                .append("Course co-ordinator text", coOrd)
                .append("Course Elements", updateCourseElements(elements))
                .append("Course Points", points)
                .append("History Text", history));


        BasicDBObject searchQuery = new BasicDBObject().append("Code", code);

        dataBase.getCollection("Courses").updateOne(searchQuery, newDoc);

        updateInfo(code, inf);
        updateOfferings(code, off);
    }

	private void updateInfo(String code, List<CourseInfo> l) {
		List<Document> newInfo = new ArrayList<>();
		for(int i=0; i < l.size(); i++) {
			Document newDoc = new Document().append("School Year", l.get(i).getYear())
					.append("Course Code", code).append("Obligatory", l.get(i).isObligatory())
					.append("Branch", l.get(i).getBranch()).append("Program", l.get(i).getProgram());
			List<Document> periods = new ArrayList<>();
			for(int j=0; j < l.get(i).getPeriods().size(); j++) {
				periods.add(new Document().append("Period", l.get(i).getPeriods().get(j).getPeriod())
						.append("Points", String.valueOf(l.get(i).getPeriods().get(j).getPoints())));
			}
			newDoc.append("Periods", periods);
			newInfo.add(newDoc);
		}

		dataBase.getCollection("Course Info").updateMany(new BasicDBObject().append("Code", code), newInfo);
	}

	private void updateOfferings(String code, List<CourseOffering> l) {

	}

	private List<Document> updateCourseElements(List<CourseElement> e){
        List<Document> newElements = new ArrayList<>();
        for(int i=0; i < e.size(); i++) {
            newElements.add(new Document().append("Name", e.get(i).getName())
                    .append("Points", String.valueOf(e.get(i).getPoints())));
        }
        return newElements;
    }*/

	public boolean updateCourse(Course c) throws MongoException, IOException {
		if(findCourses(c.getCode(), "Code").size()==0) return false;
		deleteCourse(c);
		addCourse(c);
		return true;
	}

	public boolean deleteCourse(Course c) {
		dataBase.getCollection("Courses").deleteOne(new BasicDBObject().append("Code", c.getCode()));
		dataBase.getCollection("Course Info").deleteMany(new BasicDBObject().append("Course Code", c.getCode()));
		dataBase.getCollection("Course Offerings").deleteMany(new BasicDBObject().append("Course Code", c.getCode()));

		MongoCursor<Document> cursor = dataBase.getCollection("Teachers").find().iterator();
		while(cursor.hasNext()) {
			Document obj = cursor.next();
			Bson delete = Updates.pull("Course Offerings", new Document("Course", c.getCode()));
			dataBase.getCollection("Teachers").updateOne(obj, delete);
		}
		return true;
	}


	public void updateProgram(String code, String name, String branch, String points,
			String coOrd) {

		BasicDBObject newDoc = new BasicDBObject();
		newDoc.append("$set", new BasicDBObject()
				.append("Name", name)
				.append("Branch", branch)
				.append("Points", points)
				.append("Program co-ordinator", coOrd));

		BasicDBObject searchQuery = new BasicDBObject().append("Code", code);

		dataBase.getCollection("Programs").updateOne(searchQuery, newDoc);
	}


	
	public ArrayList<Course> findCourses(String searchFor, String field) throws MongoException
	{
		ArrayList<Course> result = new ArrayList<>();
		MongoCursor<Document> cursor = null; 
		try
		{
			cursor = courses.find(regex(field, ".*" + searchFor + ".*")).iterator();
			while(cursor.hasNext())
			{ 
				Document obj =  cursor.next();
				Course courseToBeAdded = new Course();
				courseToBeAdded.setCode(obj.getString("Code"));
				courseToBeAdded.setSwName(obj.getString("Swedish Course Name"));
				courseToBeAdded.setEnName(obj.getString("English Course Name"));
				courseToBeAdded.setExaminer(obj.getString("Examiner Text"));
				courseToBeAdded.setProgress(obj.getString("Course Progress"));
				courseToBeAdded.setCoOrd(obj.getString("Course co-ordinator text"));
				ArrayList<Document> cElements = (ArrayList<Document>) obj.get("Course Elements");
				for(Document d: cElements) {
					if(!d.equals(null)) {
						String name = d.getString("Name");
						double points = d.getDouble("Points");
						courseToBeAdded.addElement(name, points);
					}

				}
				courseToBeAdded.setPoints(obj.getDouble("Course Points"));
				courseToBeAdded.setHistory(obj.getString("History Text"));
				addCourseInfo(courseToBeAdded);
				addCourseOfferings(courseToBeAdded);
				result.add(courseToBeAdded);
			}
		}
		finally 
		{
			try
			{
				cursor.close(); 
			}
			catch(MongoException e)
			{
				throw e;
			}

		}
		return result;
	}

	private void addCourseInfo(Course c) {
		MongoCursor<Document> cursor = courseInfo.find(regex("Course Code", c.getCode())).iterator();
		while(cursor.hasNext()) {
			Document obj = cursor.next();
			int year = obj.getInteger("School Year");
			boolean oblig = obj.getBoolean("Obligatory");
			String branch = obj.getString("Branch");
			String program = obj.getString("Program");
			List<Document> periods = (ArrayList<Document>) obj.get("Periods");
			List<Period> periodsList = new ArrayList<>();
			if(!periods.isEmpty()) {
				for(Document d: periods) {
					if(!d.equals(null)) periodsList.add(new Period(d.getInteger("Period")
							, d.getDouble("Points")));
				}
			}
			c.addInfo(program, branch, year, oblig, periodsList);
		}
	}

	private void addCourseOfferings(Course c) {
		MongoCursor<Document> cursor = courseOfferings.find(regex("Course Code", c.getCode())).iterator();
		while(cursor.hasNext()) {
			Document obj = cursor.next();
			int year = obj.getInteger("Year");
			int expectedStu = obj.getInteger("Expected Students");
			int reportedStu = obj.getInteger("Reported Students");
			List<Document> teachersFromDB = (ArrayList<Document>) obj.get("Teachers");
			List<CourseTeacher> teachersToAdd = new ArrayList<>();
			for(Document d: teachersFromDB) {
				teachersToAdd.add(new CourseTeacher(d.getString("Teacher"), d.getInteger("Percentage")));
			}
			c.addOffering(year, expectedStu, reportedStu, teachersToAdd);
		}
	}

        public ArrayList<CourseInfo> findCourseInfos(String searchFor)
        {
            ArrayList<CourseInfo> result = new ArrayList<>();
            MongoCursor<Document> cursor = null;

		try
		{
			cursor = courseInfo.find(regex("Program", ".*" + searchFor + ".*")).iterator(); 
			while(cursor.hasNext())
			{ 
                                CourseInfo toBeAdded;
				Document obj =  cursor.next();
				int year = obj.getInteger("School Year");
				String code = obj.getString("Course Code");
				Boolean obligatory = obj.getBoolean("Obligatory");
				String branch = obj.getString("Branch");
				String program = obj.getString("Program");
                                ArrayList<Document> periods =  (ArrayList<Document>) obj.get("Periods");
                                toBeAdded = new CourseInfo(code, program, branch, year, obligatory);                             
                                for(Document d: periods)
                                {
                                    if(!d.equals(null)) 
                                    {
					int period = d.getInteger("Period");
					Double points = d.getDouble("Points");
					toBeAdded.addPeriod(period, points);
                                    }
                                }
                            
				result.add(toBeAdded);
                                
			}
		}
		finally 
		{
			try {
				cursor.close();
			}
			catch(MongoException e) {
				throw e;
			}
		}
                return result;
        }
	
	public ArrayList<Program> findPrograms(String searchFor, String field) throws MongoException{
		ArrayList<Program> result = new ArrayList<>();
		MongoCursor<Document> cursor = null;

		try
		{
			cursor = programs.find(regex(field, ".*" + searchFor + ".*")).iterator(); 
			while(cursor.hasNext())
			{ 
				Document obj =  cursor.next();
				String code = obj.getString("Code");
				String name = obj.getString("Name");
				String branch = obj.getString("Branch");
				String points = obj.getString("Points");
				String coordinator = obj.getString("Program co-ordinator");
				result.add(new Program(code, name, branch, points, coordinator));
			}
		}
		finally 
		{
			try {
				cursor.close();
			}
			catch(MongoException e) {
				throw e;
			}
		}
		return result;
	}

	
	
	public ArrayList<Teacher> findTeachers(String searchFor, String field) throws MongoException{
		ArrayList<Teacher> result = new ArrayList<>();
		MongoCursor<Document> cursor = null;

		try {
			cursor = teachers.find(regex(field, ".*" + searchFor + ".*")).iterator(); 
			while(cursor.hasNext()) { 
				Document obj =  cursor.next();
				Teacher teacherToBeAdded = new Teacher();
				teacherToBeAdded.setEmail(obj.getString("Email"));
				teacherToBeAdded.setName(obj.getString("Name"));
				teacherToBeAdded.setSignature(obj.getString("Signature"));
				teacherToBeAdded.setHistory(obj.getString("History"));
				teacherToBeAdded.setDevPlan(obj.getString("Development Plan"));
				
				List<Document> list = (List<Document>)obj.get("Course Offerings");
				for(int i = 0; i < list.size(); i++) {
					String courseCode = list.get(i).getString("Course");
					int year = Integer.valueOf(list.get(i).get("Year").toString());
					teacherToBeAdded.addCourseOff(courseCode, year);
				}
				
				teacherToBeAdded.setUsername(obj.getString("Username"));
				teacherToBeAdded.setPassword(obj.getString("Password"));

				result.add(teacherToBeAdded);
			}
		}
		finally {
			try {
				cursor.close();
			}
			catch(MongoException e) {
				throw e;
			}
		}
		return result;
	}

	public Teacher findTeacher(String searchFor, String field) throws MongoException {
		MongoCursor<Document> cursor = null;
		try {
			cursor = teachers.find(regex(field, ".*" + searchFor + ".*")).iterator(); 
			if(cursor.hasNext()) {				
				Document obj =  cursor.next();
				Teacher teacherToBeAdded = new Teacher();
				teacherToBeAdded.setEmail(obj.getString("Email"));
				teacherToBeAdded.setName(obj.getString("Name"));
				teacherToBeAdded.setSignature(obj.getString("Signature"));
				teacherToBeAdded.setHistory(obj.getString("History"));
				teacherToBeAdded.setDevPlan(obj.getString("Development Plan"));
				
				List<Document> list = (List<Document>)obj.get("Course Offerings");
				for(int i = 0; i < list.size(); i++) {
					String courseCode = list.get(i).getString("Course");
					int year = Integer.valueOf(list.get(i).get("Year").toString());
					teacherToBeAdded.addCourseOff(courseCode, year);
				}
				teacherToBeAdded.setUsername(obj.getString("Username"));
				teacherToBeAdded.setPassword(obj.getString("Password"));

				return teacherToBeAdded;
			}
		}
		finally {
			try {
				cursor.close();
			}
			catch(MongoException e) {
				throw e;
			}
		}
		return null;
	}
	
	
	
	public boolean addTeacher(Teacher newTeacher) throws IOException, MongoException {
		
		Document newTeacherDoc = new Document();
		newTeacherDoc.put("Email", newTeacher.getEmail());
		newTeacherDoc.put("Name", newTeacher.getName());
		
		newTeacherDoc.put("Course Offerings", insertCourseOffToTeacher(newTeacher));
		
		newTeacherDoc.put("Signature", newTeacher.getSignature());
		newTeacherDoc.put("History", newTeacher.getHistory());
		newTeacherDoc.put("Development Plan", newTeacher.getDevPlan());
		newTeacherDoc.put("Username", newTeacher.getUsername());
		newTeacherDoc.put("Password", newTeacher.getPassword());
		
		teachers.insertOne(newTeacherDoc);

		return true;
	}
	private List<Document> insertCourseOffToTeacher(Teacher t) {
		if(t.getCourseOff().size() > 0) {
			List<Document> courseOff = new ArrayList<Document>();
			for(int i=0; i < t.getCourseOff().size(); i++) {
				courseOff.add(new Document().append("Course", t.getCourseOff().get(i).getCode())
						.append("Year", t.getCourseOff().get(i).getYear()));
			}
			return courseOff;
		}
		return new ArrayList<Document>();
	}
	
	
	
	public void updateTeacher(String email, String name, String signature, String history, String devPlan, 
			ArrayList<TeacherCourseOffering> courseOff, String username, String password) {
		BasicDBObject newDoc = new BasicDBObject();
		newDoc.append("$set", new BasicDBObject()
				.append("Email", email)
				.append("Name", name)
				.append("Signature", signature)
				.append("History", history)
				.append("Development Plan", devPlan)
				//.append("Course Offerings", insertCourseOffToTeacher(courseOff))
				.append("Username", username)
				.append("Password", password));
		
		BasicDBObject searchQuery = new BasicDBObject().append("Name", name);
		teachers.updateOne(searchQuery, newDoc);
	}
	/*
	private List<Document> insertCourseOffToTeacher(ArrayList<TeacherCourseOffering> t) {
		if(t.size() > 0) {
			List<Document> courseOff = new ArrayList<Document>();
			for(int i=0; i < t.size(); i++) {
				courseOff.add(new Document().append("Course", t.get(i).getCode())
						.append("Year", t.get(i).getYear()));
			}
			return courseOff;
		}
		return new ArrayList<Document>();
	}
	*/
	
	
	public ArrayList<Teacher> getAllTeachers() throws MongoException{
		ArrayList<Teacher> result = new ArrayList<>();
		MongoCursor<Document> cursor = null;

		try {
			cursor = teachers.find().iterator(); 
			while(cursor.hasNext()) { 
				Document obj =  cursor.next();
				Teacher teacherToBeAdded = new Teacher();
				teacherToBeAdded.setName(obj.getString("Name"));
				teacherToBeAdded.setSignature(obj.getString("Signature"));
				teacherToBeAdded.setEmail(obj.getString("Email"));
				teacherToBeAdded.setHistory(obj.getString("History"));
				teacherToBeAdded.setDevPlan(obj.getString("Development Plan"));
				
				List<Document> list = (List<Document>)obj.get("Course Offerings");
				for(int i = 0; i < list.size(); i++) {
					String courseCode = list.get(i).getString("Course");
					int year = Integer.valueOf(list.get(i).get("Year").toString());
					teacherToBeAdded.addCourseOff(courseCode, year);
				}
				result.add(teacherToBeAdded);
			}
		}
		finally {
			try {
				cursor.close();
			}
			catch(MongoException e) {
				throw e;
			}
		}
		return result;
	}




	public ArrayList<Course> getAllCourses() throws MongoException{
		ArrayList<Course> result = new ArrayList<>();
		MongoCursor<Document> cursor = null; 
		try
		{
			cursor = courses.find().iterator();
			while(cursor.hasNext())
			{ 
				Document obj =  cursor.next();
				Course courseToBeAdded = new Course();
				courseToBeAdded.setCode(obj.getString("Code"));
				courseToBeAdded.setSwName(obj.getString("Swedish Course Name"));
				courseToBeAdded.setEnName(obj.getString("English Course Name"));
				courseToBeAdded.setExaminer(obj.getString("Examiner Text"));
				courseToBeAdded.setProgress(obj.getString("Course Progress"));
				courseToBeAdded.setCoOrd(obj.getString("Course co-ordinator text"));
				ArrayList<Document> cElements = (ArrayList<Document>) obj.get("Course Elements");
				for(Document d: cElements) {
					if(!d.equals(null)) {
						String name = d.getString("Name");
						double points = d.getDouble("Points");
						courseToBeAdded.addElement(name, points);
					}

				}
				courseToBeAdded.setPoints(obj.getDouble("Course Points"));
				courseToBeAdded.setHistory(obj.getString("History Text"));
				addCourseInfo(courseToBeAdded);
				addCourseOfferings(courseToBeAdded);
				result.add(courseToBeAdded);
			}
		}
		finally 
		{
			try
			{
				cursor.close(); 
			}
			catch(MongoException e)
			{
				throw e;
			}

		}
		return result;

	}
	public ArrayList<String> getProgList() throws MongoException{
		ArrayList<String> result=new ArrayList<>();
		MongoCursor<Document> cursor = null;

		try
		{
			cursor = programs.find().iterator();
			while(cursor.hasNext())
			{
				Document obj =  cursor.next();
				String name = obj.getString("Name");
				boolean alreadyExists=false;

				for(int i = 0; i<result.size();i++)
				{
					if(name.contentEquals(result.get(i)))
						alreadyExists=true;
					break;
				}
				if(!alreadyExists) {
					result.add(name);
				} 

			}
		}
		finally
		{
			try {
				cursor.close();
			}
			catch(MongoException e) {
				throw e;
			}
		}
		return result;
	}

	public ArrayList<String> getBranchList() {

		ArrayList<String> result=new ArrayList<>();
		MongoCursor<Document> cursor = null;

		try
		{
			cursor = programs.find().iterator();
			while(cursor.hasNext())
			{
				Document obj =  cursor.next();
				String name = obj.getString("Branch");
				result.add(name);

			}
		}
		finally
		{
			try {
				cursor.close();
			}
			catch(MongoException e) {
				throw e;
			}
		}
		return result;	}

	public ArrayList<String> getTeachersList() {
		ArrayList<String> result=new ArrayList<>();
		MongoCursor<Document> cursor = null;

		try
		{
			cursor = teachers.find().iterator();
			while(cursor.hasNext())
			{
				Document obj =  cursor.next();
				String name = obj.getString("Name");

				result.add(name);

			}
		}
		finally
		{
			try {
				cursor.close();
			}
			catch(MongoException e) {
				throw e;
			}
		}
		return result;
	} 

	public static void main(String[] args) throws IOException
	{
		DatabaseManager db = new DatabaseManager();
		db.connect();
		ArrayList<Course> result = db.findCourses("Databas", "Swedish Course Name");
		for (int i = 0; i < result.size(); i++) 
		{
			System.out.println(result.get(i));
		}

		/*ArrayList<Program> prog = db.findPrograms("teknik", "Branch");
		for (int i = 0; i < prog.size(); i++) 
		{
			System.out.println(prog.get(i));
		} */

		Course test = new Course();
		test.setEnName("Test");
		test.setSwName("Test");
		test.setCode("HI1000");
		test.addElement("LABA", 3);
		test.addElement("TEN1", 6);
		double points = 0;
		for(int i=0; i < test.getElements().size(); i++) {
			points += test.getElements().get(i).getPoints();
		}
		test.setPoints(points);
		List<Period> periods = new ArrayList<>();
		periods.add(new Period(1, 4.5));
		periods.add(new Period(2, 4.5));
		test.addInfo("Testprogrammet", "Testteknik", 2019, true, periods);
		List<CourseTeacher> teachers = new ArrayList<>();
		teachers.add(new CourseTeacher("Anders Cajander", 100));
		test.addOffering(2019, 50, 45, teachers);
		db.addCourse(test);
		test.setEnName("Chenged EN Name"); 
		db.updateCourse(test);
		//db.deleteCourse(db.findCourses("HI1000", "Code").get(0));

		/*Program p = new Program();
		p.setName("Testprogrammet");
		p.setBranch("Testteknik");
		p.setCoOrd("Test");
		//p.setCourses(db.findCourses("HI", "Code"));
		db.addProgram(p); 

		Course test = result.get(0);

		db.updateCourse("HI1030", "Database Technology", test.getSwName(), test.getExaminer(), test.getProgress()
				, test.getCoOrd(), db.elementsToString(test), String.valueOf(test.getPoints()), db.programsToString(test)
				, db.periodsToString(test), test.getHistory()); */
		db.disconect();
	} 
}
