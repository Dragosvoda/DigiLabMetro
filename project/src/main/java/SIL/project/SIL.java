package SIL.project;

public class SIL {
    private int id;
    private String nume;
    private String tip;
    private String descriere;

    public SIL(int id, String nume, String tip, String descriere) {
        this.id = id;
        this.nume = nume;
        this.tip = tip;
        this.descriere = descriere;
    }
    
    public SIL(String nume, String tip, String descriere ) {
        this.nume = nume;
        this.tip = tip;
        this.descriere = descriere;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setTip(String tip) {
        this.tip = tip;    
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
    
    public String getNume() {
        return nume;
    }
    
    public String getTip() {
        return tip;    
    }

    public String getDescriere() {
        return descriere;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object);
    }
}