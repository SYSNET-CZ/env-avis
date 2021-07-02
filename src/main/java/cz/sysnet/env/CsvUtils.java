package cz.sysnet.env;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;


public class CsvUtils {
	private static final Logger LOG = LogManager.getLogger(CsvUtils.class);
	
	private static final char DEFAULT_SEPARATOR = ',';

	public static void writeLine(Writer w, List<String> values) throws IOException {
		writeLine(w, values, DEFAULT_SEPARATOR, ' ');
	}

	public static void writeLine(Writer w, List<String> values, char separators) throws IOException {
		writeLine(w, values, separators, ' ');
	}

	// https://tools.ietf.org/html/rfc4180
	private static String followCVSformat(String value) {

//		String result = encodeValue(value);
		String result = value;
		if (result.contains("\"")) {
			result = result.replace("\"", "\"\"");
		}
		return result;

	}

	public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {
		boolean first = true;

		// default customQuote is empty
		if (separators == ' ') {
			separators = DEFAULT_SEPARATOR;
		}

		StringBuilder sb = new StringBuilder();
		for (String value : values) {
			if (!first) {
				sb.append(separators);
			}
			if (customQuote == ' ') {
				sb.append(followCVSformat(value));
			} else {
				sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
			}

			first = false;
		}
		sb.append("\n");
		w.append(sb.toString());

	}
	
	protected static String encodeValue(String value) {
	    try {
	    	String out = URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
	    	return out;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return value;
		}
	}
	
	protected static String escapeCsv(String value) {
		if (value.indexOf("\"") != -1 || value.indexOf(",") != -1) {
		//if (value.indexOf("\"") != -1) {
			value = value.replaceAll("\"", "\"\"");
		    value = "\"" + value + "\"";
		} 
		return value;
	}
	
	public static List<?> readBeanList(String filePath, Class<?> cl) {
		List<?> out = null;
		try {
			FileInputStream fis = new FileInputStream(filePath);
			InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
			BufferedReader reader = new BufferedReader(isr);
			out = new CsvToBeanBuilder<Object>(reader).withType(cl).build().parse();
		} catch (IllegalStateException e) {
			System.out.println("readBeanList ERROR (IllegalStateException): " + e.getMessage());
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("readBeanList ERROR (FileNotFoundException): " + e.getMessage());
			e.printStackTrace();
		}		
		return out;	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String writeBeanList(String filePath, List<?> beanList) {
		String out = null;
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true), StandardCharsets.UTF_8));
			StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).build();
			beanToCsv.write(beanList);
			writer.close();
			String exlist = "";
			if (!beanToCsv.isThrowExceptions()) {
				List el = beanToCsv.getCapturedExceptions();
				for (int i = 0; i < el.size(); i++) {
					if (i == 0)
						exlist = (String) el.get(i);
					else
						exlist = "\n" + (String) el.get(i);
				}
			}
			LOG.info("writeBeanList" + beanList.getClass().getSimpleName());
			out = filePath;
			if (!exlist.isEmpty()) { 
				LOG.error(exlist);
				out += "\n" + exlist;
			}
			
		} catch (FileNotFoundException e) {
			LOG.error("writeBeanList", e);
			System.out.println("writeBeanList ERROR (FileNotFoundException): " + e.getMessage());
			e.printStackTrace();
		} catch (CsvDataTypeMismatchException e) {
			LOG.error("writeBeanList", e);
			System.out.println("writeBeanList ERROR (CsvDataTypeMismatchException): " + e.getMessage());
			e.printStackTrace();
		} catch (CsvRequiredFieldEmptyException e) {
			LOG.error("writeBeanList", e);
			System.out.println("writeBeanList ERROR (CsvRequiredFieldEmptyException): " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			LOG.error("writeBeanList", e);
			System.out.println("writeBeanList ERROR (IOException): " + e.getMessage());
			e.printStackTrace();
		}
		return out;
	}

}


