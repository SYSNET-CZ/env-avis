package cz.sysnet.env.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import cz.sysnet.env.Utils;

/**
 * SUP.DBF – číselník supervizorů
 * @author rjaeg
 *
 */
public class Sup implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String FILE_NAME = "SUP.DBF";
	private static final List<String> DBF_FIELD_LIST = new ArrayList<String>(Arrays.asList("SUP", "ICO"));
	
	String name;		// Název supervizora
	String ico;
	
	public static List<String> getDbfFieldList() {
		return DBF_FIELD_LIST;
	}
	public String getSup() {
		return name;
	}
	public void setSup(String sup) {
		this.name = sup;
	}
	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	
	@Override
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);		
	}
	
	public String hashString() {
		return this.hashString(null);
	}
	
	public String hashString(String algoritm) {
		return Utils.hash(this.toString(), algoritm);		
	}
	
	public boolean checkHashString(String hash) {
		return this.checkHashString(hash, null);
	}
	
	public boolean checkHashString(String hash, String algoritm) {
		return hash.equals(this.hashString(algoritm)); 		
	}
}
