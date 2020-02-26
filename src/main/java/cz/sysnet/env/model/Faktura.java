package cz.sysnet.env.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import cz.sysnet.env.Utils;

public class Faktura implements Serializable {
	private static final long serialVersionUID = 1L;

	public static String FILE_NAME = "FaktLN.dbf";
	public static List<String> DBF_FIELD_LIST = 
			new ArrayList<String>(Arrays.asList(
					"PORCIS", "DODAVATEL", "ICOD", "SMLOUVA", "CISPART", "CISFA", "CASTKA", 
					"UHRADA", "CUCTU", "BANKA", "VYSTAVENA", "DOSLA", "LIKVIDACE", "VRACKPROP", 
					"VARIAB", "SPECIF", "DRUHFA", "SPLATNA", "UHRAZENA", "VRACENA", "ZAUCTOVANO", 
					"SUP", "ICOS", "PU", "KODPROJ", "PROCENTAZA", "CASTKAZA", "SPLATNAZA", "CASTKAZACA", 
					"UHRAZENAZA", "CASTKABDPH"
					));
	
	String porcis;     	//Pořadové číslo faktury v AVISme
	String dodavatel;   //Název dodavatele
	String icod;     	//IČO dodavatele
	String smlouva;     //Číslo ekologické smlouvy
	String cispart;     //Pořadí partnera
	String cisfa;     	//Číslo faktury dodavatele
	double castka;     	//Částka (v Kč)
	String uhrada;     	//Forma úhrady (PU – příkaz k úhradě, DB – dobropis)
	String cuctu;     	//Číslo bankovního účtu dodavatele
	String banka;    	//Kód banky dodavatele
	Date vystavena;     //Datum vystavení faktury      
	Date dosla;     	//Datum přijetí faktury      
	Date likvidace;     //Datum likvidace faktury      
	Date vrackprop;     //Datum vrácení k proplacení         
	String variab;     	//Variabilní symbol
	String specif;     	//Specifický symbol
	String druhfa;     	//Druh faktury (A – analýza, B – aktualizovaná analýza rizik, K – kon, O – oponentura, S – sanace, V – supervize, X – různé)
	Date splatna;     	//Datum splatnosti         
	Date uhrazena;     	//Datum uhrazení         
	Date vracena;     	//Datum vrácení
	Date zauctovano;    //Datum zaúčtování
	String sup;     	//Název supervizora 
	String icos;     	//IČO supervizora
	String pu;     		//Stav příkazu k úhradě (1= uhrazeno)
	String kodproj;     //Kód smlouvy
	double procentaza;  //Procento zádržného (v %)
	double castkaza;    //Částka zádržného (v Kč)
	Date splatnaza;     //Datum splatnosti zádržného
	double castkazaca;  //Částka zádržného (v Kč)
	Date uhrazenaza;    //Datum uhrazení zádržného
	double castkabdph;  //Částka bez DPH (v Kč)
	
	
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
