package com.seng.management_system.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.seng.management_system.DataModel.generateExpense.targetExpense.TargetExpenseFilterDataModel;
import com.seng.management_system.model.generateExpense.TargetExpense;

import jakarta.persistence.criteria.Predicate;

public class TargetExpenseSpecification {
    public static Specification<TargetExpense> build(TargetExpenseFilterDataModel filter){
        return (root,query,cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("isActivate"), Boolean.TRUE));
             return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
