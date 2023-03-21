package users;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;

public class File {
	public int id;
	public String name;
	public Date dateOfCreation;
	public String ContentType;
	public Date dateOfModification;
	public String createdBy;
	public String lastModifiedBy;
	public boolean isFavourite;
	public File(int iD2, String name, Date dateOfCreation, String contentType,String createdBy,Date dateOfModification,String lastModifiedBy,boolean isFavourite) {
		this.id = iD2;
		this.name = name;
		this.dateOfCreation = dateOfCreation;
		this.lastModifiedBy = lastModifiedBy;
		this.ContentType = contentType;
		this.createdBy = createdBy;
		this.dateOfModification = dateOfModification;
		this.isFavourite = isFavourite;
	}
	@SuppressWarnings("unchecked")
	public JSONObject getJSON() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("name", name);
		obj.put("dateOfCreation", df.format(dateOfCreation));
		obj.put("ContentType", ContentType);
		obj.put("createdBy", createdBy);
		obj.put("dateOfModification", df.format(dateOfModification));
		obj.put("lastModifiedBy", lastModifiedBy);
		obj.put("isFavourite", isFavourite);
		return obj;
	}
}
