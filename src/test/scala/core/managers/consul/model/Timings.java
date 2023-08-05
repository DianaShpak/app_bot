package core.managers.consul.model;


public class Timings {
    private Long spinRandomDelaySecondMin;
    private Long spinRandomDelaySecondMax;

    public Long getSpinRandomDelaySecondMin() {
        return spinRandomDelaySecondMin;
    }

    public Long getSpinRandomDelaySecondMax() {
        return spinRandomDelaySecondMax;
    }


    @Override
    public String toString() {
        return "Timings{" +
                ", spinRandomDelaySecondMin=" + spinRandomDelaySecondMin +
                ", spinRandomDelaySecondMax=" + spinRandomDelaySecondMax +
                '}';
    }
}
