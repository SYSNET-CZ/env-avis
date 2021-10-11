package cz.sysnet.env;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import cz.sysnet.env.model.Cisdod;
import cz.sysnet.env.model.Cisdod3;
import cz.sysnet.env.model.Faktura;
import cz.sysnet.env.model.Smlouva;
import cz.sysnet.env.model.Sup;

public class CsvFactory {
	private static final Logger LOG = Logger.getLogger(CsvFactory.class.getName());
	private BufferedReader csvReader = null;
	private Iterable<CSVRecord> csvRecords = null;
	
	private CsvFactory() {
		
	}
	
	private static class Dummy {
		private static final CsvFactory INSTANCE = new CsvFactory();
	}
	
	public static CsvFactory getInstance() {
		return Dummy.INSTANCE;
	}
	
	public Iterable<CSVRecord> csvReadRecordsIterable(String csvFilename, String[] header) {
		this.csvRecords = null;
		try {
			this.csvReader = new BufferedReader(new InputStreamReader(new FileInputStream(csvFilename), "UTF-8"));
		    this.csvRecords = CSVFormat.DEFAULT
			  	      .withHeader(header)
			  	      .withFirstRecordAsHeader()
			  	      .parse(this.csvReader);
			   			
		} catch (Exception e) {
			LOG.severe(MessageFormat.format("csvReadRecordsIterable: {0}", e));
			this.csvRecords = null;			
		}
		
		return this.csvRecords;
	}
	
	public void recycle() {
		try {			
			if (this.csvReader != null) this.csvReader.close();
			this.csvReader = null;
			this.csvRecords = null;
		} catch (IOException e) {
			LOG.severe(MessageFormat.format("recycle: {0}", e));
			e.printStackTrace();
		}
	}
	
	public boolean isRecycled() {
		boolean out = false;
		if ((this.csvReader == null) && (this.csvRecords == null)) out = true;
		return out;
	}
	
	public List<Sup> csvReadSupList(String csvFilename) {
		this.csvReadRecordsIterable(csvFilename, Sup.CSV_HEADER);
		if(this.csvRecords == null) return null;
		List<Sup> outList = new ArrayList<Sup>();
		for (CSVRecord record : this.csvRecords) {
	    	Sup out = csvRecordToSup(record);
	    	outList.add(out);
	    }
		this.recycle();
    	return outList;
	}
	
	public List<Cisdod> csvReadCisdodList(String csvFilename) {
		this.csvReadRecordsIterable(csvFilename, Cisdod.CSV_HEADER);
		if(this.csvRecords == null) return null;
		List<Cisdod> outList = new ArrayList<Cisdod>();
		for (CSVRecord record : this.csvRecords) {
	    	Cisdod out = csvRecordToCisdod(record);
	    	outList.add(out);
	    }
		this.recycle();
    	return outList;
	}

	public List<Cisdod3> csvReadCisdod3List(String csvFilename) {
		this.csvReadRecordsIterable(csvFilename, Cisdod3.CSV_HEADER);
		if(this.csvRecords == null) return null;
		List<Cisdod3> outList = new ArrayList<Cisdod3>();
		for (CSVRecord record : this.csvRecords) {
	    	Cisdod3 out = csvRecordToCisdod3(record);
	    	outList.add(out);
	    }
		this.recycle();
    	return outList;
	}
	
	public List<Smlouva> csvReadSmlouvaList(String csvFilename) {
		this.csvReadRecordsIterable(csvFilename, Smlouva.CSV_HEADER);
		if(this.csvRecords == null) return null;
		List<Smlouva> outList = new ArrayList<Smlouva>();
		for (CSVRecord record : this.csvRecords) {
			Smlouva out = csvRecordToSmlouva(record);
	    	outList.add(out);
	    }
		this.recycle();
    	return outList;
	}
	
	
	public List<Faktura> csvReadFakturaList(String csvFilename) {
		this.csvReadRecordsIterable(csvFilename, Faktura.CSV_HEADER);
		if(this.csvRecords == null) return null;
		List<Faktura> outList = new ArrayList<Faktura>();
		for (CSVRecord record : this.csvRecords) {
			Faktura out = csvRecordToFaktura(record);
	    	outList.add(out);
	    }
		this.recycle();
    	return outList;
	}
	
	public static Sup csvRecordToSup(CSVRecord record) {
		if (record == null) return null;
		Sup out = null;
		try {
			out = new Sup();
	    	// "SUP", "ICO"
	    	out.setSup(record.get(Sup.CSV_HEADER[0]));
	    	out.setIco(record.get(Sup.CSV_HEADER[1]));
	    	
		} catch (Exception e) {
			LOG.severe(MessageFormat.format("csvRecordToSup: {0}", e));
			out = null;
		}
		return out;
	}

