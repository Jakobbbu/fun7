package project.game.fun7.model;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseModel extends AuditModel{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

