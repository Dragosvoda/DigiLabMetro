package ClaseFisiere.project;

public class Raport {
    int id;
    String nume;
    String tipul;
    String descriere;
    int SIL;

    public Raport(int id, String nume, String tipul, String descriere, int SIL) {
        this.id = id;
        this.nume = nume;
        this.tipul = tipul;
        this.descriere = descriere;
        this.SIL = SIL;
    }
    
    public Raport(String nume, String tipul, String descriere, int SIL) {
        this.nume = nume;
        this.tipul = tipul;
        this.descriere = descriere;
        this.SIL = SIL;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTipul(String tipul) {
        this.tipul = tipul;
    }

    public String getTipul() {
        return tipul;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }
    
    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
    
    public String getDescriere() {
        return descriere;    
    }

    public void setSIL(int SIL) {
        this.SIL = SIL;
    }

    public int getSIL() {
        return SIL;
    }

    @Override
    public String toString() {
        // TODO Implement this method
        return super.toString();
    }

    @Override
    public int hashCode() {
        // TODO Implement this method
        return super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        // TODO Implement this method
        return super.equals(object);
    }
}