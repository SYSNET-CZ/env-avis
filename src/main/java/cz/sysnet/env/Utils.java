package cz.sysnet.env;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFRow;
import com.linuxense.javadbf.DBFUtils;
import com.linuxense.javadbf.DBFWriter;

import cz.sysnet.env.model.Cisdod;
import cz.sysnet.env.model.Cisdod3;
import cz.sysnet.env.model.Faktura;
import cz.sysnet.env.model.Sup;

public class Utils {
	public static String DEFAULT_CHARSET_NAME_DBF = "Cp1250";
	public static String DEFAULT_CHARSET_NAME_OTPUT = "utf-8";
	
	private static int dotCounter = 0;
	
	
	public static String dbfToCsv(String dbfFileName) throws Exception {
		return dbfToCsv(dbfFileName, null, null, null);
	}
	
	public static String dbfToCsv(String dbfFileName, String csvFileName) throws Exception {
		return dbfToCsv(dbfFileName, csvFileName, null, null);
	}
	
	public static String dbfToCsv(String dbfFileName, String csvFileName, String inCharsetName, String outCharsetName) {
		Charset charsetIn = Charset.forName(DEFAULT_CHARSET_NAME_DBF);
		Charset charsetOut = Charset.forName(DEFAULT_CHARSET_NAME_OTPUT);
		DBFReader reader = null;
		Writer writer = null;
		String out = null;
		
		try {
			if (dbfFileName == null) throw new Exception("Chybí vstupní DBF");
			File inFile = new File(dbfFileName);
			if (!inFile.exists()) throw new Exception("Vstupní DBF neexistuje");
			
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
			
		} catch (Exception e) {
			System.out.println("dbfToCsv: " + e.getMessage());
			e.printStackTrace();
			out = null;
			
		} finally {
			DBFUtils.close(reader);
			DBFUtils.close(writer);
		}
		return out;	
	}
	
	public static int dbfGetRecordCount(String dbfFilename) {
		DBFReader reader = null;
		int out = 0;
		try {
			reader = new DBFReader(new FileInputStream(dbfFilename), Charset.forName(DEFAULT_CHARSET_NAME_DBF));
			out = reader.getRecordCount();
			
		} catch (Exception e) {
			System.out.println("getRecordCount ERROR: " + e.getMessage()); 
			e.printStackTrace();
			out = 0;
			
		} finally {
			DBFUtils.close(reader);
		}
		return out;
	}
	
	public static List<Cisdod3> loadCisdod3List(String dbfFilename, int fromItem, int itemCount) {
		List<Cisdod3> out = null;
		DBFReader reader = null;
		
		try {
			reader = new DBFReader(new FileInputStream(dbfFilename), Charset.forName(DEFAULT_CHARSET_NAME_DBF));
			int numberOfFields = reader.getFieldCount();
			Boolean ok = false;
			List<String> fields = new ArrayList<String>();
			for (int i = 0; i < numberOfFields; i++) {
				DBFField field = reader.getField(i);
				fields.add(field.getName());				
			}
			if (fields.equals(Cisdod3.DBF_FIELD_LIST)) ok = true;
			if (!ok) {
				DBFUtils.close(reader);
				throw new Exception("Datová struktura neodpovídá objektu 'Cisdod3'");
			}
			if (fromItem > 0) reader.skipRecords(fromItem);
			out = new ArrayList<Cisdod3>();
			Object[] rowObjects;
			int j = 0;
			while (((rowObjects = reader.nextRecord()) != null) && ((j < itemCount) || (itemCount <= 0))) {
				Cisdod3 row = new Cisdod3();
				for (int i = 0; i < rowObjects.length; i++) {
					Object data = rowObjects[i];
					String dataStr = "";
					if (data != null) dataStr = data.toString();
					if(i==0) row.setKodproj(dataStr);
					else if(i==1) row.setDodavatel(dataStr);
					else if(i==2) row.setSmlouva(dataStr);
					else if(i==3) row.setVariab(dataStr);
					else if(i==4) row.setSpecif(dataStr);
					else if(i==5) row.setIco(dataStr);
					else if(i==6) row.setDny(Long.parseLong(dataStr));
					else if(i==7) row.setDruhfa(dataStr);
					else if(i==8) row.setProcentaza(Double.parseDouble(dataStr));
				}
				out.add(row);
				j++;
			}
		} catch (Exception e) {
			System.out.println("loadCisdod3List ERROR: " + e.getMessage()); 
			e.printStackTrace();
			out = null;
			
		} finally {
			DBFUtils.close(reader);
		}
		return out;
	}
	
