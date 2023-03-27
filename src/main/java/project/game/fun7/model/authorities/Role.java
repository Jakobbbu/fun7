package project.game.fun7.model.authorities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;
import project.game.fun7.model.BaseModel;
import project.game.fun7.model.GameUser;

import java.util.Collection;

@Entity
public class Role extends BaseModel {

    @NotEmpty
    private String name;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    private Collection<GameUser> users;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Privilege> privileges;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<GameUser> getUsers() {
        return users;
    }

    public void setUsers(Collection<GameUser> users) {
        this.users = users;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }
}
