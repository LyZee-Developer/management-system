package com.seng.management_system.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.seng.management_system.data_model.data_ref.DataRefFilterDataModel;
import com.seng.management_system.model.DataRef;

import ch.qos.logback.core.util.StringUtil;
import jakarta.persistence.criteria.Predicate;

public class DataRefSpecification {

    public static Specification<DataRef> build(DataRefFilterDataModel filter) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getIsActivate() != null) {
                predicates.add(cb.equal(root.get("isActivate"), filter.getIsActivate()));
            } else {
                predicates.add(cb.equal(root.get("isActivate"), Boolean.TRUE));
            }
            //search
            if (!StringUtil.isNullOrEmpty(filter.getSearch())) {
                String search = filter.getSearch().trim();
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("enDescription")), "%" + search.toLowerCase() + "%"),
                        cb.like(cb.lower(root.get("description")), "%" + search.toLowerCase() + "%"),
                        cb.like(cb.lower(root.get("name")), "%" + search.toLowerCase() + "%"),
                        cb.like(cb.lower(root.get("enName")), "%" + search.toLowerCase() + "%"),
                        cb.like(cb.lower(root.get("code")), "%" + search.toLowerCase() + "%")
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
