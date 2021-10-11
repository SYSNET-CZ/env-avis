package cz.sysnet.env;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;
import com.linuxense.javadbf.DBFWriter;
import com.opencsv.CSVWriter;

import cz.sysnet.env.model.Cisdod;
import cz.sysnet.env.model.Cisdod3;
import cz.sysnet.env.model.Faktura;
import cz.sysnet.env.model.Smlouva;
import cz.sysnet.env.model.Sup;

public class Utils {
	public static final String DEFAULT_CHARSET_NAME_DBF = "Cp1250";
	public static final String DEFAULT_CHARSET_NAME_OTPUT = "utf-8";
	private static final String ERR_MSG_NULLDATA = "Vstupní data jsou null";
	private static final Logger LOG = Logger.getLogger(Utils.class.getName());
	private static int dotCounter = 0;
	
	
	private Utils() {
		throw new IllegalStateException("Utility class");
	}
	
	public static boolean logChecker() {
		LOG.finest("finest message");
		LOG.fine("fine message");
		LOG.info("info message");
		LOG.warning("warning message");
		LOG.severe("severe message");
		return true;
			
	}
	
	public static String dbfToCsv(String dbfFileName) {
		return dbfToCsv(dbfFileName, null, null, null);
	}
	
	public static String dbfToCsv(String dbfFileName, String csvFileName)  {
		return dbfToCsv(dbfFileName, csvFileName, null, null);
	}
	
	public static String dbfToCsv(String dbfFileName, String csvFileName, String inCharsetName, String outCharsetName) {
		Charset charsetIn = Charset.forName(DEFAULT_CHARSET_NAME_DBF);
		Charset charsetOut = Charset.forName(DEFAULT_CHARSET_NAME_OTPUT);
		DBFReader reader = null;
		Writer writer = null;
		String out = null;
		String msg = "";
		
		try {
			if (dbfFileName == null) throw new EnvException("Chybí vstupní DBF");
			File inFile = new File(dbfFileName);
			if (!inFile.exists()) throw new EnvException("Vstupní DBF neexistuje");
			
			String outFilename = FilenameUtils.removeExtension(inFile.getAbsolutePath()) + ".csv";
			if (csvFileName == null) csvFileName = outFilename;
			File outFile = new File(csvFileName);
			
			
			if (inCharsetName != null) charsetIn = Charset.forName(inCharsetName);
			if (outCharsetName != null) charsetOut = Charset.forName(outCharsetName);
			
			reader = new DBFReader(new FileInputStream(dbfFileName), charsetIn);
			writer = new OutputStreamWriter(new FileOutputStream(csvFileName), charsetOut);
			
			List<String> rowHeader = new ArrayList<String>();
			List<String> row = null;
			
			int numberOfFields = reader.getFieldCount();		
			for (int i = 0; i < numberOfFields; i++) {
				DBFField field = reader.getField(i);
				rowHeader.add(field.getName());			
			}
			CsvUtils.writeLine(writer, rowHeader);

			Object[] rowObjects;
			
			while ((rowObjects = reader.nextRecord()) != null) {
				row = new ArrayList<String>();
				for (int i = 0; i < rowObjects.length; i++) {
					Object data = rowObjects[i];
					String dataStr = "";
					if (data != null) dataStr = data.toString();
					row.add(dataStr);				
				}
				CsvUtils.writeLine(writer, row);
				printDot();
			}
			writer.flush();
			out = outFile.getAbsolutePath();
			msg = MessageFormat.format("dbfToCsv: written file {0}", out);
			LOG.info(msg);
			
			
		} catch (Exception e) {
			msg = MessageFormat.format("dbfToCsv: {0}", e);
			LOG.severe(msg);
			out = null;
			
		} finally {
			DBFUtils.close(reader);
			DBFUtils.close(writer);
		}
		return out;	
	}
	
	public static String dbfToCsv2(String dbfFileName) {
		return dbfToCsv2(dbfFileName, null, null, null);
	}
	
	public static String dbfToCsv2(String dbfFileName, String csvFileName)  {
		return dbfToCsv2(dbfFileName, csvFileName, null, null);
	}
	
	public static String dbfToCsv2(String dbfFileName, String csvFileName, String inCharsetName, String outCharsetName) {
		Charset charsetIn = Charset.forName(DEFAULT_CHARSET_NAME_DBF);
		Charset charsetOut = Charset.forName(DEFAULT_CHARSET_NAME_OTPUT);
		DBFReader reader = null;
		CSVWriter writer = null;
		String out = null;
		
		try {
			if (dbfFileName == null) throw new EnvException("Chybí vstupní DBF");
			File inFile = new File(dbfFileName);
			if (!inFile.exists()) throw new EnvException("Vstupní DBF neexistuje");
			
			String outFilename = FilenameUtils.removeExtension(inFile.getAbsolutePath()) + ".csv";
			if (csvFileName == null) csvFileName = outFilename;
			File outFile = new File(csvFileName);
			
			
			if (inCharsetName != null) charsetIn = Charset.forName(inCharsetName);
			if (outCharsetName != null) charsetOut = Charset.forName(outCharsetName);
			
			reader = new DBFReader(new FileInputStream(dbfFileName), charsetIn);
			writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(csvFileName), charsetOut));

			int numberOfFields = reader.getFieldCount();		
			String [] rowHeaderArray = new String[numberOfFields];
			for (int i = 0; i < numberOfFields; i++) {
				DBFField field = reader.getField(i);
				rowHeaderArray[i] = field.getName();		// TODO mapování
			}
			writer.writeNext(rowHeaderArray);
			
