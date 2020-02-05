package cz.sysnet.env;

import java.util.List;

import cz.sysnet.env.model.Cisdod3;

/*
 * https://github.com/albfernandez/javadbf
 * https://mkyong.com/java/how-to-export-data-to-csv-file-java/
 */

public class TestIt {
	public static void main(String[] args) {

		String dbfFile = "src/main/resources/CISDOD3.dbf";
		String dbfFile1 = "src/main/resources/CISDOD3a.DBF";

		List<Cisdod3> outlist = Utils.loadCisdod3List(dbfFile, 0, 0);
		System.out.println(outlist.toString());
		
		String out = Utils.storeCisdod3ToDbf(outlist, dbfFile1);
		System.out.println(out);
	}
}
