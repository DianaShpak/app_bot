package core.managers.consul.model;

public class Properties {
    private Hosts hosts;
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

    @Override
    public String toString() {
        return "Properties{" +
                "hosts=" + hosts +
                ", other=" + other +
                ", timings=" + timings +
                '}';
    }
}
