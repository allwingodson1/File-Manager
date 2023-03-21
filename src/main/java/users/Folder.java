package users;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;

public class Folder {
	public int id;
	public String name;
	public Date dateOfCreation;
	public String createdBy;
	public Date dateOfModification;
	public String lastModifiedBy;
	public boolean isFavourite;
	public Folder(int id, String name, Date dateOfCreation, String createdBy, Date dateOfModification,String lastModifiedBy,boolean isFavourite) {
		this.id = id;
		this.name = name;
		this.dateOfCreation = dateOfCreation;
		this.createdBy = createdBy;
		this.dateOfModification = dateOfModification;
		this.lastModifiedBy = lastModifiedBy;
		this.isFavourite = isFavourite;
	}
	
	public JSONObject getJSON() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("name", name);
		obj.put("dateOfCreation", df.format(dateOfCreation));
		obj.put("createdBy", createdBy);
		obj.put("dateOfModification",df.format(dateOfModification));
		obj.put("lastModifiedBy", lastModifiedBy);
		obj.put("isFavourite", isFavourite);
		return obj;
	}
}