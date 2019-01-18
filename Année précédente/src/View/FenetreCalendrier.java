package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import Controler.GestionBDD;
import Model.Equipe;
import Model.Gymnase;
import Model.Match;
import Model.Poule;

public class FenetreCalendrier extends JDialog {

	private static final long serialVersionUID = 1L;

	private JLabel pouleSelecLabel, journeeSelecLabel;
	private JPanel panPoules, panJournee, content, panMatch;

	private JComboBox<Poule> listePoule;
	private JComboBox<Integer> journee;

	private Font panelFont = new Font(Font.DIALOG, Font.PLAIN, 14);
	private Font matchFont = new Font(Font.DIALOG, Font.PLAIN, 18);

	private ArrayList<Poule> poules = new ArrayList<>();

	private ArrayList<Match> matchs = new ArrayList<>();

	private int nbJournees;
	private int nbMatchs;

	public FenetreCalendrier(JFrame parent, String title, boolean modal) {
		super(parent, title, modal);
		poules = GestionBDD.trouverToutesPoules();
		System.out.println(poules.get(0).getNom());
		matchs = GestionBDD.trouverMatchJournee(poules.get(0), 1);
		nbMatchs = matchs.size();
		nbJournees = GestionBDD.trouverNbJournee(poules.get(0));

		UIManager.put("Panel.font", panelFont);
		UIManager.put("Label.font", matchFont);
		this.setSize(1560, 810);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		this.initComponent();
		this.setVisible(true);

	}

	private void initComponent() {
		// Les Poules
		panPoules = new JPanel();
		panPoules.setBackground(Color.white);
		panPoules.setPreferredSize(new Dimension(440, 60));
		listePoule = new JComboBox<>();
		for (int i = 0; i < poules.size(); i++)
			listePoule.addItem(poules.get(i));
		listePoule.addActionListener(new PouleItemState());
		panPoules.setBorder(BorderFactory.createTitledBorder("Poules"));
		pouleSelecLabel = new JLabel("Sélectionner une poule :");
		panPoules.add(pouleSelecLabel);
		panPoules.add(listePoule);

		// La journée
		panJournee = new JPanel();
		panJournee.setBackground(Color.white);
		panJournee.setPreferredSize(new Dimension(440, 60));
		journee = new JComboBox<>();
		for (int i = 1; i <= nbJournees; i++)
			journee.addItem(i);
		journee.setSelectedIndex(0);
		journee.addActionListener(new JourneeItemState());
		panJournee.setBorder(BorderFactory.createTitledBorder("Journées"));
		journeeSelecLabel = new JLabel("Sélectionner la journée :");
		panJournee.add(journeeSelecLabel);
		panJournee.add(journee);

		// Panneau matchs
		panMatch = new JPanel();
		GridLayout gridMatch = new GridLayout(0, 4);
		panMatch.setBackground(Color.white);
		panMatch.setLayout(gridMatch);
		panMatch.setPreferredSize(new Dimension(1000, 650));
		updateMatchs();

		content = new JPanel();

		content.setBackground(Color.white);
		content.add(panPoules, BorderLayout.PAGE_START);
		content.add(panJournee, BorderLayout.PAGE_START);
		content.add(panMatch, BorderLayout.CENTER);

		this.getContentPane().add(content, BorderLayout.CENTER);
	}

	public void updateMatchs() {
		panMatch.removeAll();
		panMatch.setBorder(BorderFactory.createTitledBorder(
				listePoule.getSelectedItem().toString() + ", Journée " + journee.getSelectedItem()));
		for (int i = 0; i < nbMatchs; i++) {
			Equipe eqDomicile = GestionBDD.trouverEquipeId(matchs.get(i).getIdEquipeDom());
			panMatch.add(new JLabel(eqDomicile.getNom()));

			panMatch.add(new JLabel(" v"));

			Equipe eqExterieur = GestionBDD.trouverEquipeId(matchs.get(i).getIdEquipeExt());
			panMatch.add(new JLabel(eqExterieur.getNom()));

			Gymnase gymnase = GestionBDD.trouverGymnaseEquipe(eqDomicile);
			panMatch.add(new JLabel(gymnase.getNom()));
		}
		panMatch.revalidate();
	}

	/**
	 * Classe interne implémentant l'interface ActionListener
	 * 
	 * @author Romain
	 *
	 */
	class PouleItemState implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Poule pouleSelec = (Poule) listePoule.getSelectedItem();
			nbJournees = GestionBDD.trouverNbJournee(pouleSelec);
			matchs = GestionBDD.trouverMatchJournee(pouleSelec, (Integer) journee.getSelectedItem());
			nbMatchs = matchs.size();
			updateMatchs();
		}

	}

	/**
	 * Classe interne implémentant l'interface ActionListener
	 * 
	 * @author Romain
	 *
	 */
	class JourneeItemState implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			matchs = GestionBDD.trouverMatchJournee((Poule) listePoule.getSelectedItem(),
					(Integer) journee.getSelectedItem());
			updateMatchs();
		}
	}
}
