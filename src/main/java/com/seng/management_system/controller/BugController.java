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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.seng.management_system.DataModel.bug.BugDataModel;
import com.seng.management_system.DataModel.bug.BugFilterDataModel;
import com.seng.management_system.apiResponse.ApiResponse;
import com.seng.management_system.constant.ApiPath;
import com.seng.management_system.service.BugService;

import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping(ApiPath.BUG)
public class BugController {
    
    @Autowired
    private BugService bugService;

    @GetMapping
    public ResponseEntity<Object> list(BugFilterDataModel filter, @PageableDefault(page=0, size=10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        return ResponseEntity.ok(ApiResponse.success(this.bugService.list(filter,pageable)));
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Validated BugDataModel model){
        return ResponseEntity.ok(ApiResponse.success(this.bugService.create(model)));
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody @Validated BugDataModel model){
        return ResponseEntity.ok(ApiResponse.success(this.bugService.update(model)));
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<Object> delete(@PathVariable(name = "Id") Long id){
        return ResponseEntity.ok(ApiResponse.success(this.bugService.delete(id)));
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<Object> comment(@PathVariable(name = "id") Long id, @RequestParam String comment,@Positive Long developerId){
        return ResponseEntity.ok(ApiResponse.success(this.bugService.comment(id,comment,developerId)));
    }
   
}
