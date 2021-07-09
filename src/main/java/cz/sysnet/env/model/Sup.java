package cz.sysnet.env.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.opencsv.bean.CsvBindByName;


/**
 * SUP.DBF – číselník supervizorů
 * @author rjaeg
 *
 */
public class Sup extends BaseEnvClass {
	private static final long serialVersionUID = 1L;
	
	public static final String FILE_NAME = "SUP.DBF";
	public static final List<String> DBF_FIELD_LIST = new ArrayList<String>(Arrays.asList("SUP", "ICO"));
	public static String[] CSV_HEADER = (String[]) DBF_FIELD_LIST.toArray(new String[DBF_FIELD_LIST.size()]);
	
	@CsvBindByName(column = "SUP")
	String name;		// Název supervizora
	@CsvBindByName(column = "ICO")
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
}
