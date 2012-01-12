package progn.gui;

import progn.YearCalendar;
import progn.entity.Sort;
import progn.loaders.Model;
import progn.solver.PhenoPrognosis;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class PrognoseFrame extends Frame implements ItemListener {
    private Model model;
	private Choice sort_choice;
    private Choice zone_choice;
    private Choice startperiod_choice;
    private TextArea area;
    private TextField startdate_field;
    private Button b;
    private Button b2;
    private Button b3;

    private Locale locale = new Locale("ru");
    private ResourceBundle bundle =  ResourceBundle.getBundle("messages", locale);
    
    private String getText(String key) {
        String val = bundle.getString(key);
        try {
            return new String(val.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return val;
        }
    }

	public PrognoseFrame(Model model) {
		super("Фенологический прогноз");
        this.model = model;

		setLayout(new BorderLayout());

		// Panel pan1 = new Panel(new BorderLayout());
		Panel main_panel = new Panel(new GridLayout(0, 1));
		Panel sort_panel = new Panel(new FlowLayout());
		Panel pan4 = new Panel(new FlowLayout());
		Panel pan5 = new Panel(new FlowLayout());
		Panel pan6 = new Panel(new FlowLayout());
		Panel pan7 = new Panel(new FlowLayout());

		Label sort_label = new Label(getText("label.sort"));
		Label zone_label = new Label("Агроклиматический район:");
		Label startdate_label = new Label("Дата начала периода (ДД.ММ):");
		Label startperiod_label = new Label("Начальный период:");
		b = new Button("Расчет");
		b2 = new Button("Выход");
		b3 = new Button("О программе");
		b.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				calculate_actionPerformed(e);
			}
		});
		b2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				exit_actionPerformed(e);
			}
		});
		b3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				about_actionPerformed(e);
			}
		});
		sort_choice = new Choice();
		zone_choice = new Choice();
		startperiod_choice = new Choice();
		for (String sortDescr: model.getSortDescriptions()) {
			sort_choice.addItem(sortDescr);
		}
		for (String zoneName: model.getZonesNames()) {
			zone_choice.addItem(zoneName);
		}
		sort_choice.select(0);
		zone_choice.select(0);
		sort_choice.addItemListener(this);
		itemStateChanged(new ItemEvent(zone_choice, 0, this, 0));
		startperiod_choice.select(0);
		area = new TextArea(7, 20);
		area.setEditable(false);
		startdate_field = new TextField(10);
		startdate_field.setText("01.05");

		sort_panel.add(sort_label);
		sort_panel.add(sort_choice);

		pan4.add(zone_label);
		pan4.add(zone_choice);

		pan7.add(startperiod_label);
		pan7.add(startperiod_choice);

		pan5.add(startdate_label);
		pan5.add(startdate_field);

		pan6.add(b);
		pan6.add(b2);
		pan6.add(b3);

		main_panel.add(sort_panel);
		main_panel.add(pan4);
		main_panel.add(pan7);
		main_panel.add(pan5);
		main_panel.add(pan6);

		add(main_panel, BorderLayout.NORTH);
		add(area, BorderLayout.SOUTH);

		setSize(600, 325);
		show();
	}

	public void calculate_actionPerformed(ActionEvent e) {
		area.setText("");
		int s = sort_choice.getSelectedIndex();
		int z = zone_choice.getSelectedIndex();
		// System.out.println("Z: "+z);
		StringTokenizer st = new StringTokenizer(startdate_field.getText(), " .");
		int day = 0;
		int month = 0;
		try {
			day = Integer.parseInt(st.nextToken());
			month = Integer.parseInt(st.nextToken());
		} catch (Exception ee) {
			area.append("Дата введена некорректно");
			return;
		}
		if (day > 31) {
			area.append("Дата введена некорректно");
			return;
		}
		if ((month < 1) || (month > 12)) {
			area.append("Дата введена некорректно");
			return;
		}
		int da = YearCalendar.dateToDay(day, month);
        PhenoPrognosis progn = new PhenoPrognosis();
		String result = progn.startPrognoze(model.getSorts().get(s), model.getZones().get(z), da, startperiod_choice.getSelectedIndex());
        area.append(result);
	}

	public void exit_actionPerformed(ActionEvent e) {
		dispose();
	}

	public void about_actionPerformed(ActionEvent e) {
		new AboutDialog(this, "О программе");
	}

	public void itemStateChanged(ItemEvent e) {
		Sort sort = model.getSorts().get(sort_choice.getSelectedIndex());
		int sel = startperiod_choice.getSelectedIndex();
		startperiod_choice.removeAll();
		for (int x = 0; x < sort.getNames().length - 1; x++) {
			startperiod_choice.addItem(Sort.ALLNAMES[sort.getNames()[x]]);
		}
		//startperiod_choice.select(sel);
	}

}

class AboutDialog extends Dialog implements ActionListener {

	public AboutDialog(Frame owner, String title) {
		super(owner, title, true);
		setLayout(new GridLayout(0, 1));
		setSize(300, 200);
		String[] text = { "Методика разработана:",
				"В.М.Новиков, А.И.Южаков, В.Т.Усолкин",
				"Сибирский НИИ земледелия и химизации",
				"Программа разработана:", "А.В.Новиков, В.А.Новиков" };
		for (int x = 0; x < text.length; x++) {
			Panel pan = new Panel(new FlowLayout());
			pan.add(new Label(text[x]));
			add(pan);
		}
		Button b = new Button("OK");
		b.addActionListener(this);
		add(b);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		dispose();
	}
}
