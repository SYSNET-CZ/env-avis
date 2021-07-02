package cz.sysnet.env;
import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

import cz.sysnet.env.model.Cisdod;
import cz.sysnet.env.model.Cisdod3;
import cz.sysnet.env.model.Faktura;
import cz.sysnet.env.model.Sup;

public class TestReadDbf {
	String dbfFileSup = "src/test/resources/Sup.DBF";
	String dbfFileCisdod = "src/test/resources/CISDOD.DBF";
	String dbfFileCisdod3 = "src/test/resources/CISDOD3.dbf";
	String dbfFileFaktura = "src/test/resources/FAKTLN.DBF";
	
	String dbfFileCisdod3a = "src/test/resources/CISDOD3a.DBF";
	
	@Test
	public void testLogger() {
		boolean out = Utils.logChecker();
		assertTrue(out);
	}
	
	@Test
	public void testDbfCheck() {
		boolean out = Utils.checkFakturaDbf(dbfFileFaktura);
		assertTrue(out);
	}

	
	@Test
	public void testReadSup() {
		List<Sup> dataList = Utils.loadSupListFromDbf(dbfFileSup, 0, 10);
		assertNotNull(dataList);		
		Sup data = dataList.get(0);
		// System.out.println(data.toString());
		assertEquals("27624218", data.getIco());
		
		System.out.println(data.hashString());
		assertTrue(data.checkHashString("b16883dd9ae65c7416b9734b4d71d3a2"));				
	}
	
	@Test
	public void testReadCisdod() {
		List<Cisdod> dataList = Utils.loadCisdodListFromDbf(dbfFileCisdod, 0, 10);
		assertNotNull(dataList);		
		Cisdod data = dataList.get(0);
		// System.out.println(data.toString());
		assertEquals("28674286", data.getIco());
		
		String h = data.hashString();
		// System.out.println(data.hashString());
		// assertTrue(data.checkHashString("7f6650e40ae5d20959aeb8d72636e560"));		
		assertTrue(data.checkHashString(h));		
		
	}
	
	@Test
	public void testReadCisdod3() {
		List<Cisdod3> dataList = Utils.loadCisdod3ListFromDbf(dbfFileCisdod3, 0, 10);
		assertNotNull(dataList);		
		Cisdod3 data = dataList.get(0);
		//System.out.println(data.toString());
		assertEquals("25513231", data.getIco());
		
		// System.out.println(data.hashString());
		assertTrue(data.checkHashString("e43183a47ca16a92bdef68f2d61d9a61"));		
	
	}

	@Test
	public void testReadFaktura() {
		List<Faktura> dataList = Utils.loadFakturaListFromDbf(dbfFileFaktura, 0, 10);
		assertNotNull(dataList);		
		Faktura data = dataList.get(0);
		//System.out.println(data.toString());
		assertEquals("19992000", data.getPorcis());
		
		//System.out.println(data.hashString());
		String h = data.hashString();
		// assertTrue(data.checkHashString("c433644c864e5ab67aa46d581fb2994c"));		
		assertTrue(data.checkHashString(h));		
		
	}
	
	@Test
	public void testWriteCisdod3() {
		List<Cisdod3> outlist = Utils.loadCisdod3ListFromDbf(dbfFileCisdod3, 0, 0);
		// System.out.println(outlist.toString());
		assertNotNull(outlist);
		
		String out = Utils.storeCisdod3ToDbf(outlist, dbfFileCisdod3a);
		// System.out.println(out);
		assertNotNull(out);
		
		File fileSource = new File(dbfFileCisdod3);
		File fileTarget = new File(dbfFileCisdod3a);
		
		assertEquals(fileSource.length(), fileTarget.length());
	}
}
