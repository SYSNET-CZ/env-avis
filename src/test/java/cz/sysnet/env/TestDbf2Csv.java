package cz.sysnet.env;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.sysnet.env.model.Faktura;

public class TestDbf2Csv {
	String dbfFileSup = "src/test/resources/Sup.DBF";
	String dbfFileCisdod = "src/test/resources/CISDOD.DBF";
	String dbfFileCisdod3 = "src/test/resources/CISDOD3.dbf";
	String dbfFileFaktura = "src/test/resources/FAKTLN.DBF";
	String csvFileFaktura1 = "src/test/resources/FAKTLN1.csv";
	String csvFileFaktura2 = "src/test/resources/FAKTLN2.csv";

	@Test
	public void testWriteDbfToCsv() {
		String out = Utils.dbfToCsv(dbfFileSup);
		assertTrue(out != null);
		System.out.println(out);
		
		out = Utils.dbfToCsv(dbfFileCisdod3);
		assertTrue(out != null);
		System.out.println(out);
		
		out = Utils.dbfToCsv(dbfFileFaktura);
		assertTrue(out != null);
		System.out.println(out);
	}
	
	@Test
	public void testWriteDbfToCsv2() {
		String out = Utils.dbfToCsv2(dbfFileSup);
		assertTrue(out != null);
		System.out.println(out);
		
		out = Utils.dbfToCsv2(dbfFileCisdod3);
		assertTrue(out != null);
		System.out.println(out);
		
		out = Utils.dbfToCsv2(dbfFileFaktura);
		assertTrue(out != null);
		System.out.println(out);
	}
	
	@Test
	public void testCsv2Dbf() {
		String out = Utils.csvToDbf(csvFileFaktura2, Faktura.class);
		assertTrue(out != null);
		System.out.println(out);
	}	
}
