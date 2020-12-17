package Users.project;

public class Users {
    private int id;
    private String nume;
    private String parola;

    public Users(int id, String nume, String parola) {
        this.id = id;
        this.nume = nume;
        this.parola = parola;
    }

    public Users(String nume, String parola) {
        this.nume = nume;
        this.parola = parola;
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

    public String getNume() {
        return nume;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getParola() {
        return parola;
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