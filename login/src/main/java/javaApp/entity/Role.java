package javaApp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "\"role\"")
@Getter
@Setter
public class Role {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    private String roleName;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
