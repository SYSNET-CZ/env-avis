package cz.sysnet.env;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import cz.sysnet.env.model.Cisdod3;
import cz.sysnet.env.model.Faktura;

public class TestCsv {
	String csvFileSup = "src/test/resources/Sup.csv";
	String csvFileCisdod = "src/test/resources/CISDOD.csv";
	String csvFileCisdod3 = "src/test/resources/CISDOD3.csv";
	String csvFileCisdod3a = "src/test/resources/CISDOD3a.csv";
	String csvFileCisdod3b = "src/test/resources/CISDOD3b.csv";
	String csvFileFaktura = "src/test/resources/FAKTLN.csv";
	String csvFileFaktura1 = "src/test/resources/FAKTLN1.csv";
	
	@Test
	public void testBeanCisdod3() {
		List<?> out = CsvUtils.readBeanList(csvFileCisdod3, Cisdod3.class);
		assertTrue(out != null);
		System.out.println(out.size());
		
		String outString = CsvUtils.writeBeanList(csvFileCisdod3a, out);
		assertTrue(outString != null);
		System.out.println(outString);
		
		outString = Utils.storeCisdod3ToCsv((List<Cisdod3>)(List<?>) out, csvFileCisdod3b);
		assertTrue(outString != null);
		System.out.println(outString);
	}
	
	@Test
	public void testBeanFaktura() {
		List<?> out = CsvUtils.readBeanList(csvFileFaktura1, Faktura.class);
		assertTrue(out != null);
		System.out.println(out.size());
	}
}