	public static Cisdod csvRecordToCisdod(CSVRecord record) {
		if (record == null) return null;
		Cisdod out = null;
		try {
			out = new Cisdod();
	    	// "DODAVATEL", "SMLOUVA", "CISPART", "VARIAB", "SPECIF", "ICO", "DNY"
	    	out.setDodavatel(record.get(Cisdod.CSV_HEADER[0]));
	    	out.setSmlouva(record.get(Cisdod.CSV_HEADER[1]));
	    	out.setCispart(record.get(Cisdod.CSV_HEADER[2]));
	    	out.setVariab(record.get(Cisdod.CSV_HEADER[3]));
	    	out.setSpecif(record.get(Cisdod.CSV_HEADER[4]));
	    	out.setIco(record.get(Cisdod.CSV_HEADER[5]));
	    	out.setDny(StringToLong(Cisdod.CSV_HEADER[6]));
			
		} catch (Exception e) {
			LOG.severe(MessageFormat.format("csvRecordToCisdod: {0}", e));
			out = null;
		}
		return out;
	}
	
	public static Cisdod3 csvRecordToCisdod3(CSVRecord record) {
		if (record == null) return null;
		Cisdod3 out = null;
		try {
			out = new Cisdod3();
	    	// "KODPROJ", "DODAVATEL", "SMLOUVA", "VARIAB", "SPECIF", "ICO", "DNY" , "DRUHFA", "PROCENTAZA"
	    	out.setKodproj(record.get(Cisdod3.CSV_HEADER[0]));
	    	out.setDodavatel(record.get(Cisdod3.CSV_HEADER[1]));
	    	out.setSmlouva(record.get(Cisdod3.CSV_HEADER[2]));
	    	out.setVariab(record.get(Cisdod3.CSV_HEADER[3]));
	    	out.setSpecif(record.get(Cisdod3.CSV_HEADER[4]));
	    	out.setIco(record.get(Cisdod3.CSV_HEADER[5]));
	    	out.setDny(Long.parseLong(record.get(Cisdod3.CSV_HEADER[6])));
	    	out.setDruhfa(record.get(Cisdod3.CSV_HEADER[7]));
	    	out.setProcentaza(StringToDouble(record.get(Cisdod3.CSV_HEADER[8])));
			
		} catch (Exception e) {
			LOG.severe(MessageFormat.format("csvRecordToCisdod3: {0}", e));
			out = null;
		}
		return out;
	}
	
