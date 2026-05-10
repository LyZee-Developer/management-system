package com.seng.management_system.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.seng.management_system.data_model.bug.BugFilterDataModel;
import com.seng.management_system.model.Bug;

import ch.qos.logback.core.util.StringUtil;
import jakarta.persistence.criteria.Predicate;

public class BugSpecification {
    public static Specification<Bug> build(BugFilterDataModel filter){
        return (root,query,cb) -> { 

            List<Predicate> predicates = new ArrayList<>();
            if(filter.getIsActivate() != null){
                predicates.add(cb.equal(root.get("isActivate"), filter.getIsActivate()));
            } 
            else {
                predicates.add(cb.equal(root.get("isActivate"), Boolean.TRUE));
            }
            //search
            if(!StringUtil.isNullOrEmpty(filter.getSearch())){
                String search = filter.getSearch().trim();
                predicates.add(cb.or(
                    cb.like(cb.lower(root.get("position")), "%"+ search.toLowerCase() +"%"),
                    cb.like(cb.lower(root.get("nameKh")), "%"+ search.toLowerCase() +"%"),
                    cb.like(cb.lower(root.get("nameEn")), "%"+ search.toLowerCase() +"%")
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
