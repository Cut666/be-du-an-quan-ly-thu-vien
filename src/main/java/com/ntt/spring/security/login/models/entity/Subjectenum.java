package com.ntt.spring.security.login.models.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ntt.spring.security.login.models.fileenum.Sub;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Subjectenum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private Sub sub;
    @JsonBackReference
    @ManyToMany(mappedBy = "subjectenums")
    private List<Subjects> subjects;
}
