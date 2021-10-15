package cz.sysnet.env;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

import cz.sysnet.env.model.Smlouva;

public class TestSmlouvy {
	static String CSV_FILE_SMLOUVY_TEST = "src/test/resources/SMLOUVY_TESTa.CSV";
	static String CSV_FILE_SMLOUVY_20_TEST = "src/test/resources/SMLOUVY_TEST_20a.CSV";
	static String CSV_FILE_SMLOUVY_OUT1 = "src/test/resources/SMLOUVY_OUT1.CSV";
	static String CSV_FILE_SMLOUVY_OUT2 = "src/test/resources/SMLOUVY_OUT2.CSV";
	static String CSV_FILE_SMLOUVY_OUT3 = "src/test/resources/SMLOUVY_OUT3.CSV";
	static String DBF_FILE_SMLOUVY_OUT = "src/test/resources/SMLOUVY_OUT.DBF";
	
	@SuppressWarnings("unchecked")
	@Test
	public void testBeanSmlouva() {
		List<?> out = CsvUtils.readBeanList(CSV_FILE_SMLOUVY_20_TEST, Smlouva.class);
		assertTrue(out != null);
		System.out.println(out.size());
		
		// String outString = CsvUtils.writeBeanList(CSV_FILE_SMLOUVY_OUT1, out);
		// assertTrue(outString != null);
		// System.out.println(outString);
		
		String outString = Utils.storeSmlouvaToCsv((List<Smlouva>)(List<?>) out, CSV_FILE_SMLOUVY_OUT2);
		assertTrue(outString != null);
		System.out.println(outString);
		
		out = CsvUtils.readBeanList(CSV_FILE_SMLOUVY_TEST, Smlouva.class);
		assertTrue(out != null);
		System.out.println(out.size());
		
		List<Smlouva> out1 = (List<Smlouva>) out;
		assertTrue(out1 != null);
		String reply = Utils.storeSmlouvaToDbf(out1, DBF_FILE_SMLOUVY_OUT);
		assertTrue(reply != null);
		System.out.println(reply);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testBeanSmlouvaFeedBack() {
		List<?> out = CsvUtils.readBeanList(CSV_FILE_SMLOUVY_TEST, Smlouva.class);
		assertTrue(out != null);
		System.out.println(out.size());
		
		File file = new File(CSV_FILE_SMLOUVY_OUT2);
		if (file.exists()) file.delete();
		
		String outString = Utils.storeSmlouvaToCsv((List<Smlouva>)(List<?>) out, CSV_FILE_SMLOUVY_OUT2);
		assertTrue(outString != null);
		System.out.println(outString);
		
		out = CsvUtils.readBeanList(CSV_FILE_SMLOUVY_OUT2, Smlouva.class);
		assertTrue(out != null);
		System.out.println(out.size());
	}	
}
