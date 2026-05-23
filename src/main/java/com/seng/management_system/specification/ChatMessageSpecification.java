package com.seng.management_system.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import com.seng.management_system.data_model.chat.chat_message.ChatMessageFilterDataModel;
import com.seng.management_system.model.chat.ChatMessage;

import jakarta.persistence.criteria.Predicate;

public class ChatMessageSpecification {

    public static Specification<ChatMessage> build(ChatMessageFilterDataModel filter) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (filter.getIsActivate() != null) {
                predicates.add(cb.equal(root.get("isActivate"), filter.getIsActivate()));
            } else {
                predicates.add(cb.equal(root.get("isActivate"), Boolean.TRUE));
            }

            if (!ObjectUtils.isEmpty(filter.getId())) {
                predicates.add(cb.equal(root.get("chat").get("id"), filter.getId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