			Object[] rowObjects = null;			
			while ((rowObjects = reader.nextRecord()) != null) {
				int rowLength = rowObjects.length;
				String[] rowArray = new String[rowLength];	
				for (int i = 0; i < rowLength; i++) {
					Object data = rowObjects[i];
					String dataStr = "";
					if (data != null) {
						String cl = data.getClass().getCanonicalName();
						if (cl.equalsIgnoreCase("java.util.Date")) {
							Date dateValue = (Date) data;
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
							dataStr = df.format(dateValue);
						} else {
							dataStr = data.toString();
						}	
					}
					rowArray[i] = dataStr; 
				}
				writer.writeNext(rowArray);
				printDot();
			}
			writer.flush();
			out = outFile.getAbsolutePath();
			LOG.info(MessageFormat.format("dbfToCsv: written file {0}", out));
			
			
		} catch (Exception e) {
			LOG.severe(MessageFormat.format("dbfToCsv: {0}", e));
			out = null;
			
		} finally {
			DBFUtils.close(reader);
			DBFUtils.close(writer);
		}
		return out;	
	}
	
	@SuppressWarnings("unchecked")
	public static String csvToDbf(String csvFilename, Class<?> cl) {
		String out = null;
		String dbfFilename = csvFilename.replace(".csv", ".DBF").replace(".CSV", ".DBF");
		List<?> objectList = CsvUtils.readBeanList(csvFilename, cl);
		if (objectList == null) { 
			LOG.severe("csvToDbf - " + csvFilename + ": Error reading input file" );
			return null;
		}
		if (objectList.isEmpty()) {
			LOG.severe("csvToDbf - " + csvFilename + ": Input file is empty" );
			return null;
		}
		if (cl.getSimpleName().equalsIgnoreCase("faktura")) {
			out = storeFakturaToDbf((List<Faktura>) objectList, dbfFilename);
			
		} else if (cl.getSimpleName().equalsIgnoreCase("cisdod")) {
			out = storeCisdodToDbf((List<Cisdod>) objectList, dbfFilename);
			
		} else if (cl.getSimpleName().equalsIgnoreCase("cisdod3")) {
			out = storeCisdod3ToDbf((List<Cisdod3>) objectList, dbfFilename);
			
		} else if (cl.getSimpleName().equalsIgnoreCase("sup")) {
			out = storeSupToDbf((List<Sup>) objectList, dbfFilename);
			
		} else {
			LOG.severe("csvToDbf - " + csvFilename + ": Invalid class '" + cl.getSimpleName() + "'" );
			return null;
		}
		return out;
	}
	
	
	public static int dbfGetRecordCount(String dbfFilename) {
		FileInputStream stream = null;
		DBFReader reader = null;
		int out = 0;
		try {
			stream = new FileInputStream(dbfFilename);
			reader = new DBFReader(stream, Charset.forName(DEFAULT_CHARSET_NAME_DBF));
			out = reader.getRecordCount();
			LOG.info(MessageFormat.format("dbfGetRecordCount: dbf {0}, recnum={1}", dbfFilename, out));
			
		} catch (Exception e) {
			LOG.severe(MessageFormat.format("getRecordCount: {0}", e));
			out = 0;
			
		} finally {
			DBFUtils.close(reader);
			DBFUtils.close(stream);
		}
		return out;
	}
	
	
	private static Sup loadSupFromRowobject(Object[] rowObjects) {
		Sup out = new Sup();
		for (int i = 0; i < rowObjects.length; i++) {
			Object data = rowObjects[i];
			String dataStr = "";
			if (data != null) dataStr = data.toString();
			if(i==0) out.setSup(dataStr);
			else if(i==1) out.setIco(dataStr);
		}
		return out;	
	}
	
	public static List<Sup> loadSupList(String dbfFilename, int fromItem, int itemCount) {
		return loadSupListFromDbf(dbfFilename, fromItem, itemCount);
	}
	
	public static List<Sup> loadSupListFromDbf(String dbfFilename, int fromItem, int itemCount) {
		List<Sup> out = null;
		DBFReader reader = null;
		FileInputStream stream = null;
		
		try {
			stream = new FileInputStream(dbfFilename);
			reader = new DBFReader(stream, Charset.forName(DEFAULT_CHARSET_NAME_DBF));
			int numberOfFields = reader.getFieldCount();
			boolean ok = false;
			List<String> fields = new ArrayList<String>();
			for (int i = 0; i < numberOfFields; i++) {
				DBFField field = reader.getField(i);
				fields.add(field.getName());				
			}
			if (fields.equals(Sup.getDbfFieldList())) ok = true;
			if (!ok) {
				DBFUtils.close(reader);
				throw new EnvException("Datová struktura neodpovídá objektu 'Sup'");
			}
			if (fromItem > 0) reader.skipRecords(fromItem);
			out = new ArrayList<Sup>();
			Object[] rowObjects;
			int j = 0;
			while (((rowObjects = reader.nextRecord()) != null) && ((j < itemCount) || (itemCount <= 0))) {
				out.add(loadSupFromRowobject(rowObjects));
				j++;
			}
			String value = dbfFilename + ", " + Integer.toString(fromItem) + ", " + Integer.toString(itemCount);
			LOG.info(MessageFormat.format("loadSupList: {0} done", value));
		} catch (Exception e) {
			LOG.severe(MessageFormat.format("loadSupList: {0}", e));
			out = null;
			
		} finally {
			DBFUtils.close(reader);
			DBFUtils.close(stream);
		}
		return out;
	}

	private static Cisdod loadCisdodFromRowobject(Object[] rowObjects) {
		Cisdod out = new Cisdod();
		for (int i = 0; i < rowObjects.length; i++) {
			Object data = rowObjects[i];
			String dataStr = "";
			if (data != null) dataStr = data.toString();
			if(i==0) out.setDodavatel(dataStr);
			else if(i==1) out.setSmlouva(dataStr);
			else if(i==2) out.setCispart(dataStr);
			else if(i==3) out.setVariab(dataStr);
			else if(i==4) out.setSpecif(dataStr);
			else if(i==5) out.setIco(dataStr);
			else if(i==6) out.setDny(Long.parseLong(dataStr));					
		}
		return out;
	}
	
	public static List<Cisdod> loadCisdodList(String dbfFilename, int fromItem, int itemCount) {
		return loadCisdodListFromDbf(dbfFilename, fromItem, itemCount);
	}
	
	public static List<Cisdod> loadCisdodListFromDbf(String dbfFilename, int fromItem, int itemCount) {
		List<Cisdod> out = null;
		DBFReader reader = null;
		
		try {
			reader = new DBFReader(new FileInputStream(dbfFilename), Charset.forName(DEFAULT_CHARSET_NAME_DBF));
			int numberOfFields = reader.getFieldCount();
			boolean ok = false;
			List<String> fields = new ArrayList<String>();
			for (int i = 0; i < numberOfFields; i++) {
				DBFField field = reader.getField(i);
				fields.add(field.getName());				
			}
			if (fields.equals(Cisdod.getDbfFieldList())) ok = true;
			if (!ok) {
				DBFUtils.close(reader);
				throw new EnvException("Datová struktura neodpovídá objektu 'Cisdod'");
			}
			if (fromItem > 0) reader.skipRecords(fromItem);
			out = new ArrayList<Cisdod>();
			Object[] rowObjects;
			int j = 0;
			while (((rowObjects = reader.nextRecord()) != null) && ((j < itemCount) || (itemCount <= 0))) {
				out.add(loadCisdodFromRowobject(rowObjects));
				j++;
			}
		} catch (Exception e) {
			LOG.severe(MessageFormat.format("loadCisdodList: {0}", e));
			out = null;
			
		} finally {
			DBFUtils.close(reader);
		}
		return out;
	}
	
	private static Cisdod3 loadCisdod3FromRowobject(Object[] rowObjects) {
		Cisdod3 out = new Cisdod3();
		for (int i = 0; i < rowObjects.length; i++) {
			Object data = rowObjects[i];
			String dataStr = "";
			if (data != null) dataStr = data.toString();
			if(i==0) out.setKodproj(dataStr);
			else if(i==1) out.setDodavatel(dataStr);
			else if(i==2) out.setSmlouva(dataStr);
			else if(i==3) out.setVariab(dataStr);
			else if(i==4) out.setSpecif(dataStr);
			else if(i==5) out.setIco(dataStr);
			else if(i==6) out.setDny(Long.parseLong(dataStr));
			else if(i==7) out.setDruhfa(dataStr);
			else if(i==8) out.setProcentaza(Double.parseDouble(dataStr));
		}
		return out;
	}
	
	public static List<?> loadSupListFromCsv(String csvFilename) {
		return CsvUtils.readBeanList(csvFilename, Sup.class);
	}
	
	public static List<?> loadCisdodListFromCsv(String csvFilename) {
		return CsvUtils.readBeanList(csvFilename, Cisdod.class);
	}
	
	public static List<?> loadCisdod3ListFromCsv(String csvFilename) {
		return CsvUtils.readBeanList(csvFilename, Cisdod3.class);
	}
	
	public static List<?> loadFakturaListFromCsv(String csvFilename) {
		return CsvUtils.readBeanList(csvFilename, Faktura.class);
	}
	
	public static List<?> loadSmlouvaListFromCsv(String csvFilename) {
		return CsvUtils.readBeanList(csvFilename, Smlouva.class);
	}
	
	public static List<Cisdod3> loadCisdod3List(String dbfFilename, int fromItem, int itemCount) {
		return loadCisdod3ListFromDbf(dbfFilename, fromItem, itemCount);
	}
	
		
	public static List<Cisdod3> loadCisdod3ListFromDbf(String dbfFilename, int fromItem, int itemCount) {
		List<Cisdod3> out = null;
		DBFReader reader = null;
		
		try {
			reader = new DBFReader(new FileInputStream(dbfFilename), Charset.forName(DEFAULT_CHARSET_NAME_DBF));
			int numberOfFields = reader.getFieldCount();
			boolean ok = false;
			List<String> fields = new ArrayList<String>();
			for (int i = 0; i < numberOfFields; i++) {
				DBFField field = reader.getField(i);
				fields.add(field.getName());				
			}
			if (fields.equals(Cisdod3.getDbfFieldList())) ok = true;
			if (!ok) {
				DBFUtils.close(reader);
				throw new EnvException("Datová struktura neodpovídá objektu 'Cisdod3'");
			}
			if (fromItem > 0) reader.skipRecords(fromItem);
			out = new ArrayList<Cisdod3>();
			Object[] rowObjects;
			int j = 0;
			while (((rowObjects = reader.nextRecord()) != null) && ((j < itemCount) || (itemCount <= 0))) {
				out.add(loadCisdod3FromRowobject(rowObjects));
				j++;
			}
		} catch (Exception e) {
			LOG.severe(MessageFormat.format("loadCisdod3List: {0}", e));
			out = null;
			
		} finally {
			DBFUtils.close(reader);
		}
		return out;
	}
	
	
