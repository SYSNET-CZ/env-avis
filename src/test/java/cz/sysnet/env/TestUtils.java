package cz.sysnet.env;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestUtils {
	String csvFileCisdod3a = "src/test/resources/CISDOD3a.csv";
	String csvFileCisdod3b = "src/test/resources/CISDOD3b.csv";
	String csvFileCisdod3c = "src/test/resources/CISDOD3c.csv";
	String csvFileFaktura = "src/test/resources/FAKTLN.csv";
	String csvFileFaktura1 = "src/test/resources/FAKTLN1.csv";
	String csvFileFakturaX = "src/test/resources/FAKTLNX.csv";
	String dbfFileFaktura = "src/test/resources/FAKTLN.DBF";
	String dbfFileFaktura3 = "src/test/resources/Faktln3.dbf";
	

	@Test
	public void test() {
		String out = Utils.getFileChecksumMd5(csvFileCisdod3a);
		assertTrue(out != null);

		out = Utils.getFileChecksumSha1(csvFileCisdod3a);
		assertTrue(out != null);

		out = Utils.getFileChecksumSha256(csvFileCisdod3a);
		assertTrue(out != null);

		
		out = Utils.getFileChecksumSha256(csvFileFaktura);
		assertTrue(out != null);

		out = Utils.getFileChecksumSha256(csvFileFaktura1);
		assertTrue(out != null);

		out = Utils.getFileChecksumSha256(csvFileFakturaX);
		assertTrue(out == null);

		out = Utils.getFileChecksumSha256(dbfFileFaktura);
		assertTrue(out != null);

		out = Utils.getFileChecksumSha256(dbfFileFaktura3);
		assertTrue(out != null);

	}

}
