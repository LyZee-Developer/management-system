package com.seng.management_system.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.seng.management_system.DataModel.generateExpense.generateExpense.GenerateExpenseFilterDataModel;
import com.seng.management_system.model.generateExpense.GenerateExpense;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.criteria.Predicate;

public class GenerateExpenseSpecification {
    public static Specification<GenerateExpense> build(GenerateExpenseFilterDataModel filter){
        return (root,query,cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("isActivate"), Boolean.TRUE));

                if(!StringUtils.isEmpty(filter.getSearch())){
                    String searchLike = filter.getSearch().trim().toLowerCase();
                    predicates.add(cb.or(
                        cb.like(cb.lower(root.get("nameKh")), "%"+searchLike+"%"),
                        cb.like(cb.lower(root.get("nameEn")), "%"+searchLike+"%"),
                        cb.like(cb.lower(root.get("description")), "%"+searchLike+"%")
                    ));
                }
             return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