//	public static List<Smlouva> loadSmlouvaListFromDbf(String dbfFilename, int fromItem, int itemCount) {
//		List<Smlouva> out = null;
//		DBFReader reader = null;
//		
//		try {
//			reader = new DBFReader(new FileInputStream(dbfFilename), Charset.forName(DEFAULT_CHARSET_NAME_DBF));
//			int numberOfFields = reader.getFieldCount();
//			boolean ok = false;
//			List<String> fields = new ArrayList<String>();
//			for (int i = 0; i < numberOfFields; i++) {
//				DBFField field = reader.getField(i);
//				fields.add(field.getName());				
//			}
//			if (fields.equals(Cisdod3.getDbfFieldList())) ok = true;
//			if (!ok) {
//				DBFUtils.close(reader);
//				throw new EnvException("Datová struktura neodpovídá objektu 'Cisdod3'");
//			}
//			if (fromItem > 0) reader.skipRecords(fromItem);
//			out = new ArrayList<Smlouva>();
//			Object[] rowObjects;
//			int j = 0;
//			while (((rowObjects = reader.nextRecord()) != null) && ((j < itemCount) || (itemCount <= 0))) {
//				out.add(loadSmlouvaFromRowobject(rowObjects));
//				j++;
//			}
//		} catch (Exception e) {
//			LOG.severe(MessageFormat.format("loadCisdod3List: {0}", e));
//			out = null;
//			
//		} finally {
//			DBFUtils.close(reader);
//		}
//		return out;
//	}
//	

	public static boolean checkFakturaDbf(String dbfFilename) {
		int i = 0;
		int j = 0;
		int rcnt = 0;
		int fcnt = 0;
		DBFReader reader = null;
		FileInputStream stream = null;
		boolean out = false;
		
		
		try {
			stream = new FileInputStream(dbfFilename);
			reader = new DBFReader(stream, Charset.forName(DEFAULT_CHARSET_NAME_DBF));
			fcnt = reader.getFieldCount();
			rcnt = reader.getRecordCount();		
			String value = dbfFilename + ", rows=" + Integer.toString(rcnt);
			LOG.info(MessageFormat.format("checkFaktura: {0}", value));
			
			DBFRow dbfRow = null;
			while (((dbfRow = reader.nextRow()) != null)) {
				j++;
				Faktura fa = new Faktura();
				i = 0;
				fa.setPorcis(dbfRow.getString(i)); i++;		// 0
				fa.setDodavatel(dbfRow.getString(i)); i++;	// 1
				fa.setIcod(dbfRow.getString(i)); i++;		// 2
				fa.setSmlouva(dbfRow.getString(i)); i++;	// 3
				fa.setCispart(dbfRow.getString(i)); i++;	// 4
				fa.setCisfa(dbfRow.getString(i)); i++;		// 5
				fa.setCastka(dbfRow.getDouble(i)); i++;		// 6
				fa.setUhrada(dbfRow.getString(i)); i++;		// 7
				fa.setCuctu(dbfRow.getString(i)); i++;		// 8
				fa.setBanka(dbfRow.getString(i)); i++;		// 9
				fa.setVystavena(dbfRow.getDate(i)); i++;	// 10
				fa.setDosla(dbfRow.getDate(i)); i++;		// 11
				fa.setLikvidace(dbfRow.getDate(i)); i++;	// 12
				fa.setVrackprop(dbfRow.getDate(i)); i++;	// 13
				fa.setVariab(dbfRow.getString(i)); i++;		// 14
				fa.setSpecif(dbfRow.getString(i)); i++;		// 15
				fa.setDruhfa(dbfRow.getString(i)); i++;		// 16
				fa.setSplatna(dbfRow.getDate(i)); i++;		// 17
				fa.setUhrazena(dbfRow.getDate(i)); i++;		// 18
				fa.setVracena(dbfRow.getDate(i)); i++;		// 19
				fa.setZauctovano(dbfRow.getDate(i)); i++;	// 20
				fa.setSup(dbfRow.getString(i)); i++;		// 21
				fa.setIcos(dbfRow.getString(i)); i++;		// 22
				fa.setPu(dbfRow.getString(i)); i++;			// 23
				fa.setKodproj(dbfRow.getString(i)); i++;	// 24
				fa.setProcentaza(dbfRow.getDouble(i)); i++;	// 25
				fa.setCastkaza(dbfRow.getDouble(i)); i++;	// 26
				fa.setSplatnaza(dbfRow.getDate(i)); i++;	// 27
				fa.setCastkazaca(dbfRow.getDouble(i)); i++;	// 28
				fa.setUhrazenaza(dbfRow.getDate(i)); i++;	// 29
				fa.setCastkabdph(dbfRow.getDouble(i)); i++;	// 30		

			}					
			LOG.info(MessageFormat.format("checkFaktura: {0} OK", dbfFilename));
			out = true;			
		} catch (Exception e) {
			String msg = "ROW: " + Integer.toString(j) + "/" + Integer.toString(rcnt);
			msg += ", COL: " + Integer.toString(i) + "/" + Integer.toString(fcnt);
			msg += ": " + e.getMessage();
			LOG.severe(MessageFormat.format("checkFaktura: {0} \n\t{1}", msg, e));
			out = false;
		} finally {
			DBFUtils.close(reader);
			DBFUtils.close(stream);
		}
		return out;
	}
	
	public static List<Faktura> loadFakturaList(String dbfFilename, int fromItem, int itemCount) {
		return loadFakturaListFromDbf(dbfFilename, fromItem, itemCount);
	}
	
	public static List<Faktura> loadFakturaListFromDbf(String dbfFilename, int fromItem, int itemCount) {
		List<Faktura> out = null;
		DBFReader reader = null;
		FileInputStream stream = null; 
		int m = 0;
		int n = 0;
		int rcnt = 0;
		int fcnt = 0;
		
		try {
			stream = new FileInputStream(dbfFilename);
			reader = new DBFReader(stream, Charset.forName(DEFAULT_CHARSET_NAME_DBF));
			int numberOfFields = reader.getFieldCount();
			List<String> fields = new ArrayList<String>();
			for (int i = 0; i < numberOfFields; i++) {
				DBFField field = reader.getField(i);
				fields.add(field.getName());				
			}
			if (!fields.equals(Faktura.getDbfFieldList())) { 
				DBFUtils.close(reader);
				throw new EnvException("Datová struktura neodpovídá objektu 'Faktura'");
			}
			rcnt = reader.getRecordCount();			
			if (fromItem > 0) reader.skipRecords(fromItem);
			m = fromItem;
			out = new ArrayList<Faktura>();
			DBFRow dbfRow = null;
			int j = 0;
			while (((dbfRow = reader.nextRow()) != null) && ((j < itemCount) || (itemCount <= 0))) {
				m++;
				Faktura row = new Faktura();
				n = 0;				
				row.setPorcis(dbfRow.getString(n)); n++;		// 0
				row.setDodavatel(dbfRow.getString(n)); n++;		// 1
				row.setIcod(dbfRow.getString(n)); n++;			// 2
				row.setSmlouva(dbfRow.getString(n)); n++;		// 3
				row.setCispart(dbfRow.getString(n)); n++;		// 4
				row.setCisfa(dbfRow.getString(n)); n++;			// 5
				row.setCastka(dbfRow.getDouble(n)); n++;		// 6
				row.setUhrada(dbfRow.getString(n)); n++;		// 7
				row.setCuctu(dbfRow.getString(n)); n++;			// 8
				row.setBanka(dbfRow.getString(n)); n++;			// 9
				row.setVystavena(dbfRow.getDate(n)); n++;		// 10
				row.setDosla(dbfRow.getDate(n)); n++;			// 11
				row.setLikvidace(dbfRow.getDate(n)); n++;		// 12
				row.setVrackprop(dbfRow.getDate(n)); n++;		// 13
				row.setVariab(dbfRow.getString(n)); n++;		// 14
				row.setSpecif(dbfRow.getString(n)); n++;		// 15
				row.setDruhfa(dbfRow.getString(n)); n++;		// 16
				row.setSplatna(dbfRow.getDate(n)); n++;			// 17
				row.setUhrazena(dbfRow.getDate(n)); n++;		// 18
				row.setVracena(dbfRow.getDate(n)); n++;			// 19
				row.setZauctovano(dbfRow.getDate(n)); n++;		// 20
				row.setSup(dbfRow.getString(n)); n++;			// 21
				row.setIcos(dbfRow.getString(n)); n++;			// 22
				row.setPu(dbfRow.getString(n)); n++;			// 23
				row.setKodproj(dbfRow.getString(n)); n++;		// 24
				row.setProcentaza(dbfRow.getDouble(n)); n++;	// 25
				row.setCastkaza(dbfRow.getDouble(n)); n++;		// 26
				row.setSplatnaza(dbfRow.getDate(n)); n++;		// 27
				row.setCastkazaca(dbfRow.getDouble(n)); n++;	// 28
				row.setUhrazenaza(dbfRow.getDate(n)); n++;		// 29
				row.setCastkabdph(dbfRow.getDouble(n)); n++;	// 30		
				out.add(row);
				j++;
			}
		} catch (Exception e) {
			String msg = "ROW: " + Integer.toString(m) + "/" + Integer.toString(rcnt);
			msg += ", COL: " + Integer.toString(n) + "/" + Integer.toString(fcnt);
			msg += ": " + e.getMessage();
			LOG.severe(MessageFormat.format("loadFakturaList: {0}\n\t{1}", msg, e));			
			out = null;
			
		} finally {
			DBFUtils.close(reader);
			DBFUtils.close(stream);			
		}
		return out;
	}
	
	public static List<Smlouva> loadSmlouvaListFromDbf(String dbfFilename, int fromItem, int itemCount) {
		List<Smlouva> out = null;
		DBFReader reader = null;
		FileInputStream stream = null; 
		int m = 0;
		int n = 0;
		int rcnt = 0;
		int fcnt = 0;
		
		try {
			stream = new FileInputStream(dbfFilename);
			reader = new DBFReader(stream, Charset.forName(DEFAULT_CHARSET_NAME_DBF));
			int numberOfFields = reader.getFieldCount();
			List<String> fields = new ArrayList<String>();
			for (int i = 0; i < numberOfFields; i++) {
				DBFField field = reader.getField(i);
				fields.add(field.getName());				
			}
			if (!fields.equals(Faktura.getDbfFieldList())) { 
				DBFUtils.close(reader);
				throw new EnvException("Datová struktura neodpovídá objektu 'Smlouva'");
			}
			rcnt = reader.getRecordCount();			
			if (fromItem > 0) reader.skipRecords(fromItem);
			m = fromItem;
			out = new ArrayList<Smlouva>();
			DBFRow dbfRow = null;
			int j = 0;
			while (((dbfRow = reader.nextRow()) != null) && ((j < itemCount) || (itemCount <= 0))) {
				m++;
				Smlouva row = new Smlouva();
				n = 0;				
				row.setCisloSmlouvy(dbfRow.getString(n)); n++;		// 0
				row.setTyp(dbfRow.getString(n)); n++;				// 1
				row.setZakladniSmlouva(dbfRow.getString(n)); n++;	// 2
				row.setPredmet(dbfRow.getString(n)); n++;			// 3
				row.setDny(dbfRow.getInt(n)); n++;					// 4
				row.setDruhFaktury(dbfRow.getString(n)); n++;		// 5
				row.setKodProjektu(dbfRow.getString(n)); n++;		// 6
				row.setSazbaDph(dbfRow.getDouble(n)); n++;			// 7
				row.setCastka(dbfRow.getDouble(n)); n++;			// 8
				row.setProcentoZad(dbfRow.getDouble(n)); n++;		// 9
				row.setVarSymbol(dbfRow.getString(n)); n++;			// 10
				row.setSpecSymbol(dbfRow.getString(n)); n++;		// 11
				row.setDatumPodpisu(dbfRow.getDate(n)); n++;		// 12
				row.setIco(dbfRow.getString(n)); n++;				// 13
				row.setDodavatel(dbfRow.getString(n)); n++;			// 14
				
				out.add(row);
				j++;
			}
		} catch (Exception e) {
			String msg = "ROW: " + Integer.toString(m) + "/" + Integer.toString(rcnt);
			msg += ", COL: " + Integer.toString(n) + "/" + Integer.toString(fcnt);
			msg += ": " + e.getMessage();
			LOG.severe(MessageFormat.format("loadSmlouvaList: {0}\n\t{1}", msg, e));			
			out = null;
			
		} finally {
			DBFUtils.close(reader);
			DBFUtils.close(stream);			
		}
		return out;
	}
	
	public static String storeSupToDbf(List<Sup> objectList, String dbfFilename) {
		DBFWriter writer = null;
		FileOutputStream stream = null;
		String outFilename = null;
		try {
			if (objectList == null) throw new EnvException(ERR_MSG_NULLDATA);
			if (dbfFilename == null) dbfFilename = Sup.FILE_NAME;
			File outFile = new File(dbfFilename);
			outFilename = outFile.getAbsolutePath();
			stream = new FileOutputStream(outFilename);
			writer = new DBFWriter(stream, Charset.forName(DEFAULT_CHARSET_NAME_DBF));
			
			DBFField[] fields = new DBFField[2];			
			fields[0] = Utils.createDbField("SUP", DBFDataType.CHARACTER, 20, null);
			fields[1] = Utils.createDbField("ICO", DBFDataType.CHARACTER, 11, null);					
			writer.setFields(fields);		
			Object[] rowData = null;			
			for (Sup item:objectList) {
				rowData = new Object[2];
				rowData[0] = item.getSup();
				rowData[1] = item.getIco();
				writer.addRecord(rowData);
			}
		} catch (Exception e) {
			LOG.severe(MessageFormat.format("storeSupToDbf: {0}", e));
			outFilename = null;
			
		} finally {
			DBFUtils.close(writer);
			DBFUtils.close(stream);			
		}
		return outFilename;		
	}
	
	
	public static String storeCisdodToDbf(List<Cisdod> objectList, String dbfFilename) {
		DBFWriter writer = null;
		FileOutputStream stream = null;
		String outFilename = null;
		try {
			if (objectList == null) throw new EnvException(ERR_MSG_NULLDATA);
			if (dbfFilename == null) dbfFilename = Cisdod.FILE_NAME;
			File outFile = new File(dbfFilename);
			outFilename = outFile.getAbsolutePath();
			
			stream = new FileOutputStream(outFilename);
			writer = new DBFWriter(stream, Charset.forName(DEFAULT_CHARSET_NAME_DBF));
			DBFField[] fields = new DBFField[7];
			
			fields[0] = Utils.createDbField("DODAVATEL", DBFDataType.CHARACTER, 20, null);
			fields[1] = Utils.createDbField("SMLOUVA", DBFDataType.CHARACTER, 5, null);
			fields[2] = Utils.createDbField("CISPART", DBFDataType.CHARACTER, 2, null);
			fields[3] = Utils.createDbField("VARIAB", DBFDataType.CHARACTER, 9, null);
			fields[4] = Utils.createDbField("SPECIF", DBFDataType.CHARACTER, 10, null);
			fields[5] = Utils.createDbField("ICO", DBFDataType.CHARACTER, 12, null);
			fields[6] = Utils.createDbField("DNY", DBFDataType.NUMERIC, 3, 0);
									
			writer.setFields(fields);
			
			Object[] rowData = null;			
			for (Cisdod item:objectList) {
				rowData = new Object[7];
				rowData[0] = item.getDodavatel();
				rowData[1] = item.getSmlouva();
				rowData[2] = item.getCispart();
				rowData[3] = item.getVariab();
				rowData[4] = item.getSpecif();
				rowData[5] = item.getIco();
				rowData[6] = item.getDny();
				writer.addRecord(rowData);
			}
		} catch (Exception e) {
			LOG.severe(MessageFormat.format("storeCisdodToDbf: {0}", e));
			outFilename = null;
			
		} finally {
			DBFUtils.close(writer);
			DBFUtils.close(stream);			
		}
		return outFilename;		
	}

	
	public static String storeCisdod3ToDbf(List<Cisdod3> objectList, String dbfFilename) {
		DBFWriter writer = null;
		FileOutputStream stream = null;
		
		String outFilename = null;
		try {
			if (objectList == null) throw new EnvException(ERR_MSG_NULLDATA);
			if (dbfFilename == null) dbfFilename = Cisdod3.FILE_NAME;
			File outFile = new File(dbfFilename);
			outFilename = outFile.getAbsolutePath();
			stream = new FileOutputStream(outFilename);
			writer = new DBFWriter(stream, Charset.forName(DEFAULT_CHARSET_NAME_DBF));
			DBFField[] fields = new DBFField[9];
			
			fields[0] = Utils.createDbField("KODPROJ", DBFDataType.CHARACTER, 21, null);
			fields[1] = Utils.createDbField("DODAVATEL", DBFDataType.CHARACTER, 20, null);
			fields[2] = Utils.createDbField("SMLOUVA", DBFDataType.CHARACTER, 5, null);
			fields[3] = Utils.createDbField("VARIAB", DBFDataType.CHARACTER, 9, null);
			fields[4] = Utils.createDbField("SPECIF", DBFDataType.CHARACTER, 10, null);
			fields[5] = Utils.createDbField("ICO", DBFDataType.CHARACTER, 12, null);
			fields[6] = Utils.createDbField("DNY", DBFDataType.NUMERIC, 3, 0);
			fields[7] = Utils.createDbField("DRUHFA", DBFDataType.CHARACTER, 1, null);
			fields[8] = Utils.createDbField("PROCENTAZA", DBFDataType.NUMERIC, 2, 0);
			
									
			writer.setFields(fields);
			
			Object[] rowData = null;			
			for (Cisdod3 item:objectList) {
				rowData = new Object[9];
				rowData[0] = item.getKodproj();
				rowData[1] = item.getDodavatel();
				rowData[2] = item.getSmlouva();
				rowData[3] = item.getVariab();
				rowData[4] = item.getSpecif();
				rowData[5] = item.getIco();
				rowData[6] = item.getDny();
				rowData[7] = item.getDruhfa();
				rowData[8] = item.getProcentaza();
				
				writer.addRecord(rowData);
			}
		} catch (Exception e) {
			LOG.severe(MessageFormat.format("storeCisdod3ToDbf: {0}", e));
			outFilename = null;
			
		} finally {
			DBFUtils.close(writer);
			DBFUtils.close(stream);			
		}
		return outFilename;		
	}
	
	public static String storeSmlouvaToDbf(List<Smlouva> objectList, String dbfFilename) {
		DBFWriter writer = null;
		FileOutputStream stream = null;
		
		String outFilename = null;
		try {
			if (objectList == null) throw new EnvException(ERR_MSG_NULLDATA);
			if (dbfFilename == null) dbfFilename = Smlouva.FILE_NAME;
			File outFile = new File(dbfFilename);
			outFilename = outFile.getAbsolutePath();
			stream = new FileOutputStream(outFilename);
			writer = new DBFWriter(stream, Charset.forName(DEFAULT_CHARSET_NAME_DBF));
			DBFField[] fields = new DBFField[15];
			
			// "CISLOSML", "TYP", "ZAKLSML", "PREDMET", "DNY", "DRUHFA",		
			fields[0] = Utils.createDbField("CISLOSML", DBFDataType.CHARACTER, 5, null);
			fields[1] = Utils.createDbField("TYP", DBFDataType.CHARACTER, 1, null);
			fields[2] = Utils.createDbField("ZAKLSML", DBFDataType.CHARACTER, 5, null);
			fields[3] = Utils.createDbField("PREDMET", DBFDataType.CHARACTER, 32, null);
			fields[4] = Utils.createDbField("DNY", DBFDataType.NUMERIC, 6, 0);
			fields[5] = Utils.createDbField("DRUHFA", DBFDataType.CHARACTER, 1, null);

			// "KODPROJ", "SAZBADPH", "CASTKA", "PROCENTOZA", "VARSYMBOL",			
			fields[6] = Utils.createDbField("KODPROJ", DBFDataType.CHARACTER, 21, null);
			fields[7] = Utils.createDbField("SAZBADPH", DBFDataType.NUMERIC, 4, 2);
			fields[8] = Utils.createDbField("CASTKA", DBFDataType.NUMERIC, 12, 2);
			fields[9] = Utils.createDbField("PROCENTOZA", DBFDataType.NUMERIC, 2, 0);
			fields[10] = Utils.createDbField("VARSYMBOL", DBFDataType.CHARACTER, 9, null);
			
			// "SPECSYMBOL", "DATPODPIS", "ICO", DODAVATEL
			fields[11] = Utils.createDbField("SPECSYMBOL", DBFDataType.CHARACTER, 10, null);
			fields[12] = Utils.createDbField("DATPODPIS", DBFDataType.DATE, 8, null);
			fields[13] = Utils.createDbField("ICO", DBFDataType.CHARACTER, 12, null);
			fields[14] = Utils.createDbField("DODAVATEL", DBFDataType.CHARACTER, 20, null);
									
			writer.setFields(fields);
			
			Object[] rowData = null;			
			for (Smlouva item:objectList) {
				rowData = new Object[15];
				rowData[0] = item.getCisloSmlouvy();
				rowData[1] = item.getTyp();
				rowData[2] = item.getZakladniSmlouva();
				rowData[3] = item.getPredmet();
				rowData[4] = item.getDny();
				rowData[5] = item.getDruhFaktury();
				rowData[6] = item.getKodProjektu();
				rowData[7] = item.getSazbaDph();
				rowData[8] = item.getCastka();
				rowData[9] = item.getProcentoZad();
				rowData[10] = item.getVarSymbol();
				rowData[11] = item.getSpecSymbol();
				rowData[12] = item.getDatumPodpisu();
				rowData[13] = item.getIco();
				rowData[14] = item.getDodavatel();
				
				writer.addRecord(rowData);
			}
		} catch (Exception e) {
			LOG.severe(MessageFormat.format("storeSmlouvaToDbf: {0}", e));
			outFilename = null;
			
		} finally {
			DBFUtils.close(writer);
			DBFUtils.close(stream);			
		}
		return outFilename;		
	}
	
	
	
	public static String storeFakturaToDbf(List<Faktura> objectList, String dbfFilename) {
		DBFWriter writer = null;
		FileOutputStream stream = null;
		
		String outFilename = null;
		try {
			if (objectList == null) throw new EnvException(ERR_MSG_NULLDATA);
			if (dbfFilename == null) dbfFilename = Faktura.FILE_NAME;
			File outFile = new File(dbfFilename);
			outFilename = outFile.getAbsolutePath();
			stream = new FileOutputStream(outFilename);
			writer = new DBFWriter(stream, Charset.forName(DEFAULT_CHARSET_NAME_DBF));
			DBFField[] fields = new DBFField[31];
						
			fields[0] = Utils.createDbField("PORCIS", DBFDataType.CHARACTER, 8, null);
			fields[1] = Utils.createDbField("DODAVATEL", DBFDataType.CHARACTER, 20, null);
			fields[2] = Utils.createDbField("ICOD", DBFDataType.CHARACTER, 11, null);
			fields[3] = Utils.createDbField("SMLOUVA", DBFDataType.CHARACTER, 4, null);
			fields[4] = Utils.createDbField("CISPART", DBFDataType.CHARACTER, 2, null);
			fields[5] = Utils.createDbField("CISFA", DBFDataType.CHARACTER, 10, null);
			fields[6] = Utils.createDbField("CASTKA", DBFDataType.NUMERIC, 12, 2);
			
			fields[7] = Utils.createDbField("UHRADA", DBFDataType.CHARACTER, 2, null);
			fields[8] = Utils.createDbField("CUCTU", DBFDataType.CHARACTER, 20, null);
			fields[9] = Utils.createDbField("BANKA", DBFDataType.CHARACTER, 4, null);
			fields[10] = Utils.createDbField("VYSTAVENA", DBFDataType.DATE, 8, null);
			fields[11] = Utils.createDbField("DOSLA", DBFDataType.DATE, 8, null);
			fields[12] = Utils.createDbField("LIKVIDACE", DBFDataType.DATE, 8, null);
			fields[13] = Utils.createDbField("VRACKPROP", DBFDataType.DATE, 8, null);

			fields[14] = Utils.createDbField("VARIAB", DBFDataType.CHARACTER, 9, null);
			fields[15] = Utils.createDbField("SPECIF", DBFDataType.CHARACTER, 10, null);
			fields[16] = Utils.createDbField("DRUHFA", DBFDataType.CHARACTER, 1, null);
			fields[17] = Utils.createDbField("SPLATNA", DBFDataType.DATE, 8, null);
			fields[18] = Utils.createDbField("UHRAZENA", DBFDataType.DATE, 8, null);
			fields[19] = Utils.createDbField("VRACENA", DBFDataType.DATE, 8, null);
			fields[20] = Utils.createDbField("ZAUCTOVANO", DBFDataType.DATE, 8, null);
			
			fields[21] = Utils.createDbField("SUP", DBFDataType.CHARACTER, 20, null);
			fields[22] = Utils.createDbField("ICOS", DBFDataType.CHARACTER, 11, null);
			fields[23] = Utils.createDbField("PU", DBFDataType.CHARACTER, 1, null);
			fields[24] = Utils.createDbField("KODPROJ", DBFDataType.CHARACTER, 21, null);
			fields[25] = Utils.createDbField("PROCENTAZA", DBFDataType.NUMERIC, 2, 0);
			fields[26] = Utils.createDbField("CASTKAZA", DBFDataType.NUMERIC, 12, 2);
			fields[27] = Utils.createDbField("SPLATNAZA", DBFDataType.DATE, 8, null);
			fields[28] = Utils.createDbField("CASTKAZACA", DBFDataType.NUMERIC, 12, 2);
			fields[29] = Utils.createDbField("UHRAZENAZA", DBFDataType.DATE, 8, null);
			fields[30] = Utils.createDbField("CASTKABDPH", DBFDataType.NUMERIC, 12, 2);
			
			writer.setFields(fields);			

			Object[] rowData = null;			
			for (Faktura item:objectList) {
				rowData = new Object[31];
				rowData[0] = item.getPorcis();
				rowData[1] = item.getDodavatel();
				rowData[2] = item.getIcod();
				rowData[3] = item.getSmlouva();
				rowData[4] = item.getCispart();
				rowData[5] = item.getCisfa();
				rowData[6] = item.getCastka();
				
				rowData[7] = item.getUhrada();
				rowData[8] = item.getCuctu();
				rowData[9] = item.getBanka();
				rowData[10] = item.getVystavena();
				rowData[11] = item.getDosla();
				rowData[12] = item.getLikvidace();
				rowData[13] = item.getVrackprop();
				
				
				rowData[14] = item.getVariab();
				rowData[15] = item.getSpecif();
				rowData[16] = item.getDruhfa();
				rowData[17] = item.getSplatna();
				rowData[18] = item.getUhrazena();
				rowData[19] = item.getVracena();
				rowData[20] = item.getZauctovano();
				
				rowData[21] = item.getSup();
				rowData[22] = item.getIcos();
				rowData[23] = item.getPu();
				rowData[24] = item.getKodproj();
				rowData[25] = item.getProcentaza();
				rowData[26] = item.getCastkaza();
				rowData[27] = item.getSplatnaza();
				rowData[28] = item.getCastkazaca();
				rowData[29] = item.getUhrazenaza();
				rowData[30] = item.getCastkabdph();
				
				writer.addRecord(rowData);
			}
		} catch (Exception e) {
			LOG.severe(MessageFormat.format("storeFakturaToDbf: {0}", e));
			outFilename = null;
			
		} finally {
			DBFUtils.close(writer);
			DBFUtils.close(stream);			
		}
		return outFilename;		
	}

	
	public static DBFField createDbField(String fieldName, DBFDataType fieldType, Integer length, Integer decimal) {
		DBFField field = new DBFField();
		field.setName(fieldName);
		field.setType(fieldType);
		if (fieldType == DBFDataType.CHARACTER) {
			field.setLength(length.intValue());
			
		} else if (fieldType == DBFDataType.NUMERIC) {
			field.setLength(length.intValue());
			if (decimal != null) field.setDecimalCount(decimal.intValue());
		}
		return field;
	}
	
	
	private static void printDot() {
		if (dotCounter == 99) {
			System.out.println(".");
			dotCounter = 0;
		}
		else {
			System.out.print(".");
			dotCounter++;
		}		
	}
	
	public static String hashMd5(String value) {
		return hash(value, null);
	}
	
	public static String hashSha1(String value) {
		return hash(value, "SHA-1");
	}
	
	public static String hashSha256(String value) {
		return hash(value, "SHA-256");
	}
	
	
	public static String hash(String value, String algorithm) {
		//MD5, SHA-1, SHA-256
		StringBuilder stringBuilder = null;
		if (algorithm == null) algorithm = "MD5";
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			byte[] array = md.digest(value.getBytes());
			stringBuilder = new StringBuilder();
			for (int i = 0; i < array.length; ++i) {
				stringBuilder.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
			}
			return stringBuilder.toString();
		} catch (NoSuchAlgorithmException e) {			
			LOG.severe(MessageFormat.format("hash: {0}", e));
		}
		return null;
	}
	
	public static void printAlhoritms() {
		Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            showHashAlgorithms(provider, MessageDigest.class);
        }	
	}
	
	
	private static final void showHashAlgorithms(Provider prov, Class<?> typeClass) {
        String type = typeClass.getSimpleName();

        List<Service> algos = new ArrayList<Service>();

        Set<Service> services = prov.getServices();
        for (Service service : services) {
            if (service.getType().equalsIgnoreCase(type)) {
                algos.add(service);
            }
        }

        if (!algos.isEmpty()) {
        	String msg = String.format(" --- Provider %s, version %.2f --- %n", prov.getName(), prov.getVersion());
        	LOG.info(msg);
            for (Service service : algos) {
                String algo = service.getAlgorithm();
                msg = String.format("Algorithm name: \"%s\"%n", algo);
                LOG.info(msg);
            }
        }

        // --- find aliases (inefficiently)
        Set<Object> keys = prov.keySet();
        for (Object key : keys) {
            final String prefix = "Alg.Alias." + type + ".";
            if (key.toString().startsWith(prefix)) {
                String value = prov.get(key.toString()).toString();
                String msg = String.format("Alias: \"%s\" -> \"%s\"%n", key.toString().substring(prefix.length()), value);
                LOG.info(msg);
            }
        }
    }
	
	
	public static String storeCisdod3BeanToCsv(List<Cisdod3> objectList, String csvFilename) {
		String out = CsvUtils.writeBeanList(csvFilename, objectList);
		return out;
	}
	
	public static String storeSmlouvaBeanToCsv(List<Smlouva> objectList, String csvFilename) {
		String out = CsvUtils.writeBeanList(csvFilename, objectList);
		return out;
	}
	
	public static String storeCisdodBeanToCsv(List<Cisdod> objectList, String csvFilename) {
		String out = CsvUtils.writeBeanList(csvFilename, objectList);
		return out;
	}
	
	public static String storeSupBeanToCsv(List<Sup> objectList, String csvFilename) {
		String out = CsvUtils.writeBeanList(csvFilename, objectList);
		return out;
	}
	
	public static String storeFakturaBeanToCsv(List<Faktura> objectList, String csvFilename) {
		String out = CsvUtils.writeBeanList(csvFilename, objectList);
		return out;
	}
	
	public static String storeCisdod3ToCsv(List<Cisdod3> objectList, String csvFilename) {
		String dbfFilename = csvFilename.replace(".csv", ".DBF").replace(".CSV", ".DBF");
		String out = storeCisdod3ToDbf(objectList, dbfFilename);
		if (out != null) {
			out = dbfToCsv2(dbfFilename, csvFilename);
			if (out != null) deleteFile(dbfFilename);
		}
		return out;
	}
	
	public static String storeSmlouvaToCsv(List<Smlouva> objectList, String csvFilename) {
		return CsvUtils.writeSmlouvaList(csvFilename, objectList);
	}
	
	
	public static String storeCisdodToCsv(List<Cisdod> objectList, String csvFilename) {
		String dbfFilename = csvFilename.replace(".csv", ".DBF").replace(".CSV", ".DBF");
		String out = storeCisdodToDbf(objectList, dbfFilename);
		if (out != null) {
			out = dbfToCsv2(dbfFilename, csvFilename);
			if (out != null) deleteFile(dbfFilename);
		}
		return out;
	}	

	public static String storeSupToCsv(List<Sup> objectList, String csvFilename) {
		String dbfFilename = csvFilename.replace(".csv", ".DBF").replace(".CSV", ".DBF");
		String out = storeSupToDbf(objectList, dbfFilename);
		if (out != null) {
			out = dbfToCsv2(dbfFilename, csvFilename);
			if (out != null) deleteFile(dbfFilename);
		}
		return out;
	}	
	
	private static boolean deleteFile(String fileName) {
		boolean out = false;
		try {
			File file = new File(fileName);
			out = file.delete();
			
		} catch (Exception e) {
			out = false;
		}
		return out;
	}

	public static String getFileChecksumSha256(String fileName) {
		File file = new File(fileName);
		return getFileChecksum(file, "SHA-256");
	}
	
	public static String getFileChecksumSha256(File file) {
		return getFileChecksum(file, "SHA-256");
	}

	public static String getFileChecksumSha1(String fileName) {
		File file = new File(fileName);
		return getFileChecksum(file, "SHA-1");
	}
	
	public static String getFileChecksumSha1(File file) {
		return getFileChecksum(file, "SHA-1");
	}
	
	public static String getFileChecksumMd5(String fileName) {
		File file = new File(fileName);
		return getFileChecksum(file, "MD5");
	}
	
	public static String getFileChecksumMd5(File file) {
		return getFileChecksum(file, "MD5");
	}
	
	public static String getFileChecksum(String fileName, String algorithm) {
		File file = new File(fileName);
		return getFileChecksum(file, algorithm);
	}
	
	public static String getFileChecksum(File file, String algorithm) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);			
		    FileInputStream fis = new FileInputStream(file);
		    byte[] byteArray = new byte[1024];
		    int bytesCount = 0; 
		    while ((bytesCount = fis.read(byteArray)) != -1) {
		        digest.update(byteArray, 0, bytesCount);
		    };
		    fis.close();
		     
		    byte[] bytes = digest.digest();
		    StringBuilder sb = new StringBuilder();
		    for(int i=0; i< bytes.length ;i++) {
		        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		    }
		    LOG.info(MessageFormat.format("getFileChecksum - {0}: {1} ({2})", file.getAbsolutePath(), sb.toString(), algorithm));
			return sb.toString();
			
		} catch (IOException e) {
			LOG.severe(MessageFormat.format("getFileChecksum: {0}", e));
			// e.printStackTrace();
			return null;
			
		} catch (NoSuchAlgorithmException e) {
			LOG.severe(MessageFormat.format("getFileChecksum: {0}", e));
			// e.printStackTrace();
			return null;
		}
	}
}
