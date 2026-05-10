package com.seng.management_system.service;

import java.util.List;

import com.seng.management_system.dto.DataRefDTO;
import org.springframework.data.domain.Pageable;

import com.seng.management_system.data_model.data_ref.DataRefDataModel;
import com.seng.management_system.data_model.data_ref.DataRefFilterDataModel;
import com.seng.management_system.model.DataRef;

public interface DataRefService {

    Object list(DataRefFilterDataModel filter);

    DataRef create(DataRefDataModel model);

    DataRef update(Long id, DataRefDataModel model);

    boolean delete(Long id);
}
