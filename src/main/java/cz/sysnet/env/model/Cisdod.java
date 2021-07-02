package cz.sysnet.env.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.bean.CsvBindByName;

/**
 * CISDOD.DBF – ekologické smlouvy
 * 
 * @author rjaeg
 *
 */
public class Cisdod extends BaseEnvClass {
	private static final long serialVersionUID = 1L;
	
	public static final String FILE_NAME = "CISDOD.DBF";
	private static final List<String> DBF_FIELD_LIST = 
			new ArrayList<String>(Arrays.asList(
					"DODAVATEL", "SMLOUVA", "CISPART", "VARIAB", "SPECIF", "ICO", "DNY"
					));
	
	@CsvBindByName(column = "DODAVATEL")
	String dodavatel;		// Název dodavatele
	@CsvBindByName(column = "SMLOUVA")
	String smlouva;			// Číslo smlouvy
	@CsvBindByName(column = "CISPART")
	String cispart;			// Pořadí partnera
	@CsvBindByName(column = "VARIAB")
	String variab;			// Variabilní symbol
	@CsvBindByName(column = "SPECIF")
	String specif;			// Specifický symbol
	@CsvBindByName(column = "ICO")
	String ico;				// IČO
	@CsvBindByName(column = "DNY")
	long dny;				// Počet dní
	
	
	public static List<String> getDbfFieldList() {
		return DBF_FIELD_LIST;
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
}
