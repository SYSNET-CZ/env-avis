package cz.sysnet.env.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

/**
 * SMLOUVY.DBF – realizační smlouvy
 * SMLOUVY.CSV – realizační smlouvy
 * 
 * @author rjaeg
 *
 */
public class Smlouva  extends BaseEnvClass {
	private static final long serialVersionUID = 1L;
	
	public static final String FILE_NAME = "SMLOUVY.DBF"; 
//	public static final List<String> DBF_FIELD_LIST = 
//			new ArrayList<String>(Arrays.asList(
//					"KODPROJ", "DODAVATEL", "SMLOUVA", "VARIAB", "SPECIF", "ICO", "DNY" , "DRUHFA", "PROCENTAZA"
//					));
	public static final List<String> DBF_FIELD_LIST = 
			new ArrayList<String>(Arrays.asList(
					"CISLOSML",
					"TYP",
					"ZAKLSML",
					"PREDMET",
					// "PORADIPART",
					"DNY",
					"DRUHFA",
					"KODPROJ",
					"SAZBADPH",
					"CASTKA",
					"PROCENTOZA",
					"VARSYMBOL",
					"SPECSYMBOL",
					// "CISLOJEDN",
					// "CISLOEVID",
					"DATPODPIS",
					//"DATUCIN",
					"ICO",
					"DODAVATEL"
					));
		
	public static final List<String> CSV_FIELD_LIST = 
			new ArrayList<String>(Arrays.asList(
					"CISLOSMLOUVY",
					"TYP",
					"ZAKLADNISMLOUVA",
					"PREDMET",
					// "PORADIPARTNERA",
					"DNY",
					"DRUHFAKTURY",
					"KODPROJEKTU",
					"SAZBADPH",
					"CASTKA",
					"PROCENTOZAD",
					"VARSYMBOL",
					"SPECSYMBOL",
					// "CISLOJEDNACI",
					//"CISLOEVIDENCNI",
					"DATUMPODPISU",
					// "DATUMUCINNOSTI",
					"ICO", 
					"DODAVATEL"
					));
	
	public static String[] CSV_HEADER = (String[]) CSV_FIELD_LIST.toArray(new String[CSV_FIELD_LIST.size()]);
	public static String[] DBF_HEADER = (String[]) DBF_FIELD_LIST.toArray(new String[DBF_FIELD_LIST.size()]);
	
	@CsvBindByName(column = "CISLOSMLOUVY")
	private String cisloSmlouvy;
	
	@CsvBindByName(column = "TYP")
	private String typ;
	
	@CsvBindByName(column = "ZAKLADNISMLOUVA")
	private String zakladniSmlouva;
	
	@CsvBindByName(column = "PREDMET")
	private String predmet;
	
//	@CsvBindByName(column = "PORADIPARTNERA")
//	private String poradiPartnera;
	
	@CsvBindByName(column = "DNY")
	private long dny;
	
	@CsvBindByName(column = "DRUHFAKTURY")
	private String druhFaktury;
	
	@CsvBindByName(column = "KODPROJEKTU")
	private String kodProjektu;
	
	@CsvBindByName(column = "SAZBADPH")
	private double sazbaDph;
	
	@CsvBindByName(column = "CASTKA")
	private double castka;
	
	@CsvBindByName(column = "PROCENTOZAD")
	private double procentoZad;
	
	@CsvBindByName(column = "VARSYMBOL")
	private String varSymbol;
	
	@CsvBindByName(column = "SPECSYMBOL")
	private String specSymbol;
	
//	@CsvBindByName(column = "CISLOJEDNACI")
//	private String cisloJednaci;
//	
//	@CsvBindByName(column = "CISLOEVIDENCNI")
//	private String cisloEvidencni;
	
	@CsvBindByName(column = "DATUMPODPISU")
	@CsvDate("yyyy-MM-dd")
	private Date datumPodpisu;      
	
//	@CsvBindByName(column = "DATUMUCINNOSTI")
//	@CsvDate("yyyy-MM-dd")
//	private Date datumUcinnosti;      
	
	@CsvBindByName(column = "ICO")
	private String ico;
	
	@CsvBindByName(column = "DODAVATEL")
	private String dodavatel;

	public static List<String> getDbfFieldList() {
		return DBF_FIELD_LIST;
	}

	public String getCisloSmlouvy() {
		return cisloSmlouvy;
	}

	public void setCisloSmlouvy(String cisloSmlouvy) {
		this.cisloSmlouvy = cisloSmlouvy;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getZakladniSmlouva() {
		return zakladniSmlouva;
	}

	public void setZakladniSmlouva(String zakladniSmlouva) {
		this.zakladniSmlouva = zakladniSmlouva;
	}

	public String getPredmet() {
		return predmet;
	}

	public void setPredmet(String predmet) {
		this.predmet = predmet;
	}

	public long getDny() {
		return dny;
	}

	public void setDny(long dny) {
		this.dny = dny;
	}

	public String getDruhFaktury() {
		return druhFaktury;
	}

	public void setDruhFaktury(String druhFaktury) {
		this.druhFaktury = druhFaktury;
	}

	public String getKodProjektu() {
		return kodProjektu;
	}

	public void setKodProjektu(String kodProjektu) {
		this.kodProjektu = kodProjektu;
	}

	public double getSazbaDph() {
		return sazbaDph;
	}

	public void setSazbaDph(double sazbaDph) {
		this.sazbaDph = sazbaDph;
	}

	public double getCastka() {
		return castka;
	}

	public void setCastka(double castka) {
		this.castka = castka;
	}

	public double getProcentoZad() {
		return procentoZad;
	}

	public void setProcentoZad(double procentoZad) {
		this.procentoZad = procentoZad;
	}

	public String getVarSymbol() {
		return varSymbol;
	}

	public void setVarSymbol(String varSymbol) {
		this.varSymbol = varSymbol;
	}

	public String getSpecSymbol() {
		return specSymbol;
	}

	public void setSpecSymbol(String specSymbol) {
		this.specSymbol = specSymbol;
	}

	public Date getDatumPodpisu() {
		return datumPodpisu;
	}

	public void setDatumPodpisu(Date datumPodpisu) {
		this.datumPodpisu = datumPodpisu;
	}

	public String getIco() {
		return ico;
	}

	public void setIco(String ico) {
		this.ico = ico;
	}

	public String getDodavatel() {
		return dodavatel;
	}

	public void setDodavatel(String dodavatel) {
		this.dodavatel = dodavatel;
	}
}
