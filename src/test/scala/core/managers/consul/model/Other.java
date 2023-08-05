package core.managers.consul.model;

import java.util.List;

public class Other {

    private Boolean isBalanceTransfer;
    private String scenario;
    private Integer usersPerContainer;
    private Integer during;

    public Integer getUsersPerContainer() {
        return usersPerContainer;
    }

    public Integer getDuring() {
        return during;
    }

    public Boolean getBalanceTransfer() {
        return isBalanceTransfer;
    }

    public String getScenario() {
        return scenario;
    }



    @Override
    public String toString() {
        return "Other{" +
                "isBalanceTransfer=" + isBalanceTransfer +
                "Scenario=" + scenario +
                "usersPerContainer=" + usersPerContainer +
                "during=" + during +

                '}';
    }
}
