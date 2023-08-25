package core.managers.consul.model;

public class Timings {
    private Long getDelaySecondMin;
    private Long getDelaySecondMax;
    private Long imageDelaySecondMin;
    private Long imageDelaySecondMax;
    private Long jsonDelaySecondMin;
    private Long jsonDelaySecondMax;
    private Long htmlDocDelaySecondMin;
    private Long htmlDocDelaySecondMax;
    private Long deflateDelaySecondMin;
    private Long deflateDelaySecondMax;
    private Long denyDelaySecondMin;
    private Long denyDelaySecondMax;
    private Long encodingUtf8DelaySecondMin;
    private Long encodingUtf8DelaySecondMax;

    public Long getGetDelaySecondMin() {
        return getDelaySecondMin;
    }

    public Long getGetDelaySecondMax() {
        return getDelaySecondMax;
    }

    public Long getDeflateDelaySecondMin() {
        return deflateDelaySecondMin;
    }

    public Long getDeflateDelaySecondMax() {
        return deflateDelaySecondMax;
    }

    public Long getDenyDelaySecondMin() {
        return denyDelaySecondMin;
    }

    public Long getDenyDelaySecondMax() {
        return denyDelaySecondMax;
    }

    public Long getEncodingUtf8DelaySecondMin() {
        return encodingUtf8DelaySecondMin;
    }

    public Long getEncodingUtf8DelaySecondMax() {
        return encodingUtf8DelaySecondMax;
    }

    public Long getHtmlDocDelaySecondMin() {
        return htmlDocDelaySecondMin;
    }

    public Long getHtmlDocDelaySecondMax() {
        return htmlDocDelaySecondMax;
    }

    public Long getJsonDelaySecondMin() {
        return jsonDelaySecondMin;
    }

    public Long getJsonDelaySecondMax() {
        return jsonDelaySecondMax;
    }

    public Long getImageDelaySecondMin() {
        return imageDelaySecondMin;
    }

    public Long getImageDelaySecondMax() {
        return imageDelaySecondMax;
    }
}
