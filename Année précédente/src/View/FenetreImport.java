package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import static Controler.CSVtoBDD.EquipeEtPouleToBDD;
import static Controler.CSVtoBDD.GymnaseToBDD;
import static Controler.GestionBDD.nettoyerBDD;
import static Controler.GestionDonneesGLPK.GLPKTodonnees;
import static Controler.GestionDonneesGLPK.donneesToGLPK;
import static Controler.GestionFichierCSV.lireFichierCSV;
import static Controler.GestionFichierCSV.openFichier;

public class FenetreImport extends JFrame {

	public FenetreImport() {
		// Création de la fenêtre principale
		this.setTitle("Création d'un calendrier sportif");
		this.setSize(500, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		// Séparation du texte et des boutons dans deux panels
		JPanel panTexte = new JPanel();
		panTexte.setPreferredSize(new Dimension(400, 100));
		JTextArea texte = new JTextArea("Afin de procéder à l'élaboration du calendrier,\n"
				+ "veuillez suivre les trois étapes suivantes :\n"
				+ "  - Importer un fichier CSV contenant les gymnases\n"
				+ "  - Importer un fichier CSV contenant les équipes\n" + "  - Lancer la création du calendrier\n");
		texte.setEditable(false);
		texte.setOpaque(false);
		texte.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));
		panTexte.add(texte);
		JPanel panBoutons = new JPanel();
		panBoutons.setPreferredSize(new Dimension(450, 100));

		// CrÃ©ation du bouton d'import des gymnases
		JButton boutonImportGymnase = new JButton("Import des gymnases");
		boutonImportGymnase.setPreferredSize(new Dimension(220, 40));
		boutonImportGymnase.setFont(boutonImportGymnase.getFont().deriveFont(14.0f));
		panBoutons.add(boutonImportGymnase);

		// CrÃ©ation du bouton d'import des Ã©quipes et des poules
		JButton boutonImportEquipe = new JButton("Import des équipes et poules");
		boutonImportEquipe.setPreferredSize(new Dimension(220, 40));
		boutonImportEquipe.setFont(boutonImportGymnase.getFont().deriveFont(14.0f));
		boutonImportEquipe.setEnabled(false);
		panBoutons.add(boutonImportEquipe);

		// CrÃ©ation du bouton de lancement de crÃ©ation du calendrier
		JButton boutonRun = new JButton("Fabriquer le calendrier");
		boutonRun.setPreferredSize(new Dimension(220, 40));
		boutonRun.setFont(boutonImportGymnase.getFont().deriveFont(14.0f));
		boutonRun.setEnabled(false);
		panBoutons.add(boutonRun);

		// Action Ã  rÃ©aliser lors du clic sur le bouton d'import des gymnses
		boutonImportGymnase.addActionListener((ActionEvent arg0) -> {
			nettoyerBDD();
			File repertoireCourant = null;
			try {
				repertoireCourant = new File(".").getCanonicalFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			JFileChooser choisirFichierGymnase = new JFileChooser(repertoireCourant);
			choisirFichierGymnase.showOpenDialog(null);

			if (choisirFichierGymnase.getSelectedFile() != null) {
				openFichier(choisirFichierGymnase.getSelectedFile().toString());
				lireFichierCSV();
				GymnaseToBDD();
				boutonImportEquipe.setEnabled(true);
			}
		});

		// Action à réaliser lors du clic sur le bouton d'import des équipes
		boutonImportEquipe.addActionListener((ActionEvent arg0) -> {
			File repertoireCourant = null;
			try {
				repertoireCourant = new File(".").getCanonicalFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			JFileChooser choisirFichierEquipe = new JFileChooser(repertoireCourant);
			choisirFichierEquipe.showOpenDialog(null);

			if (choisirFichierEquipe.getSelectedFile() != null) {
				System.out.println(choisirFichierEquipe.getSelectedFile());
				openFichier(choisirFichierEquipe.getSelectedFile().toString());
				lireFichierCSV();
				EquipeEtPouleToBDD();
				boutonRun.setEnabled(true);
			}
		});

		// Action à réaliser lors du clic sur le bouton de création du
		// calendrier
		boutonRun.addActionListener((ActionEvent arg0) -> {
			donneesToGLPK();
			try {
				Runtime.getRuntime()
						.exec("cmd /c start C:\\Users\\Romain\\Documents\\Projet_libre\\ProjetLibre\\test.bat");
				Thread.sleep(4000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			GLPKTodonnees();
			FenetreCalendrier zd = new FenetreCalendrier(null, "Nouveau Calendrier", true);
		});

		// Placement des objets dans la fenêtre
		this.getContentPane().setLayout(new FlowLayout());
		this.getContentPane().add(panTexte);
		this.getContentPane().add(panBoutons, BorderLayout.SOUTH);
		this.setVisible(true);
	}
}