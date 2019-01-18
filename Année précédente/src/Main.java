import View.FenetreImport;

import javax.swing.*;

import static Controler.GestionBDD.*;

public class Main {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		initBDD();
		new FenetreImport();
	}
}
