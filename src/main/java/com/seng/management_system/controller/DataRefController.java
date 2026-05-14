package com.seng.management_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seng.management_system.apiResponse.ApiResponse;
import com.seng.management_system.constant.RouteApi;
import com.seng.management_system.data_model.data_ref.DataRefDataModel;
import com.seng.management_system.data_model.data_ref.DataRefFilterDataModel;
import com.seng.management_system.service.DataRefService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping(RouteApi.DATA_REF)
public class DataRefController {

    @Autowired
    DataRefService dataService;

    @PostMapping
    public ResponseEntity<Object> list(@RequestBody DataRefFilterDataModel filter) {
        return ResponseEntity.ok(ApiResponse.success(dataService.list(filter)));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody DataRefDataModel model) {
        return ResponseEntity.ok(ApiResponse.success(dataService.create(model)));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable @Positive Long id, @Valid @RequestBody DataRefDataModel model) {
        return ResponseEntity.ok(ApiResponse.success(dataService.update(id, model)));
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(ApiResponse.success(dataService.delete(id)));
    }
}
