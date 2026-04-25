package com.seng.management_system.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seng.management_system.DataModel.developer.DeveloperDataModel;
import com.seng.management_system.DataModel.developer.DeveloperFilterDataModel;
import com.seng.management_system.apiResponse.ApiResponse;
import com.seng.management_system.constant.ApiPath;
import com.seng.management_system.service.DeveloperService;



@RestController
@RequestMapping(ApiPath.DEVELOPER)
public class DeveloperController {
    
    @Autowired
    private DeveloperService developerService;

    @GetMapping
    public ResponseEntity<Object> list(DeveloperFilterDataModel filter, @PageableDefault(page=0, size=10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseEntity.ok(ApiResponse.success(this.developerService.list(filter,pageable)));
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Validated DeveloperDataModel model){
        return ResponseEntity.ok(ApiResponse.success(this.developerService.create(model)));
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody @Validated DeveloperDataModel model){
        return ResponseEntity.ok(ApiResponse.success(this.developerService.update(model)));
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<Object> delete(@PathVariable(name = "Id") Long id){
        return ResponseEntity.ok(ApiResponse.success(this.developerService.delete(id)));
    }
   
}
