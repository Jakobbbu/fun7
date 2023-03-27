package project.game.fun7.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class GameUser extends BaseModel {

    @NotEmpty
    private String userName;

    @NotNull
    private int usageCount;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usage_count) {
        this.usageCount = usage_count;
    }
}
