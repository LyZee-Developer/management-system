package com.seng.management_system.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@JsonIncludeProperties({"id","position","nameKh","nameEn"})
public class Developer  extends BaseActivateEnity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false)
    private String nameEn;
    @Column(nullable=false)
    private String nameKh;
    @Column(nullable=false)
    private Boolean isMale;
    @Column(nullable=false)
    private String position;
}
