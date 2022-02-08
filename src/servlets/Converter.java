//Author: Ramy
package servlets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Converter {
	public static String convertFileToStr(File file) {
	    StringBuilder contentB = new StringBuilder();
	    
		try (BufferedReader br = new BufferedReader(new FileReader(file)))
	    {	 
	        String sCurrentLine;
	        while ((sCurrentLine = br.readLine()) != null) 
	        {
	            contentB.append(sCurrentLine).append("\n"); 
	        }
	    } 
	    catch (IOException e) 
	    {
	        e.printStackTrace();
	    }

		String content=contentB.toString();
		return content;
		
	}
	
	public static StringBuilder jsonAppend(StringBuilder json, String type, String value) {
		json.append("\n");
		json.append("\"");
		json.append(type);
		json.append("\"");
		json.append(" : ");
		json.append("\"");
		json.append(value);
		json.append("\"");
		json.append(",");
		json.append("\n");
		return json;
		
	}	
	public static StringBuilder jsonAppendLast(StringBuilder json, String type, String value) {
		json.append("\n");
		json.append("\"");
		json.append(type);
		json.append("\"");
		json.append(" : ");
		json.append("\"");
		json.append(value);
		json.append("\"");
		json.append("\n");
		return json;
		
	}
}
