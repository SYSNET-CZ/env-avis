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
	
	public static String FILE_NAME = "SUP.DBF";
	public static List<String> DBF_FIELD_LIST = new ArrayList<String>(Arrays.asList("SUP", "ICO"));
	
	String sup;		// Název supervizora
	String ico;
	
	public String getSup() {
		return sup;
	}
	public void setSup(String sup) {
		this.sup = sup;
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
		String json = gson.toJson(this);
		return json;		
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
