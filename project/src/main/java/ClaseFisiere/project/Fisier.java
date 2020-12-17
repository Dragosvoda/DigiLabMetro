package ClaseFisiere.project;

public class Fisier {
    private long id;
    private String locatie;
    private int silLink;
    private int raportLink;
    
    public Fisier(String locatie, int silLink, int raportLink) {
        this.locatie = locatie;
        this.silLink = silLink;
        this.raportLink = raportLink;
    }

    public Fisier(long id, String locatie, int silLink, int raportLink) {
        this.id = id;
        this.locatie = locatie;
        this.silLink = silLink;
        this.raportLink = raportLink;
    }

    public Fisier(int id, String path) {
        this.id = id;
        this.locatie = path;
    }

    public Fisier(String path) {
        this.locatie = path;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setSilLink(int silLink) {
        this.silLink = silLink;
    }

    public int getSilLink() {
        return silLink;
    }

    public void setRaportLink(int raportLink) {
        this.raportLink = raportLink;
    }

    public int getRaportLink() {
        return raportLink;
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