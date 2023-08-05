package core.managers.consul.model;

public class Hosts {

    private String emul;

    public String getEmul() {
        return emul;
    }

    public void setEmul(String emul) {
        this.emul = emul;
    }


    @Override
    public String toString() {
        return "Hosts{" +
                "emul='" + emul + '\'' +
                '}';
    }
}
