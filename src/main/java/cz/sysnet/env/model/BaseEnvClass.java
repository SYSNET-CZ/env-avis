package cz.sysnet.env.model;

import java.io.Serializable;

import com.google.gson.Gson;

import cz.sysnet.env.Utils;

public class BaseEnvClass implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
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
