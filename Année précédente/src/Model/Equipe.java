package Model;

public class Equipe {
    private int id;
    private String nom;
    private int idGymnase;
    private int idPoule;

    public Equipe(int id, String nom, int idGymnase, int idPoule) {
        this.id = id;
        this.nom = nom;
        this.idGymnase = idGymnase;
        this.idPoule = idPoule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getIdGymnase() {
        return idGymnase;
    }

    public void setIdGymnase(int idGymnase) {
        this.idGymnase = idGymnase;
    }

    public int getIdPoule() {
        return idPoule;
    }

    public void setIdPoule(int idPoule) {
        this.idPoule = idPoule;
    }
}
