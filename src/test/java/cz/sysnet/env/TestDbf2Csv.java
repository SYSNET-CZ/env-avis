package cz.sysnet.env;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestDbf2Csv {
	String dbfFileSup = "src/test/resources/Sup.DBF";
	String dbfFileCisdod = "src/test/resources/CISDOD.DBF";
	String dbfFileCisdod3 = "src/test/resources/CISDOD3.dbf";
	String dbfFileFaktura = "src/test/resources/FAKTLN.DBF";

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

}
