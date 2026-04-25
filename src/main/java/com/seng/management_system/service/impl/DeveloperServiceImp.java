package com.seng.management_system.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.seng.management_system.DataModel.developer.DeveloperDataModel;
import com.seng.management_system.DataModel.developer.DeveloperFilterDataModel;
import com.seng.management_system.constant.GlobalHelper;
import com.seng.management_system.dto.DeveloperDto;
import com.seng.management_system.exception.ApiException;
import com.seng.management_system.mapper.DeveloperMapper;
import com.seng.management_system.model.Developer;
import com.seng.management_system.repository.DeveloperRepository;
import com.seng.management_system.service.DeveloperService;
import com.seng.management_system.specification.DeveloperSpecification;


@Service
public class DeveloperServiceImp implements DeveloperService {
    @Autowired
    DeveloperRepository developerRepository;

    @Override
    public Page<DeveloperDto> list(DeveloperFilterDataModel filter, Pageable pageable){
        Specification<Developer> spec = DeveloperSpecification.build(filter);
        Page<Developer> pageData = developerRepository.findAll(spec, pageable);
        return pageData.map(DeveloperMapper::MapToDto);
    }

    @Override
    public DeveloperDto create(DeveloperDataModel model){
        Developer data = new Developer();
        data.setNameEn(model.getNameEn());
        data.setNameKh(model.getNameKh());
        data.setIsMale(model.getIsMale());
        data.setPosition(model.getPosition());
        data.setCreateBy(GlobalHelper.SYSTEM);
        data.setCreateDate(new Date());
        data.setIsActivate(Boolean.TRUE);
        developerRepository.save(data);
        return DeveloperMapper.MapToDto(data);

    }

    @Override
    public DeveloperDto update(DeveloperDataModel model){
        Long id = Optional.ofNullable(model.getId()).orElseThrow(() -> new ApiException("id is required!"));
        var data = developerRepository.findById(id).orElseThrow(() -> new ApiException("Developer not found!"));
        data.setUpdateBy(GlobalHelper.SYSTEM);
        data.setNameEn(model.getNameEn());
        data.setNameKh(model.getNameKh());
        data.setIsMale(model.getIsMale());
        data.setPosition(model.getPosition());
        data.setUpdateDate(new Date());
        data.setIsActivate(model.getIsActivate());
        developerRepository.save(data);
        return DeveloperMapper.MapToDto(data);
    }

    @Override
    public Boolean delete(Long Id){
        Long id = Optional.ofNullable(Id).orElse(0L);
        var data = developerRepository.findById(id).orElseThrow(() -> new ApiException("Developer not found!"));
        data.setIsActivate(Boolean.FALSE);
        developerRepository.save(data);
        return Boolean.TRUE;
    }
}
