package progn.gui;

import progn.loaders.Model;

import java.net.MalformedURLException;

/**
 * Created: 1/1/12 9:15 PM
 *
 * @author Alexei Novikov <alexei.novikov@lequa.com>
 */
public class Main {
    public static void main(String[] args) throws MalformedURLException {
        Model model = new Model();
        PrognoseFrame frame = new PrognoseFrame(model);
        frame.setVisible(true);
    }
}
