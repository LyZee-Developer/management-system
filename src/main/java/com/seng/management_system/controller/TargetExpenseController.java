package com.seng.management_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seng.management_system.DataModel.generateExpense.targetExpense.TargetExpenseDataModel;
import com.seng.management_system.DataModel.generateExpense.targetExpense.TargetExpenseFilterDataModel;
import com.seng.management_system.apiResponse.ApiResponse;
import com.seng.management_system.constant.ApiPath;
import com.seng.management_system.exception.ApiException;
import com.seng.management_system.model.generateExpense.TargetExpense;
import com.seng.management_system.repository.generateExpense.TargetExpenseRepository;
import com.seng.management_system.service.TargetExpenseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiPath.TARGET_EXPENSE)
public class TargetExpenseController {
    
    @Autowired
    TargetExpenseRepository targetExpenseRepository;

    @Autowired
    TargetExpenseService targetExpenseService;

    @GetMapping
    public ResponseEntity<Object> list(TargetExpenseFilterDataModel filter){
        List<String> result = List.of("123","33");
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody TargetExpenseDataModel model){
        return ResponseEntity.ok(ApiResponse.success(targetExpenseService.create(model)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id,@Valid @RequestBody TargetExpenseDataModel model){
        return ResponseEntity.ok(ApiResponse.success(targetExpenseService.update(id, model)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> softDelete(@PathVariable Long id){
        TargetExpense delete = targetExpenseRepository.findById(id).orElseThrow(()->new ApiException("target expense not found!"));
        delete.setIsActivate(Boolean.FALSE);
        targetExpenseRepository.save(delete);
        return ResponseEntity.ok(ApiResponse.success("delete target expense success!"));
    }

}
