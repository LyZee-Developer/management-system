package com.seng.management_system.model.generateExpense;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.seng.management_system.model.BaseActivateEnity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class GenerateExpense extends BaseActivateEnity  {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Date date;  
    private Double amount;
    private Boolean isKHR = Boolean.FALSE;

    @OneToMany(mappedBy="generateExpense" , fetch=FetchType.LAZY, orphanRemoval=true)
    @JsonManagedReference
    private List<TargetItem> TargetItems;
}
