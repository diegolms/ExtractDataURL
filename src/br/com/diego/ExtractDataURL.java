package br.com.diego;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ExtractDataURL {

	public static final String TAG_IN = "meta[name=description]";
	public static final String TAG_OUT = "meta description";
	public static final String ATTR = "content";
	public static final String FILE_IN_PATH = "files/sites.txt";
	public static final String FILE_OUT_PATH = "files/saida.txt";

	public static void main(String args[]) throws IOException {

		File fileIn = new File(FILE_IN_PATH);
		FileInputStream fis = new FileInputStream(fileIn);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		FileWriter fw = new FileWriter(FILE_OUT_PATH);
		BufferedWriter bw = new BufferedWriter(fw);
		String line = null;
		System.out.println("Extraindo dados...");
		
		while ((line = br.readLine()) != null) {
			InputStream is = new URL(line.trim()).openStream();
			String result = getStringFromInputStream(is);
			Document doc = Jsoup.parse(result);
			String description = doc.select(TAG_IN).get(0).attr(ATTR);
			bw.write("Site - " +line+ " - TAG - " +TAG_OUT+ " - " +description);
			bw.newLine();
			bw.flush();
		}

		bw.close();
		br.close();
		System.out.println("Acabou!!!");
	}

	private static String getStringFromInputStream(InputStream is) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

}
