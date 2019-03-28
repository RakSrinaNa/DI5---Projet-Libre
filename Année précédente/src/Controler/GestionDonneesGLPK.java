package Controler;

import Model.Equipe;
import Model.Gymnase;
import Model.Poule;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static Controler.GestionBDD.*;

public class GestionDonneesGLPK {

	public static void donneesToGLPK() {
		ArrayList<Poule> poules = trouverToutesPoules();
		ArrayList<Gymnase> gymnases = trouverTousGymnases();
		ArrayList<Equipe> equipes;
		try {
			PrintWriter sortieFichier = new PrintWriter(new File("dataCalendrier.dat"));
			sortieFichier.println("param NbPoules:= " + poules.size() + ";");
			sortieFichier.println("param NbEquipesPoule:=");
			for (int i = 0; i < poules.size(); i++) {
				if (i == (poules.size() - 1)) {
					sortieFichier.println((i + 1) + " " + trouverEquipesParPoule(poules.get(i).getNom()).size() + ";");
				} else {
					sortieFichier.println((i + 1) + " " + trouverEquipesParPoule(poules.get(i).getNom()).size());
				}
			}
			sortieFichier.println();
			sortieFichier.println("param NbGymnases:= " + gymnases.size() + ";");
			sortieFichier.println("param CapacitesMax:=");
			for (int j = 0; j < gymnases.size(); j++) {
				if (j == (gymnases.size() - 1)) {
					sortieFichier.println((j + 1) + " " + trouverGymnase(gymnases.get(j).getNom()).getCapacite() + ";");
				} else {
					sortieFichier.println((j + 1) + " " + trouverGymnase(gymnases.get(j).getNom()).getCapacite());
				}
			}
			sortieFichier.println();
			sortieFichier.println("param EquipeGymnase:=");
			String ligne;
			String ligneParEquipe;
			int idGymnase;
			boolean dernierePoule = false, derniereEquipe = false;
			for (int k = 0; k < poules.size(); k++) {
				if (k == (poules.size() - 1)) {
					dernierePoule = true;
				}
				ligne = "[" + (k + 1) + ",*,*]:  ";
				for (int m = 1; m <= gymnases.size(); m++) {
					ligne += (m + " ");
				}
				ligne += " :=";
				sortieFichier.println(ligne);
				equipes = trouverEquipesParPoule(poules.get(k).getNom());
				for (int n = 0; n < equipes.size(); n++) {
					if (n == (equipes.size() - 1)) {
						derniereEquipe = true;
					}
					idGymnase = equipes.get(n).getIdGymnase();
					ligneParEquipe = String.valueOf(n + 1);
					for (int p = 1; p <= gymnases.size(); p++) {
						if (n == (equipes.size() - 1) && p == gymnases.size() && derniereEquipe && dernierePoule) {
							if (gymnases.get(p - 1).getId() == idGymnase) {
								ligneParEquipe += (" " + 1 + ";");
							} else {
								ligneParEquipe += (" " + 0 + ";");
							}
						} else {
							if (gymnases.get(p - 1).getId() == idGymnase) {
								ligneParEquipe += (" " + 1);
							} else {
								ligneParEquipe += (" " + 0);
							}
						}
					}
					sortieFichier.println(ligneParEquipe);
				}
			}
			sortieFichier.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void GLPKTodonnees() {
		ArrayList<Poule> poules = new ArrayList<>(trouverToutesPoules());
		ArrayList<ArrayList<Equipe>> equipes = new ArrayList<>();
		for (Poule poule : poules) {
			equipes.add(trouverEquipesParPoule(poule.getNom()));
		}
		File fichier = new File("outputCalendrier.txt");
		String ligne;
		String[] elementLigne;
		boolean placement = false;
		boolean parcoursMatch = true;
		try (Scanner scanner = new Scanner(fichier)) {
			while (scanner.hasNextLine() && !placement) {
				ligne = scanner.nextLine();
				if (ligne.contains("Column name")) {
					placement = true;
				}
			}
			scanner.nextLine();
			while (scanner.hasNextLine() && parcoursMatch) {
				ligne = scanner.nextLine();
				if (!ligne.contains("1")) {
					parcoursMatch = false;
				} else {
					elementLigne = ligne.split("]");
					if (elementLigne[1].substring(15, 23).contains("1")) {
						elementLigne = elementLigne[0].split("\\[");
						elementLigne = elementLigne[1].split(",");
						ajouterMatch(
								equipes.get(Integer.parseInt(elementLigne[0]) - 1)
										.get(Integer.parseInt(elementLigne[1]) - 1).getId(),
								equipes.get(Integer.parseInt(elementLigne[0]) - 1)
										.get(Integer.parseInt(elementLigne[2]) - 1).getId(),
								Integer.parseInt(elementLigne[3]));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
