package com.seng.management_system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.seng.management_system.DataModel.developer.DeveloperDataModel;
import com.seng.management_system.DataModel.developer.DeveloperFilterDataModel;
import com.seng.management_system.dto.DeveloperDto;


public interface  DeveloperService {
    Page<DeveloperDto> list(DeveloperFilterDataModel filter, Pageable pageable);
    DeveloperDto create(DeveloperDataModel model);
    DeveloperDto update(DeveloperDataModel model);
    Boolean delete(Long Id);
}
