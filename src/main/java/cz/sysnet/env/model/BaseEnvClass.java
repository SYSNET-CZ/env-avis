package cz.sysnet.env.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import cz.sysnet.env.Utils;

public class BaseEnvClass implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final List<String> DBF_FIELD_LIST = new ArrayList<String>(Arrays.asList("FIELD1", "FIELD2"));
	public static String[] CSV_HEADER = (String[]) DBF_FIELD_LIST.toArray(new String[DBF_FIELD_LIST.size()]);
	
	
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
