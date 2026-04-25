package com.seng.management_system.dto;

import java.util.List;

import com.seng.management_system.model.BugComment;
import com.seng.management_system.model.Developer;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BugDto extends BaseDto{
    private Long id;
    private String title;
    private String description;
    private List<Developer> developer;
    private List<BugComment> comment;
}
