package com.seng.management_system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.seng.management_system.data_model.bug.BugDataModel;
import com.seng.management_system.data_model.bug.BugFilterDataModel;
import com.seng.management_system.dto.BugDto;


public interface BugService {
    Page<BugDto> list(BugFilterDataModel filter, Pageable pageable);
    BugDto create(BugDataModel model);
    BugDto update(BugDataModel model);
    Boolean delete(Long Id);
    Boolean comment(Long Id,String comment,Long developerId);
}
