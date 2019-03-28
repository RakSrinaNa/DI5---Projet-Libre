package Controler;

import static Controler.GestionBDD.*;
import static Controler.GestionFichierCSV.getContenu;

public class CSVtoBDD {
    public static void GymnaseToBDD() {
        String[] ligne;
        String[] jours;
        for (String donnee : getContenu()) {
            if (!donnee.split(";")[0].equals("Nom")) {
                ligne = donnee.split(";");
                jours = ligne[2].split(",");
                for (String jour : jours) {
                    ajouterGymnase(ligne[0]+", "+jour.trim(), Integer.parseInt(ligne[1]));
                }
            }
        }
    }

    public static void EquipeEtPouleToBDD() {
        String[] ligne;
        for (String donnee : getContenu()) {
            if (!donnee.split(";")[0].equals("Nom")) {
                ligne = donnee.split(";");
                if (trouverPoule(ligne[3]) == null) {
                    ajouterPoule(ligne[3]);
                }
                ajouterEquipe(ligne[0], ligne[1]+", "+ligne[2], ligne[3]);
            }
        }
    }
}
