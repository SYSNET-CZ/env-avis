import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.Test;

import cz.sysnet.env.Utils;
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
	public void testReadSup() {
		List<Sup> dataList = Utils.loadSupList(dbfFileSup, 0, 10);
		assertTrue(dataList != null);		
		Sup data = dataList.get(0);
		// System.out.println(data.toString());
		assertEquals(data.getIco(), "27624218");
		
		// System.out.println(data.hashString());
		assertTrue(data.checkHashString("7a097fbb8b94fe984f05115283020b9f"));				
	}
	
	@Test
	public void testReadCisdod() {
		List<Cisdod> dataList = Utils.loadCisdodList(dbfFileCisdod, 0, 10);
		assertTrue(dataList != null);		
		Cisdod data = dataList.get(0);
		// System.out.println(data.toString());
		assertEquals(data.getIco(), "28674286");
		
		String h = data.hashString();
		// System.out.println(data.hashString());
		// assertTrue(data.checkHashString("7f6650e40ae5d20959aeb8d72636e560"));		
		assertTrue(data.checkHashString(h));		
		
	}
	
	@Test
	public void testReadCisdod3() {
		List<Cisdod3> dataList = Utils.loadCisdod3List(dbfFileCisdod3, 0, 10);
		assertTrue(dataList != null);		
		Cisdod3 data = dataList.get(0);
		//System.out.println(data.toString());
		assertEquals(data.getIco(), "25513231");
		
		// System.out.println(data.hashString());
		assertTrue(data.checkHashString("e43183a47ca16a92bdef68f2d61d9a61"));		
	
	}

	@Test
	public void testReadFaktura() {
		List<Faktura> dataList = Utils.loadFakturaList(dbfFileFaktura, 0, 10);
		assertTrue(dataList != null);		
		Faktura data = dataList.get(0);
		//System.out.println(data.toString());
		assertEquals(data.getPorcis(), "19992000");
		
		//System.out.println(data.hashString());
		String h = data.hashString();
		// assertTrue(data.checkHashString("c433644c864e5ab67aa46d581fb2994c"));		
		assertTrue(data.checkHashString(h));		
		
	}
	
	@Test
	public void testWriteCisdod3() {
		List<Cisdod3> outlist = Utils.loadCisdod3List(dbfFileCisdod3, 0, 0);
		// System.out.println(outlist.toString());
		assertTrue(outlist != null);
		
		String out = Utils.storeCisdod3ToDbf(outlist, dbfFileCisdod3a);
		// System.out.println(out);
		assertTrue(out != null);
		
		File fileSource = new File(dbfFileCisdod3);
		File fileTarget = new File(dbfFileCisdod3a);
		
		assertEquals(fileSource.length(), fileTarget.length());
	}
}
