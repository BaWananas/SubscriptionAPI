package com.learnings.learningproject.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
//Rename the table because of the SQL "group" restricted keyword.
@Table(name = "team")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group {

    public Group(String name, String description, long associationId) {
        this.name = name;
        if (description != null) this.description = description;
        this.associationId = associationId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private long associationId;
    @NotNull
    private String name;
    private String description;
    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
    private List<Subscription> subscriptions;
}
