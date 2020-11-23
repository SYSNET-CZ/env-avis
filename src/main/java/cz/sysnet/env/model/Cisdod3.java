package cz.sysnet.env.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import cz.sysnet.env.Utils;

/**
 * CISDOD3.DBF – realizační smlouvy
 * 
 * @author rjaeg
 *
 */
public class Cisdod3 implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String FILE_NAME = "CISDOD3.DBF"; 
	private static final List<String> DBF_FIELD_LIST = 
			new ArrayList<String>(Arrays.asList(
					"KODPROJ", "DODAVATEL", "SMLOUVA", "VARIAB", "SPECIF", "ICO", "DNY" , "DRUHFA", "PROCENTAZA"
					));
	
	String kodproj;
	String dodavatel;
	String smlouva;
	String variab;
	String specif;
	String ico;
	long dny;
	String druhfa;
	double procentaza;
	
	public static List<String> getDbfFieldList() {
		return DBF_FIELD_LIST;
	}
	public String getKodproj() {
		return kodproj;
	}
	public void setKodproj(String kodproj) {
		this.kodproj = kodproj;
	}
	public String getDodavatel() {
		return dodavatel;
	}
	public void setDodavatel(String dodavatel) {
		this.dodavatel = dodavatel;
	}
	public String getSmlouva() {
		return smlouva;
	}
	public void setSmlouva(String smlouva) {
		this.smlouva = smlouva;
	}
	public String getVariab() {
		return variab;
	}
	public void setVariab(String variab) {
		this.variab = variab;
	}
	public String getSpecif() {
		return specif;
	}
	public void setSpecif(String specif) {
		this.specif = specif;
	}
	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	public long getDny() {
		return dny;
	}
	public void setDny(long dny) {
		this.dny = dny;
	}
	public String getDruhfa() {
		return druhfa;
	}
	public void setDruhfa(String druhfa) {
		this.druhfa = druhfa;
	}
	public double getProcentaza() {
		return procentaza;
	}
	public void setProcentaza(double procentaza) {
		this.procentaza = procentaza;
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
