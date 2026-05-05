package com.seng.management_system.model.generateExpense;

import com.seng.management_system.model.BaseActivateEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class TargetExpense extends BaseActivateEntity  {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nameEn;
    private String nameKh;
    private String description;
}
