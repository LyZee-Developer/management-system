package com.seng.management_system.data_model.developer;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeveloperDataModel {
    private Long id;
    private String nameEn;

    @NotBlank(message = "Name (KH) is required")
    private String nameKh;
    private Boolean isMale;

    private Boolean isActivate = Boolean.TRUE;

    @NotBlank(message = "Position is required")
    private String position;
}
