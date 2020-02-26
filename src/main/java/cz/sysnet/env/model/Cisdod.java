package cz.sysnet.env.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;

import cz.sysnet.env.Utils;

/**
 * CISDOD.DBF – ekologické smlouvy
 * 
 * @author rjaeg
 *
 */
public class Cisdod implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static String FILE_NAME = "CISDOD.DBF";
	public static List<String> DBF_FIELD_LIST = 
			new ArrayList<String>(Arrays.asList(
					"DODAVATEL", "SMLOUVA", "CISPART", "VARIAB", "SPECIF", "ICO", "DNY"
					));
	
	
	String dodavatel;		// Název dodavatele
	String smlouva;			// Číslo smlouvy
	String cispart;			// Pořadí partnera
	String variab;			// Variabilní symbol
	String specif;			// Specifický symbol
	String ico;				// IČO
	long dny;				// Počet dní
	
	
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
	public String getCispart() {
		return cispart;
	}
	public void setCispart(String cispart) {
		this.cispart = cispart;
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
