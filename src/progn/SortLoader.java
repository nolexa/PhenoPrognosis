//Source file: D:/Work/Pheno-Prognosis/Java/source/progn/SortLoader.java

package progn;

import java.io.*;
import java.util.Vector;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;

public class SortLoader {
	private BufferedReader reader;
	Vector sorts;

	public SortLoader(String fil, URL base) {
		sorts = new Vector();
		try {
			URL file = new URL(base, fil);
			reader = new BufferedReader(new InputStreamReader(file.openStream(), Charset.forName("UTF-8")));
			for (;;) {
				String[] group = new String[5];
				for (int x = 0; x < group.length; x++) {
					String s = reader.readLine();
					if (s.equals("") || s.startsWith("#")) {
						x--;
					} else {
						group[x] = s;
					}
				}
				Hashtable table = readFields(group);
				 //System.out.println(table);
				String[] keys = { "культура", "сорт", "скороспелость", "фазы",
						"названия" };
				String[] values = new String[keys.length];
				for (int x = 0; x < keys.length; x++) {
					if (table.get(keys[x]) == null) {
						 System.out.println("Can't find value for " + keys[x]);
					}
					values[x] = (String) (table.get(keys[x]));
					 //System.out.println(values[x]);
				}
				if(values[2] == null){
					values[2] = "";
				}
				Sort sss = new Sort(values[1], values[2], values[0],
						parsePhase(values[3]), parseNames(values[4]));
				// System.out.println(sss);
				sorts.addElement(sss);
			}
		} catch (NullPointerException e) {
		} catch (Exception e) {
			// System.out.println();
			e.printStackTrace();
		}
		
		/*
		for (int x = 0; x < sorts.size(); x++) {
			Sort sort = (Sort) (sorts.elementAt(x));
			// System.out.println(sort + "\n");
		}*/
	}

	protected Hashtable readFields(String[] group) throws Exception {
		Hashtable table = new Hashtable();
		for (int x = 0; x < group.length; x++) {
			String s = group[x];
			StringTokenizer st = new StringTokenizer(s, "=", false);
			String key = st.nextToken();
			if (st.hasMoreTokens()) {
				String value = st.nextToken();
				table.put(key, value);
				// System.out.println(key+" "+value);
			} else {
				// System.out.println("Can't find '=' in '" + s + "'");
			}
		}
		return table;
	}

	protected PhenoPhase[] parsePhase(String sourse) {
		StringTokenizer st = new StringTokenizer(sourse, " ,;");
		PhenoPhase[] phases = new PhenoPhase[st.countTokens() / 2];
		for (int x = 0; x < phases.length; x++) {
			double factor = Double.valueOf(st.nextToken()).doubleValue();
			double exponent = Double.valueOf(st.nextToken()).doubleValue();
			phases[x] = new PhenoPhase(factor, exponent);
		}
		return phases;
	}

	protected int[] parseNames(String sourse) {
		StringTokenizer st = new StringTokenizer(sourse, " ,;");
		int[] names = new int[st.countTokens()];
		for (int x = 0; x < names.length; x++) {
			names[x] = Integer.parseInt(st.nextToken());
		}
		return names;
	}

}