package progn.gui;

import progn.solver.PhenoPrognosis;

import java.applet.Applet;
import java.awt.*;

/* Класс Starter запускается через апплет и запускает класс PhenoPrognosis */
public class Starter extends Applet {
	Button button;
	String name;

	public void init() {
		/* Создать кнопку запуска */
		name = getParameter("name");
		button = new Button(name);
		add("Center", button);
	}

	public boolean action(Event e, Object target) {
		if (target == name) {
			/* При нажатии кнопки запустить */
			new PhenoPrognosis();
			return true;
		}
		return false;
	}
}
