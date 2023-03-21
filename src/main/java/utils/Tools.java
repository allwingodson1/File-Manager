package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;

public class Tools {
	public static Connection DB;
	public static Integer ID;
	public static String getTextOfAFile(File file) {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String result = "";
			String temp;
			while((temp = reader.readLine())!=null) {
				result += temp;
				result+="\n";
			}
			return result;
		}
		catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
