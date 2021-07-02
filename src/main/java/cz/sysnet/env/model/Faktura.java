package cz.sysnet.env.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

public class Faktura  extends BaseEnvClass {
	private static final long serialVersionUID = 1L;

	public static final String FILE_NAME = "FaktLN.dbf";
	private static final List<String> DBF_FIELD_LIST = 
			new ArrayList<String>(Arrays.asList(
					"PORCIS", "DODAVATEL", "ICOD", "SMLOUVA", "CISPART", "CISFA", "CASTKA", 
					"UHRADA", "CUCTU", "BANKA", "VYSTAVENA", "DOSLA", "LIKVIDACE", "VRACKPROP", 
					"VARIAB", "SPECIF", "DRUHFA", "SPLATNA", "UHRAZENA", "VRACENA", "ZAUCTOVANO", 
					"SUP", "ICOS", "PU", "KODPROJ", "PROCENTAZA", "CASTKAZA", "SPLATNAZA", "CASTKAZACA", 
					"UHRAZENAZA", "CASTKABDPH"
					));
	
	@CsvBindByName(column = "PORCIS")
	String porcis;     	//Pořadové číslo faktury v AVISme
	@CsvBindByName(column = "DODAVATEL")
	String dodavatel;   //Název dodavatele
	@CsvBindByName(column = "ICOD")
	String icod;     	//IČO dodavatele
	@CsvBindByName(column = "SMLOUVA")
	String smlouva;     //Číslo ekologické smlouvy
	@CsvBindByName(column = "CISPART")
	String cispart;     //Pořadí partnera
	@CsvBindByName(column = "CISFA")
	String cisfa;     	//Číslo faktury dodavatele
	@CsvBindByName(column = "CASTKA")
	double castka;     	//Částka (v Kč)
	@CsvBindByName(column = "UHRADA")
	String uhrada;     	//Forma úhrady (PU – příkaz k úhradě, DB – dobropis)
	@CsvBindByName(column = "CUCTU")
	String cuctu;     	//Číslo bankovního účtu dodavatele
	@CsvBindByName(column = "BANKA")
	String banka;    	//Kód banky dodavatele
	@CsvBindByName(column = "VYSTAVENA")
	@CsvDate("yyyy-MM-dd")
	Date vystavena;     //Datum vystavení faktury      
	@CsvBindByName(column = "DOSLA")
	@CsvDate("yyyy-MM-dd")
	Date dosla;     	//Datum přijetí faktury      
	@CsvBindByName(column = "LIKVIDACE")
	@CsvDate("yyyy-MM-dd")
	Date likvidace;     //Datum likvidace faktury      
	@CsvBindByName(column = "VRACKPROP")
	@CsvDate("yyyy-MM-dd")
	Date vrackprop;     //Datum vrácení k proplacení         
	@CsvBindByName(column = "VARIAB")
	String variab;     	//Variabilní symbol
	@CsvBindByName(column = "SPECIF")
	String specif;     	//Specifický symbol
	@CsvBindByName(column = "DRUHFA")
	String druhfa;     	//Druh faktury (A – analýza, B – aktualizovaná analýza rizik, K – kon, O – oponentura, S – sanace, V – supervize, X – různé)
	@CsvBindByName(column = "SPLATNA")
	@CsvDate("yyyy-MM-dd")
	Date splatna;     	//Datum splatnosti         
	@CsvBindByName(column = "UHRAZENA")
	@CsvDate("yyyy-MM-dd")
	Date uhrazena;     	//Datum uhrazení         
	@CsvBindByName(column = "VRACENA")
	@CsvDate("yyyy-MM-dd")
	Date vracena;     	//Datum vrácení
	@CsvBindByName(column = "ZAUCTOVANO")
	@CsvDate("yyyy-MM-dd")
	Date zauctovano;    //Datum zaúčtování
	@CsvBindByName(column = "SUP")
	String sup;     	//Název supervizora 
	@CsvBindByName(column = "ICOS")
	String icos;     	//IČO supervizora
	@CsvBindByName(column = "PU")
	String pu;     		//Stav příkazu k úhradě (1= uhrazeno)
	@CsvBindByName(column = "KODPROJ")
	String kodproj;     //Kód smlouvy
	@CsvBindByName(column = "PROCENTAZA")
	double procentaza;  //Procento zádržného (v %)
	@CsvBindByName(column = "CASTKAZA")
	double castkaza;    //Částka zádržného (v Kč)
	@CsvBindByName(column = "SPLATNAZA")
	@CsvDate("yyyy-MM-dd")
	Date splatnaza;     //Datum splatnosti zádržného
	@CsvBindByName(column = "CASTKAZACA")
	double castkazaca;  //Částka zádržného (v Kč)
	@CsvBindByName(column = "UHRAZENAZA")
	@CsvDate("yyyy-MM-dd")
	Date uhrazenaza;    //Datum uhrazení zádržného
	@CsvBindByName(column = "CASTKABDPH")
	double castkabdph;  //Částka bez DPH (v Kč)
	
