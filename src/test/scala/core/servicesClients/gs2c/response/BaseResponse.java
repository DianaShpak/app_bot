package core.servicesClients.gs2c.response;


import com.google.gson.JsonObject;
import core.utils.ResponseParser;

public abstract class BaseResponse {
    protected Integer error;
    protected String description;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void parseAndSetResponse(String response) {
        JsonObject jsonObject = ResponseParser.parseStringToJson(response).getAsJsonObject();

        if (jsonObject.has(Parameters.ERROR)) setError(jsonObject.get(Parameters.ERROR).getAsInt());
        if (jsonObject.has(Parameters.DESCRIPTION))
            setDescription(jsonObject.get(Parameters.DESCRIPTION).getAsString());
    }

    private static class Parameters {
        static final String ERROR = "error";
        static final String DESCRIPTION = "description";
    }
}