package cz.sysnet.env;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

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
	
	
	static String JSON = "\"{\"cisloSmlouvy\":\"0050\",\"typ\":\"S\",\"zakladniSmlouva\":\"\",\"predmet\":\"Koncepce řešení ekologických škod vzniklých před privatizací hnědouhelných těžebních společností v Ústeckém a Karlovarském kraji\",\"dny\":45,\"druhFaktury\":\"K\",\"kodProjektu\":\"0050/02-UV-001-KU0001\",\"sazbaDph\":10.13103448275862,\"castka\":7250000.0,\"procentoZad\":0.0,\"varSymbol\":\"0005202999\",\"specSymbol\":\"0052029905\",\"datumPodpisu\":\"Oct 23, 2002 8:34:08 PM\",\"ico\":\"44569181\",\"dodavatel\":\"Výzkumný ústav pro h\"}\"";
	
	

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
	
	@Test
	public void testRound() {
		double d = 123456789.45667894521;
		
		BigDecimal bd = new BigDecimal(d).setScale(2, RoundingMode.HALF_EVEN);
		assertTrue(bd != null);
		double d1 = bd.doubleValue();
		
		DecimalFormat df2 = new DecimalFormat("#.##");
		
		System.out.println(d);
		System.out.println(d1);
		System.out.println(df2.format(d).replace(",", "."));
		
	}
	
	
	@Test
	public void testJson() {
		String out = Utils.hash(JSON, null);
		assertTrue(out != null);
		System.out.println(out);
	}
	
	
	
	

}
