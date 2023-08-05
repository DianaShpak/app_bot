package core.managers.consul.model;

public class Properties {

    private Hosts hosts;
    private Hosts hosts2;
    private Other other;
    private Timings timings;

    public Hosts getHosts() {
        return hosts;
    }

    public void setHosts(Hosts hosts) {
        this.hosts = hosts;
    }

    public Other getOther() {
        return other;
    }

    public void setOther(Other other) {
        this.other = other;
    }

    public Timings getTimings() {
        return timings;
    }

    public void setTimings(Timings timings) {
        this.timings = timings;
    }
    public Hosts getHosts2() {
        return hosts2;
    }

    public void setHosts2(Hosts hosts2) {
        this.hosts2 = hosts2;
    }

    @Override
    public String toString() {
        return "Properties{" +
                "hosts=" + hosts +
                ", hosts2=" + hosts2 +
                ", other=" + other +
                ", timings=" + timings +
                '}';
    }
}
