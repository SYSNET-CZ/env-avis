package cz.sysnet.env.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.bean.CsvBindByName;

/**
 * CISDOD3.DBF – realizační smlouvy
 * 
 * @author rjaeg
 *
 */
public class Cisdod3  extends BaseEnvClass {
	private static final long serialVersionUID = 1L;
	
	public static final String FILE_NAME = "CISDOD3.DBF"; 
	public static final List<String> DBF_FIELD_LIST = 
			new ArrayList<String>(Arrays.asList(
					"KODPROJ", "DODAVATEL", "SMLOUVA", "VARIAB", "SPECIF", "ICO", "DNY" , "DRUHFA", "PROCENTAZA"
					));
	public static String[] CSV_HEADER = (String[]) DBF_FIELD_LIST.toArray(new String[DBF_FIELD_LIST.size()]);
	
	@CsvBindByName(column = "KODPROJ")
	private String kodproj;
	
	@CsvBindByName(column = "DODAVATEL")
	private String dodavatel;
	
	@CsvBindByName(column = "SMLOUVA")
	private String smlouva;
	
	@CsvBindByName(column = "VARIAB")
	private String variab;
	
	@CsvBindByName(column = "SPECIF")
	private String specif;
	
	@CsvBindByName(column = "ICO")
	private String ico;
	
	@CsvBindByName(column = "DNY")
	private long dny;
	
	@CsvBindByName(column = "DRUHFA")
	private String druhfa;
	
	@CsvBindByName(column = "PROCENTAZA")
	private double procentaza;
	
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
}
