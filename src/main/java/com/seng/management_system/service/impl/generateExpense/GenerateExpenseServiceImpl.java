package com.seng.management_system.service.impl.generateExpense;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.seng.management_system.constant.GlobalHelper;
import com.seng.management_system.data_model.generateExpense.generateExpense.GenerateExpenseDataModel;
import com.seng.management_system.data_model.generateExpense.generateExpense.GenerateExpenseFilterDataModel;
import com.seng.management_system.dto.GenerateExpenseDTO;
import com.seng.management_system.exception.ApiException;
import com.seng.management_system.mapper.generateExpense.GenerateExpenseMapper;
import com.seng.management_system.model.generateExpense.GenerateExpense;
import com.seng.management_system.model.generateExpense.TargetExpense;
import com.seng.management_system.model.generateExpense.TargetItem;
import com.seng.management_system.repository.generateExpense.GenerateExpenseRepository;
import com.seng.management_system.repository.generateExpense.TargetExpenseRepository;
import com.seng.management_system.repository.generateExpense.TargetItemRepository;
import com.seng.management_system.service.GenerateExpenseService;
import com.seng.management_system.specification.GenerateExpenseSpecification;

@Service
class GenerateExpenseServiceImpl implements GenerateExpenseService {

    @Autowired
    GenerateExpenseRepository generateExpenseRepository;

    @Autowired
    TargetExpenseRepository targetExpenseRepository;

    @Autowired
    TargetItemRepository targetItemRepository;

    @Override
    public Page<GenerateExpenseDTO> list(GenerateExpenseFilterDataModel filter, Pageable pageable) {
        Specification<GenerateExpense> spec = GenerateExpenseSpecification.build(filter);
        Page<GenerateExpense> result = generateExpenseRepository.findAll(spec, pageable);
        return result.map(GenerateExpenseMapper::toDTO);
    }

    @Override
    @Transactional
    public GenerateExpenseDTO create(GenerateExpenseDataModel model) {
        boolean isOver = isOver100(model.getTargetItems());
        if (isOver) {
            throw new ApiException("Your percent is over 100%");
        }
        GenerateExpense data = new GenerateExpense();
        data.setDate(new Date());
        data.setAmount(model.getAmount());
        data.setIsKHR(model.getIsCurrencyKH());
        data.setIsActivate(Boolean.TRUE);
        data.setCreateBy(GlobalHelper.ADMIN);
        data.setCreateDate(new Date());

        generateExpenseRepository.save(data);

        List<TargetItem> tgtItm = new ArrayList<>();

        for (GenerateExpenseDataModel.TargetItem item : model.getTargetItems()) {
            TargetExpense targetExpense = targetExpenseRepository.findById(item.getTargetItemId()).orElseThrow(() -> new ApiException("target expense not found!"));
            TargetItem dt = new TargetItem();

            dt.setPercent(item.getPercent());
            dt.setGenerateExpense(data);
            dt.setIsActivate(Boolean.TRUE);
            dt.setTargetExpense(targetExpense);

            tgtItm.add(dt);
            targetItemRepository.save(dt);
        }

        data.setTargetItems(tgtItm);
        generateExpenseRepository.save(data);
        return GenerateExpenseMapper.toDTO(data);
    }

    @Override
    @Transactional
    public GenerateExpenseDTO update(Long id, GenerateExpenseDataModel model) {
        boolean isOver = isOver100(model.getTargetItems());
        if (isOver) {
            throw new ApiException("Your percent is over 100%");
        }
        GenerateExpense data = generateExpenseRepository.findById(id).orElseThrow(() -> new ApiException("generate expense not found!"));
        data.setDate(new Date());
        data.setAmount(model.getAmount());
        data.setIsKHR(model.getIsCurrencyKH());
        if (model.getIsActivate() != null) {
            data.setIsActivate(model.getIsActivate());
        }
        data.setUpdateBy(GlobalHelper.ADMIN);
        data.setUpdateDate(new Date());
        List<TargetItem> existingItems = data.getTargetItems();
        if (!ObjectUtils.isEmpty(data.getTargetItems())) {
            existingItems.clear();
        }
        for (GenerateExpenseDataModel.TargetItem item : model.getTargetItems()) {
            TargetExpense targetExpense = targetExpenseRepository.findById(item.getId()).orElseThrow(() -> new ApiException("target expense not found!"));
            TargetItem dt = new TargetItem();
            dt.setPercent(item.getPercent());
            dt.setGenerateExpense(data);
            dt.setIsActivate(Boolean.TRUE);
            dt.setTargetExpense(targetExpense);
            existingItems.add(dt);
            targetItemRepository.save(dt);
        }

        generateExpenseRepository.save(data);
        return GenerateExpenseMapper.toDTO(data);
    }

    private Boolean isOver100(List<GenerateExpenseDataModel.TargetItem> targetItems) {
        if (targetItems == null) {
            return false;
        }
        double total = targetItems.stream()
                .filter(item -> item.getPercent() != null)
                .mapToDouble(GenerateExpenseDataModel.TargetItem::getPercent)
                .sum();
        return total > 100;
    }
}
