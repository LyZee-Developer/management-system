package com.seng.management_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seng.management_system.DataModel.generateExpense.generateExpense.GenerateExpenseDataModel;
import com.seng.management_system.DataModel.generateExpense.generateExpense.GenerateExpenseFilterDataModel;
import com.seng.management_system.apiResponse.ApiResponse;
import com.seng.management_system.constant.ApiPath;
import com.seng.management_system.exception.ApiException;
import com.seng.management_system.model.generateExpense.GenerateExpense;
import com.seng.management_system.repository.generateExpense.GenerateExpenseRepository;
import com.seng.management_system.service.GenerateExpenseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiPath.GENERATE_EXPENSE)
public class GenerateExpenseController {
    
    @Autowired
    GenerateExpenseRepository generateExpenseRepository;

    @Autowired
    GenerateExpenseService generateExpenseService;

    @GetMapping
    public ResponseEntity<Object> list(@RequestBody GenerateExpenseFilterDataModel filter,@PageableDefault(page=0,size=10,direction=Sort.Direction.DESC,sort = "id") Pageable pageable){
        return ResponseEntity.ok(ApiResponse.success(generateExpenseService.list(filter,pageable)));
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody GenerateExpenseDataModel model){
        return ResponseEntity.ok(ApiResponse.success(generateExpenseService.create(model)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id,@Valid @RequestBody GenerateExpenseDataModel model){
        return ResponseEntity.ok(ApiResponse.success(generateExpenseService.update(id, model)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> softDelete(@PathVariable Long id){
        GenerateExpense delete = generateExpenseRepository.findById(id).orElseThrow(()->new ApiException("target expense not found!"));
        delete.setIsActivate(Boolean.FALSE);
        generateExpenseRepository.save(delete);
        return ResponseEntity.ok(ApiResponse.success(Boolean.TRUE));
    }

}
