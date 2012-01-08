//Source file: D:/Work/Pheno-Prognosis/Java/source/progn/SortLoader.java

package progn.loaders;

import progn.entity.PhenoPhase;
import progn.entity.Sort;

import java.io.*;
import java.util.*;
import java.net.URL;
import java.nio.charset.Charset;

public class SortLoader {
	private BufferedReader reader;
	private List<Sort> sorts;

	public SortLoader(String fil, URL base) {
		sorts = new ArrayList<>();
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
				Map table = readFields(group);
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
				sorts.add(sss);
			}
		} catch (NullPointerException e) {
		} catch (Exception e) {
			// System.out.println();
			e.printStackTrace();
		}
	}

	protected Map<String, String> readFields(String[] group) throws Exception {
		Map<String, String> fieldsMap = new HashMap<>();
        for (String s : group) {
            StringTokenizer st = new StringTokenizer(s, "=", false);
            String key = st.nextToken();
            if (st.hasMoreTokens()) {
                String value = st.nextToken();
                fieldsMap.put(key, value);
                // System.out.println(key+" "+value);
            } else {
                // System.out.println("Can't find '=' in '" + s + "'");
            }
        }
		return fieldsMap;
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

    public List<Sort> getSorts() {
        return sorts;
    }
}