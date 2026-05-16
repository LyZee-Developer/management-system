package com.seng.management_system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.seng.management_system.dto.DataRefDTO;
import com.seng.management_system.mapper.DataRefMapper;
import com.seng.management_system.util.AuthenticationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.seng.management_system.data_model.data_ref.DataRefDataModel;
import com.seng.management_system.data_model.data_ref.DataRefFilterDataModel;
import com.seng.management_system.exception.ApiException;
import com.seng.management_system.model.DataRef;
import com.seng.management_system.repository.DataRefRepository;
import com.seng.management_system.service.DataRefService;
import com.seng.management_system.specification.DataRefSpecification;

import ch.qos.logback.core.util.StringUtil;

@Service
public class DataRefServiceImpl implements DataRefService {

    @Autowired
    private DataRefRepository dataRefRepository;

    @Override
    public Object list(DataRefFilterDataModel filter) {
        Specification<DataRef> spec = DataRefSpecification.build(filter);
        List<DataRef> data = dataRefRepository.findAll(spec);

        Map<String, List<DataRef>> children = data.stream().filter(d -> d.getParent() != null).collect(Collectors.groupingBy(d -> d.getParent().getCode()));

        List<DataRef> parents = data.stream().filter(d -> d.getParent() == null).peek(d -> d.setChild(children.getOrDefault(d.getCode(), new ArrayList<>()))).toList();

        String code = filter.getCode();
        if (StringUtil.isNullOrEmpty(code)) {
            throw new ApiException("code is required!");
        }
        parents = parents.stream().filter(s -> s.getCode().equals(code)).toList();

        return parents.stream().skip((filter.getPage() - 1) * filter.getRecord()) // Skip preceding pages
                .limit(filter.getRecord())                  // Take only the current page size
                .collect(Collectors.toList());
    }

    @Override
    public DataRef create(DataRefDataModel model) {
        DataRef data = null;
        if (StringUtil.isNullOrEmpty(model.getParentCode())) {
            //********** create the parent of data ref ***********
            data = addNewDataRef(model, data, true);
        } else {
            DataRef parent = dataRefRepository.findByCode(model.getParentCode()).orElseThrow(() -> new ApiException("parent not found!"));
            data = addNewDataRef(model, parent, true);
        }
        return data;
    }

    private DataRef addNewDataRef(DataRefDataModel model, DataRef parent, boolean isCreate) {
        String currentUser = AuthenticationUtil.getCurrentUser();
        Date now = new Date();
        DataRef data = new DataRef();
        DataRef parentData = parent;

        if (!isCreate) {
            data = dataRefRepository.findById(model.getId()).orElseThrow(() -> new ApiException("data ref not found!"));
            parentData = dataRefRepository.findByCode(model.getParentCode()).orElse(null);
        }

        data.setCode(model.getCode());
        data.setCreateBy(currentUser);
        data.setCreateDate(now);
        data.setName(model.getName());
        data.setEnDescription(model.getEnDescription());
        data.setEnName(model.getEnName());
        data.setDescription(model.getDescription());
        data.setValue(model.getValue());
        data.setIsActivate(isCreate ? Boolean.TRUE : model.getIsActivate());
        data.setParent(parentData);
        dataRefRepository.save(data);
        return data;
    }

    @Override
    public DataRef update(Long id, DataRefDataModel model) {
        model.setId(id);
        return addNewDataRef(model, null, Boolean.FALSE);
    }

    @Override
    public boolean delete(Long id) {
        DataRef data = dataRefRepository.findById(id).orElseThrow(() -> new ApiException("data ref not found!"));
        data.setIsActivate(Boolean.FALSE);
        dataRefRepository.save(data);
        return true;
    }
}
