package com.seng.management_system.model.generateExpense;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.seng.management_system.model.BaseActivateEnity;

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
public class TargetItem extends BaseActivateEnity  {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Double percent;
    @ManyToOne(fetch=FetchType.LAZY)
    private TargetExpense targetExpense;

    @ManyToOne(fetch=FetchType.LAZY)
    @JsonBackReference
    private GenerateExpense generateExpense;

}
