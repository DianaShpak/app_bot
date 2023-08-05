package core.servicesClients.gs2c.model;

import java.util.List;

public class Tournament {
    private Long id;
    private String status;
    private String name;
    private String clientMode;
    private Long startDate;
    private Long endDate;
    private Boolean optin;
    private List<String> optJurisdiction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientMode() {
        return clientMode;
    }

    public void setClientMode(String clientMode) {
        this.clientMode = clientMode;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Boolean getOptin() {
        return optin;
    }

    public void setOptin(Boolean optin) {
        this.optin = optin;
    }

    public List<String> getOptJurisdiction() {
        return optJurisdiction;
    }

    public void setOptJurisdiction(List<String> optJurisdiction) {
        this.optJurisdiction = optJurisdiction;
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", name='" + name + '\'' +
                ", clientMode='" + clientMode + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", optin=" + optin +
                ", optJurisdiction=" + optJurisdiction +
                '}';
    }
}
