package com.seng.management_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class IsActivateEnity {

    @Column(nullable = false)
    private Boolean isActivate;

    public void setIsActivate(Boolean isActivate) {
        this.isActivate = isActivate;
    }

    public Boolean getIsActivate() {
        return this.isActivate;
    }
}
