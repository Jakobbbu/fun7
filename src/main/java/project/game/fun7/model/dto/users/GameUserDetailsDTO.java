package project.game.fun7.model.dto.users;

public class GameUserDetailsDTO {

    private String userName;

    private int usageCount;

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }
}
