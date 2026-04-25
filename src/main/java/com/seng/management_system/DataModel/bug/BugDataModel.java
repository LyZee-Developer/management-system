package com.seng.management_system.DataModel.bug;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BugDataModel {
    private Long id;
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    @NotEmpty
    private List<Long> developers;
    private List<BugCommentDataModel> comment;

    @Setter
    @Getter
    public static class BugCommentDataModel {

        @NotBlank(message = "Comment is required")
        private String comment;

        private Long developerId;
    }
}

