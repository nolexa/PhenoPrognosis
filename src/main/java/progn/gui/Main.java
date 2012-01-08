package progn.gui;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created: 1/1/12 9:15 PM
 *
 * @author Alexei Novikov <alexei.novikov@lequa.com>
 */
public class Main {
    public static void main(String[] args) throws MalformedURLException {
        new PhenoPrognosis(new URL("file://" + args[0]));
    }
}
