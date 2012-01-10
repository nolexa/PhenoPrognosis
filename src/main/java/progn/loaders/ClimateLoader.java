package progn.loaders;

import progn.YearCalendar;
import progn.entity.ClimateZone;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ClimateLoader {
    private List<ClimateZone> zones;
    private List<String> zonesNames;
    private String buffer = null;
    private String name;
    private int start;
    private int frostDay;

    public ClimateLoader(File fil) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(fil), Charset.forName("UTF-8")));
        } catch (Exception e) {
            System.out.println("���� �� ��������");
            e.printStackTrace();
            return;
        }
        zones = new ArrayList<>();
        zonesNames = new ArrayList<>();
        boolean result = true;
        result = skipLines(reader);
        while (result) {
            readHeader(reader);
            readFields(reader);
            result = skipLines(reader);
        }
        for (int x = 0; x < zones.size(); x++) {
            ClimateZone z = zones.get(x);
            //System.out.println(z);
        }
    }

    boolean skipLines(BufferedReader reader) {
        for (; ; ) {
            String s = null;
            try {
                s = reader.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (s == null) {
                return false;
            } else if (!(s.equals("") || s.startsWith("#"))) {
                buffer = s;
                return true;
            }
        }
    }

    void readHeader(BufferedReader reader) {
        String s1 = null;
        String s2 = null;
        String s3 = null;
        try {
            s1 = buffer;
            s2 = reader.readLine();
            while (s2.startsWith("#")) {
                s2 = reader.readLine();
            }
            s3 = reader.readLine();
            while (s3.startsWith("#")) {
                s3 = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringTokenizer st1 = new StringTokenizer(s1, "=");
        StringTokenizer st2 = new StringTokenizer(s2, " =");
        StringTokenizer st3 = new StringTokenizer(s3, " =");
        st1.nextToken();
        st2.nextToken();
        st3.nextToken();
        name = st1.nextToken();
        start = Integer.parseInt(st2.nextToken());
        String frost = st3.nextToken();
        StringTokenizer st4 = new StringTokenizer(frost, ". ");
        int d = Integer.parseInt(st4.nextToken());
        int m = Integer.parseInt(st4.nextToken());
        frostDay = YearCalendar.dateToDay(d, m);
//    System.out.println(d+" "+m);
    }

    void readFields(BufferedReader reader) {
        double[] values = new double[0];
        for (; ; ) {
            String s = null;
            try {
                s = reader.readLine();
//        System.out.println(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (s == null) {
                buildZone(values);
                return;
            }
            if (s.equals("")) {
                buildZone(values);
                return;
            }
            StringTokenizer st = new StringTokenizer(s, " ");
            String lastToken = null;
            while (st.hasMoreTokens()) {
                lastToken = st.nextToken();
            }
            double[] val2 = new double[values.length + 1];
            System.arraycopy(values, 0, val2, 0, values.length);
            val2[val2.length - 1] = Double.valueOf(lastToken);
            values = val2;
        }
    }

    void buildZone(double[] values) {
//    System.out.println("build");
        zones.add(new ClimateZone(name, start, values, frostDay));
        zonesNames.add(name);
    }

    public List<ClimateZone> getZones() {
        return zones;
    }

    public List<String> getZonesNames() {
        return zonesNames;
    }
}

