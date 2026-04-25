package com.seng.management_system.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.seng.management_system.DataModel.bug.BugDataModel;
import com.seng.management_system.DataModel.bug.BugFilterDataModel;
import com.seng.management_system.constant.GlobalHelper;
import com.seng.management_system.dto.BugDto;
import com.seng.management_system.exception.ApiException;
import com.seng.management_system.mapper.BugMapper;
import com.seng.management_system.model.Bug;
import com.seng.management_system.model.BugComment;
import com.seng.management_system.model.Developer;
import com.seng.management_system.repository.BugCommentRepository;
import com.seng.management_system.repository.BugRepository;
import com.seng.management_system.repository.DeveloperRepository;
import com.seng.management_system.service.BugService;
import com.seng.management_system.specification.BugSpecification;

import jakarta.transaction.Transactional;

@Service
public class BugServiceImp implements BugService {
    @Autowired
    DeveloperRepository developerRepository;

    @Autowired
    BugRepository bugRepository;

    @Autowired
    BugCommentRepository  bugCommentRepository;

    @Override
    public Page<BugDto> list(BugFilterDataModel filter, Pageable pageable){
        Specification<Bug> spec = BugSpecification.build(filter);
        Page<Bug> pageData = bugRepository.findAll(spec, pageable);
        return pageData.map(BugMapper::MapToDto);
    }

    @Override
    @Transactional
    public BugDto create(BugDataModel model){
        Bug data = new Bug();
        List<Developer> developers = developerRepository.findAllById(model.getDevelopers());
        List<BugComment> comments = new ArrayList<>();
        data.setDevelopers(developers);
        data.setTitle(model.getTitle());
        data.setDescription(model.getDescription());

        data.setCreateBy(GlobalHelper.SYSTEM);
        data.setCreateDate(new Date());
        data.setIsActivate(Boolean.TRUE);
        bugRepository.save(data);
        if (!model.getComment().isEmpty()) {
            for(BugDataModel.BugCommentDataModel cm : model.getComment()){
                BugComment bc = new BugComment();
                Developer dev = developerRepository.findById(cm.getDeveloperId()).orElseThrow(()-> new ApiException("developer not found!"));
                bc.setComment(cm.getComment());
                bc.setDeveloper(dev);
                bc.setBug(data);
                comments.add(bc);
                bugCommentRepository.save(bc);
            }
        }
        data.setComments(comments);
        bugRepository.save(data);
        return BugMapper.MapToDto(data);

    }

    @Override
    public BugDto update(BugDataModel model){
        Optional.ofNullable(model.getId()).orElseThrow(()-> new ApiException("Id is required!"));
        Bug data = bugRepository.findById(model.getId()).orElseThrow(()-> new ApiException("Bug not found!"));
        List<Developer> devs = developerRepository.findAllById(model.getDevelopers());
        data.setDevelopers(devs);
        data.setDescription(model.getDescription());
        data.setTitle(model.getTitle());
        bugRepository.save(data);
        return BugMapper.MapToDto(data);
    }

    @Override
    public Boolean delete(Long Id){
        Long id = Optional.ofNullable(Id).orElse(0L);
        var data = bugRepository.findById(id).orElseThrow(() -> new ApiException("Bug not found!"));
        data.setIsActivate(Boolean.FALSE);
        bugRepository.save(data);
        return Boolean.TRUE;
    }

    @Override
    public Boolean comment(Long id,String comment,Long developerId){
         var data = bugRepository.findById(id).orElseThrow(() -> new ApiException("Bug not found!"));
         BugComment bc = new BugComment();
         Developer dev = developerRepository.findById(developerId).orElseThrow(() -> new ApiException("Developer not found!"));
         bc.setComment(comment);
         bc.setDeveloper(dev);
         bc.setBug(data);
         bugCommentRepository.save(bc);
         data.setComments(List.of(bc));
         bugRepository.save(data);
        return true;
    }
}
