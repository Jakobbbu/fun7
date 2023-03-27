package project.game.fun7.model.dto.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"multiplayer", "user-support", "ads"})
public class ServicesInfoDTO {

    private String multiplayer;

    @JsonProperty("user-support")
    private String userSupport;

    private String ads;

    public String getMultiplayer() {
        return multiplayer;
    }

    public void setMultiplayer(String multiplayer) {
        this.multiplayer = multiplayer;
    }

    public String getUserSupport() {
        return userSupport;
    }

    public void setUserSupport(String userSupport) {
        this.userSupport = userSupport;
    }

    public String getAds() {
        return ads;
    }

    public void setAds(String ads) {
        this.ads = ads;
    }
}
