package project.game.fun7.model.authorities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import project.game.fun7.model.BaseModel;

import java.util.Collection;

@Entity
public class Privilege extends BaseModel {
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