	public static List<Faktura> loadFakturaList(String dbfFilename, int fromItem, int itemCount) {
		List<Faktura> out = null;
		DBFReader reader = null;
		
		try {
			reader = new DBFReader(new FileInputStream(dbfFilename), Charset.forName(DEFAULT_CHARSET_NAME_DBF));
			int numberOfFields = reader.getFieldCount();
			Boolean ok = false;
			List<String> fields = new ArrayList<String>();
			for (int i = 0; i < numberOfFields; i++) {
				DBFField field = reader.getField(i);
				fields.add(field.getName());				
			}
			if (fields.equals(Faktura.DBF_FIELD_LIST)) ok = true;
			if (!ok) {
				DBFUtils.close(reader);
				throw new Exception("Datová struktura neodpovídá objektu 'Faktura'");
			}
			if (fromItem > 0) reader.skipRecords(fromItem);
			out = new ArrayList<Faktura>();
			DBFRow dbfRow = null;
			int j = 0;
			while (((dbfRow = reader.nextRow()) != null) && ((j < itemCount) || (itemCount <= 0))) {
				Faktura row = new Faktura();
				row.setPorcis(dbfRow.getString(0));
				row.setDodavatel(dbfRow.getString(1));
				row.setIcod(dbfRow.getString(2));
				row.setSmlouva(dbfRow.getString(3));
				row.setCispart(dbfRow.getString(4));
				row.setCisfa(dbfRow.getString(5));
				row.setCastka(dbfRow.getDouble(6));
				row.setUhrada(dbfRow.getString(7));
				row.setCuctu(dbfRow.getString(8));
				row.setBanka(dbfRow.getString(9));
				row.setVystavena(dbfRow.getDate(10));
				row.setDosla(dbfRow.getDate(11));
				row.setLikvidace(dbfRow.getDate(12));
				row.setVrackprop(dbfRow.getDate(13));
				row.setVariab(dbfRow.getString(14));
				row.setSpecif(dbfRow.getString(15));
				row.setDruhfa(dbfRow.getString(16));
				row.setSplatna(dbfRow.getDate(17));
				row.setUhrazena(dbfRow.getDate(18));
				row.setVracena(dbfRow.getDate(19));
				row.setZauctovano(dbfRow.getDate(20));
				row.setSup(dbfRow.getString(21));
				row.setIcos(dbfRow.getString(22));
				row.setPu(dbfRow.getString(23));
				row.setKodproj(dbfRow.getString(24));
				row.setProcentaza(dbfRow.getDouble(25));
				row.setCastkaza(dbfRow.getDouble(26));
				row.setSplatnaza(dbfRow.getDate(27));
				row.setCastkazaca(dbfRow.getDouble(28));
				row.setUhrazenaza(dbfRow.getDate(29));
				row.setCastkabdph(dbfRow.getDouble(30));
				out.add(row);
				j++;
			}
		} catch (Exception e) {
			System.out.println("loadFakturaList ERROR: " + e.getMessage()); 
			e.printStackTrace();
			out = null;
			
		} finally {
			DBFUtils.close(reader);
		}
		return out;
	}
	
	public static String storeSupToDbf(List<Sup> objectList, String dbfFilename) {
		DBFWriter writer = null;
		String outFilename = null;
		try {
			if (objectList == null) throw new Exception("Vstupní data jsou null");
			if (dbfFilename == null) dbfFilename = Sup.FILE_NAME;
			File outFile = new File(dbfFilename);
			outFilename = outFile.getAbsolutePath();
			
			writer = new DBFWriter(new FileOutputStream(outFilename), Charset.forName(DEFAULT_CHARSET_NAME_DBF));
			DBFField[] fields = new DBFField[2];
			
			fields[0] = Utils.createDbField("SUP", DBFDataType.CHARACTER, 20, null);
			fields[1] = Utils.createDbField("ICO", DBFDataType.CHARACTER, 11, null);
						
			writer.setFields(fields);
			
			Object rowData[] = null;			
			for (Sup item:objectList) {
				rowData = new Object[2];
				rowData[0] = item.getSup();
				rowData[1] = item.getIco();
				writer.addRecord(rowData);
			}
		} catch (Exception e) {
			System.out.println("storeSupToDbf ERROR: " + e.getMessage());
			e.printStackTrace();
			
		} finally {
			DBFUtils.close(writer);
		}
		return outFilename;		
	}
	
	
	public static String storeCisdodToDbf(List<Cisdod> objectList, String dbfFilename) {
		DBFWriter writer = null;
		String outFilename = null;
		try {
			if (objectList == null) throw new Exception("Vstupní data jsou null");
			if (dbfFilename == null) dbfFilename = Cisdod.FILE_NAME;
			File outFile = new File(dbfFilename);
			outFilename = outFile.getAbsolutePath();
			
			writer = new DBFWriter(new FileOutputStream(outFilename), Charset.forName(DEFAULT_CHARSET_NAME_DBF));
			DBFField[] fields = new DBFField[7];
			
			fields[0] = Utils.createDbField("DODAVATEL", DBFDataType.CHARACTER, 20, null);
			fields[1] = Utils.createDbField("SMLOUVA", DBFDataType.CHARACTER, 5, null);
			fields[2] = Utils.createDbField("CISPART", DBFDataType.CHARACTER, 2, null);
			fields[3] = Utils.createDbField("VARIAB", DBFDataType.CHARACTER, 9, null);
			fields[4] = Utils.createDbField("SPECIF", DBFDataType.CHARACTER, 10, null);
			fields[5] = Utils.createDbField("ICO", DBFDataType.CHARACTER, 12, null);
			fields[6] = Utils.createDbField("DNY", DBFDataType.NUMERIC, 3, 0);
									
			writer.setFields(fields);
			
			Object rowData[] = null;			
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
			System.out.println("storeCisdodToDbf ERROR: " + e.getMessage());
			e.printStackTrace();
			
		} finally {
			DBFUtils.close(writer);
		}
		return outFilename;		
	}

	
	public static String storeCisdod3ToDbf(List<Cisdod3> objectList, String dbfFilename) {
		DBFWriter writer = null;
		String outFilename = null;
		try {
			if (objectList == null) throw new Exception("Vstupní data jsou null");
			if (dbfFilename == null) dbfFilename = Cisdod3.FILE_NAME;
			File outFile = new File(dbfFilename);
			outFilename = outFile.getAbsolutePath();
			
			writer = new DBFWriter(new FileOutputStream(outFilename), Charset.forName(DEFAULT_CHARSET_NAME_DBF));
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
			
			Object rowData[] = null;			
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
			System.out.println("storeCisdod3ToDbf ERROR: " + e.getMessage());
			e.printStackTrace();
			
		} finally {
			DBFUtils.close(writer);
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
	
}
