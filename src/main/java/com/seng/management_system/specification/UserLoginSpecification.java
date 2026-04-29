package com.seng.management_system.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.seng.management_system.DataModel.UserLoginFilterDataModel;
import com.seng.management_system.model.UserLogin;

import jakarta.persistence.criteria.Predicate;

public class UserLoginSpecification {
    public static Specification<UserLogin> buildFilter(UserLoginFilterDataModel filter){
        return (root,query,cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("isActivate"), Boolean.TRUE));
             return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