	public static List<String> getDbfFieldList() {
		return DBF_FIELD_LIST;
	}
	public String getPorcis() {
		return porcis;
	}
	public void setPorcis(String porcis) {
		this.porcis = porcis;
	}
	public String getDodavatel() {
		return dodavatel;
	}
	public void setDodavatel(String dodavatel) {
		this.dodavatel = dodavatel;
	}
	public String getIcod() {
		return icod;
	}
	public void setIcod(String icod) {
		this.icod = icod;
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
	public String getCisfa() {
		return cisfa;
	}
	public void setCisfa(String cisfa) {
		this.cisfa = cisfa;
	}
	public double getCastka() {
		return castka;
	}
	public void setCastka(double castka) {
		this.castka = castka;
	}
	public String getUhrada() {
		return uhrada;
	}
	public void setUhrada(String uhrada) {
		this.uhrada = uhrada;
	}
	public String getCuctu() {
		return cuctu;
	}
	public void setCuctu(String cuctu) {
		this.cuctu = cuctu;
	}
	public String getBanka() {
		return banka;
	}
	public void setBanka(String banka) {
		this.banka = banka;
	}
	public Date getVystavena() {
		return vystavena;
	}
	public void setVystavena(Date vystavena) {
		this.vystavena = vystavena;
	}
	public Date getDosla() {
		return dosla;
	}
	public void setDosla(Date dosla) {
		this.dosla = dosla;
	}
	public Date getLikvidace() {
		return likvidace;
	}
	public void setLikvidace(Date likvidace) {
		this.likvidace = likvidace;
	}
	public Date getVrackprop() {
		return vrackprop;
	}
	public void setVrackprop(Date vrackprop) {
		this.vrackprop = vrackprop;
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
	public String getDruhfa() {
		return druhfa;
	}
	public void setDruhfa(String druhfa) {
		this.druhfa = druhfa;
	}
	public Date getSplatna() {
		return splatna;
	}
	public void setSplatna(Date splatna) {
		this.splatna = splatna;
	}
	public Date getUhrazena() {
		return uhrazena;
	}
	public void setUhrazena(Date uhrazena) {
		this.uhrazena = uhrazena;
	}
	public Date getVracena() {
		return vracena;
	}
	public void setVracena(Date vracena) {
		this.vracena = vracena;
	}
	public Date getZauctovano() {
		return zauctovano;
	}
	public void setZauctovano(Date zauctovano) {
		this.zauctovano = zauctovano;
	}
	public String getSup() {
		return sup;
	}
	public void setSup(String sup) {
		this.sup = sup;
	}
	public String getIcos() {
		return icos;
	}
	public void setIcos(String icos) {
		this.icos = icos;
	}
	public String getPu() {
		return pu;
	}
	public void setPu(String pu) {
		this.pu = pu;
	}
	public String getKodproj() {
		return kodproj;
	}
	public void setKodproj(String kodproj) {
		this.kodproj = kodproj;
	}
	public double getProcentaza() {
		return procentaza;
	}
	public void setProcentaza(double procentaza) {
		this.procentaza = procentaza;
	}
	public double getCastkaza() {
		return castkaza;
	}
	public void setCastkaza(double castkaza) {
		this.castkaza = castkaza;
	}
	public Date getSplatnaza() {
		return splatnaza;
	}
	public void setSplatnaza(Date splatnaza) {
		this.splatnaza = splatnaza;
	}
	public double getCastkazaca() {
		return castkazaca;
	}
	public void setCastkazaca(double castkazaca) {
		this.castkazaca = castkazaca;
	}
	public Date getUhrazenaza() {
		return uhrazenaza;
	}
	public void setUhrazenaza(Date uhrazenaza) {
		this.uhrazenaza = uhrazenaza;
	}
	public double getCastkabdph() {
		return castkabdph;
	}
	public void setCastkabdph(double castkabdph) {
		this.castkabdph = castkabdph;
	}
}
