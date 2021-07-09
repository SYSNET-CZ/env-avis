package cz.sysnet.env;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import cz.sysnet.env.model.Cisdod;
import cz.sysnet.env.model.Cisdod3;
import cz.sysnet.env.model.Faktura;
import cz.sysnet.env.model.Sup;

public class TestCommonsCsv {
	String[] h = null;
	
	String csvFileSup = "src/test/resources/Sup.csv";
	String csvFileCisdod = "src/test/resources/CISDOD.csv";
	String csvFileCisdod3 = "src/test/resources/CISDOD3.csv";
	String csvFileCisdod3a = "src/test/resources/CISDOD3a.csv";
	String csvFileCisdod3b = "src/test/resources/CISDOD3b.csv";
	String csvFileCisdod3c = "src/test/resources/CISDOD3c.csv";
	String csvFileFaktura = "src/test/resources/FAKTLN.csv";
	String csvFileFaktura1 = "src/test/resources/FAKTLN1.csv";


	@Test
	public void testCsvHeader() {
		h = Sup.CSV_HEADER;
		assertTrue(h != null);
		assertTrue(h.length == 2);
		
		h = Cisdod.CSV_HEADER;
		assertTrue(h != null);
		assertTrue(h.length == 7);
		
		h = Cisdod3.CSV_HEADER;
		assertTrue(h != null);
		assertTrue(h.length == 9);
		
		h = Faktura.CSV_HEADER;
		assertTrue(h != null);
		assertTrue(h.length == 31);
		
		//System.out.println(h.length);	
		//System.out.println(h[0]);	
		
	}
		
	@Test
	public void testCsvFactoryCisdod3() {
		CsvFactory factory = CsvFactory.getInstance();
		Iterable<CSVRecord> records = factory.csvReadRecordsIterable(csvFileCisdod3c, Cisdod3.CSV_HEADER);
		assertTrue(records != null);
		List<Cisdod3> outList = new ArrayList<Cisdod3>();
		for (CSVRecord record : records) {
	    	Cisdod3 out = CsvFactory.csvRecordToCisdod3(record);
	    	assertTrue(out != null);
	    	outList.add(out);
	    	System.out.println(out.toString());   	
	    }
		assertTrue(!factory.isRecycled());
    	factory.recycle();
    	assertTrue(factory.isRecycled());
	}
	
	@Test
	public void testCsvFactoryFaktura() {
		CsvFactory factory = CsvFactory.getInstance();
		Iterable<CSVRecord> records = factory.csvReadRecordsIterable(csvFileFaktura1, Faktura.CSV_HEADER);
		assertTrue(records != null);
		List<Faktura> outList = new ArrayList<Faktura>();
		for (CSVRecord record : records) {
			Faktura out = CsvFactory.csvRecordToFaktura(record);
	    	assertTrue(out != null);
	    	outList.add(out);
	    	System.out.println(out.toString());   	
	    }
		assertTrue(!factory.isRecycled());
    	factory.recycle();
    	assertTrue(factory.isRecycled());
	}
	
}