	public static Smlouva csvRecordToSmlouva(CSVRecord record) {
		if (record == null) return null;
		Smlouva out = null;
		try {
			out = new Smlouva();
	    	// "CISLOSMLOUVY", "TYP", "ZAKLADNISMLOUVA", "PREDMET", "DNY",
			out.setCisloSmlouvy(record.get(Smlouva.CSV_HEADER[0]));
			out.setTyp(record.get(Smlouva.CSV_HEADER[1]));
			out.setZakladniSmlouva(record.get(Smlouva.CSV_HEADER[2]));
			out.setPredmet(record.get(Smlouva.CSV_HEADER[3]));
			out.setDny(Long.parseLong(record.get(Smlouva.CSV_HEADER[4])));
	    	
			// "DRUHFAKTURY", "KODPROJEKTU", "SAZBADPH", "CASTKA", "PROCENTOZAD", "VARSYMBOL",
			out.setDruhFaktury(record.get(Smlouva.CSV_HEADER[5]));
			out.setKodProjektu(record.get(Smlouva.CSV_HEADER[6]));
			out.setSazbaDph(StringToDouble(record.get(Smlouva.CSV_HEADER[7])));
			out.setCastka(StringToDouble(record.get(Smlouva.CSV_HEADER[8])));
			out.setProcentoZad(StringToDouble(record.get(Smlouva.CSV_HEADER[9])));
			out.setVarSymbol(record.get(Smlouva.CSV_HEADER[10]));
			
			// "SPECSYMBOL", "DATUMPODPISU", "ICO", "DODAVATEL"
			out.setSpecSymbol(record.get(Smlouva.CSV_HEADER[11]));
			out.setDatumPodpisu(StringToDate(record.get(Faktura.CSV_HEADER[12])));
			out.setIco(record.get(Smlouva.CSV_HEADER[13]));
			out.setDodavatel(record.get(Smlouva.CSV_HEADER[14]));
			
		} catch (Exception e) {
			LOG.severe(MessageFormat.format("csvRecordToSmlouva: {0}", e));
			out = null;
		}
		return out;
	}
	
	
	
	
	public static Faktura csvRecordToFaktura(CSVRecord record) {
		if (record == null) return null;
		Faktura out = null;
		try {
			out = new Faktura();
	    	// 	"PORCIS", "DODAVATEL", "ICOD", "SMLOUVA", "CISPART", "CISFA", "CASTKA", 
	    	out.setPorcis(record.get(Faktura.CSV_HEADER[0]));
	    	out.setDodavatel(record.get(Faktura.CSV_HEADER[1]));
	    	out.setIcod(record.get(Faktura.CSV_HEADER[2]));
	    	out.setSmlouva(record.get(Faktura.CSV_HEADER[3]));
	    	out.setCispart(record.get(Faktura.CSV_HEADER[4]));
	    	out.setCisfa(record.get(Faktura.CSV_HEADER[5]));
	    	out.setCastka(StringToDouble(record.get(Faktura.CSV_HEADER[6])));
	    	
	    	//	"UHRADA", "CUCTU", "BANKA", "VYSTAVENA", "DOSLA", "LIKVIDACE", "VRACKPROP", 
	    	out.setUhrada(record.get(Faktura.CSV_HEADER[7]));
	    	out.setCuctu(record.get(Faktura.CSV_HEADER[8]));
	    	out.setBanka(record.get(Faktura.CSV_HEADER[9]));
	    	out.setVystavena(StringToDate(record.get(Faktura.CSV_HEADER[10])));
	    	out.setDosla(StringToDate(record.get(Faktura.CSV_HEADER[11])));
	    	out.setLikvidace(StringToDate(record.get(Faktura.CSV_HEADER[12])));
	    	out.setVrackprop(StringToDate(record.get(Faktura.CSV_HEADER[13])));

	    	//	"VARIAB", "SPECIF", "DRUHFA", "SPLATNA", "UHRAZENA", "VRACENA", "ZAUCTOVANO", 
	    	out.setVariab(record.get(Faktura.CSV_HEADER[14]));
	    	out.setSpecif(record.get(Faktura.CSV_HEADER[15]));
	    	out.setDruhfa(record.get(Faktura.CSV_HEADER[16]));
	    	out.setSplatna(StringToDate(record.get(Faktura.CSV_HEADER[17])));
	    	out.setUhrazena(StringToDate(record.get(Faktura.CSV_HEADER[18])));
	    	out.setVracena(StringToDate(record.get(Faktura.CSV_HEADER[19])));
	    	out.setZauctovano(StringToDate(record.get(Faktura.CSV_HEADER[20])));
	    	
	    	//	"SUP", "ICOS", "PU", "KODPROJ", "PROCENTAZA", "CASTKAZA", "SPLATNAZA", "CASTKAZACA", "UHRAZENAZA", "CASTKABDPH"
	    	out.setSup(record.get(Faktura.CSV_HEADER[21]));
	    	out.setIcos(record.get(Faktura.CSV_HEADER[22]));
	    	out.setPu(record.get(Faktura.CSV_HEADER[23]));
	    	out.setKodproj(record.get(Faktura.CSV_HEADER[24]));
	    	out.setProcentaza(StringToDouble(record.get(Faktura.CSV_HEADER[25])));
	    	out.setCastkaza(StringToDouble(record.get(Faktura.CSV_HEADER[26])));
	    	out.setSplatnaza(StringToDate(record.get(Faktura.CSV_HEADER[27])));
	    	out.setCastkazaca(StringToDouble(record.get(Faktura.CSV_HEADER[28])));
	    	out.setUhrazenaza(StringToDate(record.get(Faktura.CSV_HEADER[29])));
	    	out.setCastkabdph(StringToDouble(record.get(Faktura.CSV_HEADER[30])));
			
		} catch (Exception e) {
			LOG.severe(MessageFormat.format("csvRecordToFaktura: {0}", e));
			e.printStackTrace();
			out = null;
		}
		return out;
	}
	
	public static double StringToDouble(String value) {
		double out = 0.0;
		if (value == null) return out;
		if (value.isEmpty()) return out;
		try {
			out = Double.parseDouble(value);
			LOG.fine(MessageFormat.format("StringToDouble: {0}, {1}", value, out));
			
		} catch (Exception e) {
			LOG.severe(MessageFormat.format("StringToDouble: {0}", e));
			out = 0.0;
		}
		return out;
	}
	
	public static long StringToLong(String value) {
		long out = 0;
		if (value == null) return out;
		if (value.isEmpty()) return out;
		try {
			out = Long.parseLong(value);
			LOG.fine(MessageFormat.format("StringToLong: {0}, {1}", value, out));

		} catch (Exception e) {
			LOG.severe(MessageFormat.format("StringToLong: {0}", e));
			out = 0;
		}
		return out;
	}
	
	public static Date StringToDate(String value) {
		Date out = null;
		if(value == null) return null;
		if(value.isEmpty()) return null;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			out = df.parse(value);
			LOG.fine(MessageFormat.format("StringToDate: {0}, {1}", value, out));

		} catch (ParseException e) {
			LOG.severe(MessageFormat.format("StringToDate: {0}", e));
			out = null;
		}
		return out;
	}
	
}
