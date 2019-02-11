package Controler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GestionFichierCSV {
    private static File fichier;
    private static ArrayList<String> contenu = new ArrayList<>();

    public static void openFichier(String emplacement) {
        fichier = new File(emplacement);
        contenu = new ArrayList<>();
    }

    public static void lireFichierCSV() {
        try {
            FileReader fr = new FileReader(fichier);
            BufferedReader br = new BufferedReader(fr);

            for (String line = br.readLine(); line != null; line = br.readLine()) {
                contenu.add(line);
            }

            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getContenu() {
        return contenu;
    }
}
