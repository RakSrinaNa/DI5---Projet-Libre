package Model;

public class Match {
    private int id;
    private int idEquipeDom;
    private int idEquipeExt;
    private int journee;

    public Match(int id, int idEquipeDom, int idEquipeExt, int journee) {
        this.id = id;
        this.idEquipeDom = idEquipeDom;
        this.idEquipeExt = idEquipeExt;
        this.journee = journee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEquipeDom() {
        return idEquipeDom;
    }

    public void setIdEquipeDom(int idEquipeDom) {
        this.idEquipeDom = idEquipeDom;
    }

    public int getIdEquipeExt() {
        return idEquipeExt;
    }

    public void setIdEquipeExt(int idEquipeExt) {
        this.idEquipeExt = idEquipeExt;
    }

    public int getJournee() {
        return journee;
    }

    public void setJournee(int journee) {
        this.journee = journee;
    }
}
