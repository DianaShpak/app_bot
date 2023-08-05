package core.servicesClients.gs2c.response;


import core.servicesClients.gs2c.model.Race;
import core.servicesClients.gs2c.model.Tournament;

import java.util.List;

public class PromoActiveListResponse extends BaseResponse {
    protected Long serverTime;
    private List<Tournament> tournaments;
    private List<Race> races;

    public Long getServerTime() {
        return serverTime;
    }

    public void setServerTime(Long serverTime) {
        this.serverTime = serverTime;
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public List<Race> getRaces() {
        return races;
    }

    public void setRaces(List<Race> races) {
        this.races = races;
    }

    @Override
    public String toString() {
        return "PromoActiveListResponse{" +
                "error=" + error +
                ", description='" + description + '\'' +
                ", serverTime=" + serverTime +
                ", tournaments=" + tournaments +
                ", races=" + races +
                '}';
    }
}
