package ClaseFisiere.project;

public class FisierSingle {
    private long id;
    private String nume;
    private String tip;
    private String descriere;
    private String locatie;

    public FisierSingle(String nume, String tip, String descriere, String locatie) {
        this.nume = nume;
        this.tip = tip;
        this.descriere = descriere;
        this.locatie = locatie;
    }

    public FisierSingle(long id, String nume, String tip, String descriere, String locatie) {
        this.id = id;
        this.nume = nume;
        this.tip = tip;
        this.descriere = descriere;
        this.locatie = locatie;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getTip() {
        return tip;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getLocatie() {
        return locatie;
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}