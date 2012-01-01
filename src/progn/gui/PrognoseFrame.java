package progn.gui;

import progn.YearCalendar;
import progn.entity.Sort;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.StringTokenizer;

public class PrognoseFrame extends Frame implements ItemListener {
	Choice sort_choice;
	Choice zone_choice;
	Choice startperiod_choice;
	TextArea area;
	TextField startdate_field;
	Button b;
	Button b2;
	Button b3;
	PhenoPrognosis progn;

	public PrognoseFrame(PhenoPrognosis progn, String[] sortNames,
			String[] zoneNames) {
		super("Фенологический прогноз");
		this.progn = progn;

		setLayout(new BorderLayout());

		// Panel pan1 = new Panel(new BorderLayout());
		Panel main_panel = new Panel(new GridLayout(0, 1));
		Panel sort_panel = new Panel(new FlowLayout());
		Panel pan4 = new Panel(new FlowLayout());
		Panel pan5 = new Panel(new FlowLayout());
		Panel pan6 = new Panel(new FlowLayout());
		Panel pan7 = new Panel(new FlowLayout());

		Label sort_label = new Label("Сорт:");
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
		for (int x = 0; x < sortNames.length; x++) {
			sort_choice.addItem(sortNames[x]);
		}
		for (int x = 0; x < zoneNames.length; x++) {
			zone_choice.addItem(zoneNames[x]);
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
		progn.startPer = startperiod_choice.getSelectedIndex();
		progn.startPrognoze(s, z, da);
	}

	public void exit_actionPerformed(ActionEvent e) {
		progn.endWork();
		dispose();
	}

	public void about_actionPerformed(ActionEvent e) {
		new AboutDialog(this, "О программе");
	}

	public void itemStateChanged(ItemEvent e) {
		Sort sort = progn.sorts[sort_choice.getSelectedIndex()];
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
