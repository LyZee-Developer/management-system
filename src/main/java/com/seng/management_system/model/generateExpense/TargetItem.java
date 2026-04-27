package com.seng.management_system.model.generateExpense;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.seng.management_system.model.IsActivateEnity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class TargetItem extends IsActivateEnity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Double percent;
    @ManyToOne(fetch=FetchType.LAZY)
    @JsonIncludeProperties({"description","nameKh","nameEn","id"})
    private TargetExpense targetExpense;

    @ManyToOne(fetch=FetchType.LAZY)
    @JsonBackReference
    @JsonIgnore
    private GenerateExpense generateExpense;

}
