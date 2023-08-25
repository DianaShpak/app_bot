package core.managers.consul.model;

import java.util.List;

public class Other {

    private String scenario;
    private Integer usersPerContainer;
    private Integer during;

    public Integer getUsersPerContainer() {
        return usersPerContainer;
    }

    public Integer getDuring() {
        return during;
    }


    public String getScenario() {
        return scenario;
    }



    @Override
    public String toString() {
        return "Other{" +
                "Scenario=" + scenario +
                "usersPerContainer=" + usersPerContainer +
                "during=" + during +
                '}';
    }
}
