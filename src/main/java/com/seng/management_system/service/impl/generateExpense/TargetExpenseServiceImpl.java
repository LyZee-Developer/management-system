package com.seng.management_system.service.impl.generateExpense;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.seng.management_system.DataModel.generateExpense.targetExpense.TargetExpenseDataModel;
import com.seng.management_system.DataModel.generateExpense.targetExpense.TargetExpenseFilterDataModel;
import com.seng.management_system.constant.GlobalHelper;
import com.seng.management_system.dto.TargetExpenseDTO;
import com.seng.management_system.exception.ApiException;
import com.seng.management_system.mapper.generateExpense.TargetExpenseMapper;
import com.seng.management_system.model.generateExpense.TargetExpense;
import com.seng.management_system.repository.generateExpense.TargetExpenseRepository;
import com.seng.management_system.service.TargetExpenseService;
import com.seng.management_system.specification.TargetExpenseSpecification;

@Service
class TargetExpenseServiceImpl implements TargetExpenseService {
    @Autowired
    TargetExpenseRepository targetExpenseRepository;

    @Override
    public Page<TargetExpenseDTO> list(TargetExpenseFilterDataModel filter , Pageable pageable){
        Specification<TargetExpense> spec = TargetExpenseSpecification.build(filter);
        Page<TargetExpense> result = targetExpenseRepository.findAll(spec ,pageable);
        return result.map(TargetExpenseMapper::toDTO);
    }

    @Override
    public TargetExpenseDTO create(TargetExpenseDataModel model){
        TargetExpense data = new TargetExpense();
        updateData(data,model);
        targetExpenseRepository.save(data);
        return TargetExpenseMapper.toDTO(data);
    }

    @Override
    public TargetExpenseDTO update(Long id,TargetExpenseDataModel model){
        TargetExpense data = targetExpenseRepository.findById(id).orElseThrow(()->new ApiException("target expense not found!"));
        updateData(data,model);
        targetExpenseRepository.save(data);
        return TargetExpenseMapper.toDTO(data);
    }

    private TargetExpense updateData(TargetExpense data,TargetExpenseDataModel model){
       
        data.setNameEn(model.getNameEn());
        data.setNameKh(model.getNameKh());
        data.setDescription(model.getDescription());
        if(ObjectUtils.isEmpty(model.getId())){
            data.setIsActivate(Boolean.TRUE);
            data.setCreateBy(GlobalHelper.ADMIN);
            data.setCreateDate(new Date());
        }else{
            data.setIsActivate(model.getIsActivate());
            data.setUpdateBy(GlobalHelper.ADMIN);
            data.setUpdateDate(new Date());
        }
        return data;
    }
}
