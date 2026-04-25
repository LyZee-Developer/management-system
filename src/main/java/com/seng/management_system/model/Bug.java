    package com.seng.management_system.model;

    import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

    @Entity
    @Setter
    @Getter
    public class Bug extends BaseActivateEnity{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable=false, length=255)
        private String title;

        @Column(nullable=true)
        private String description;

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(
            name="bug_developer",
            joinColumns = @JoinColumn( name = "bug_id"),
            inverseJoinColumns= @JoinColumn( name = "developer_id")
        )
        private List<Developer> developers;
        
        @OneToMany(mappedBy="bug", orphanRemoval=true, cascade= CascadeType.ALL)
        @JsonManagedReference
        private List<BugComment> comments;

    }
