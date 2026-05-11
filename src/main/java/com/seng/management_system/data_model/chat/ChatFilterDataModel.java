package com.seng.management_system.data_model.chat;

import com.seng.management_system.data_model.BaseFilterDataModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatFilterDataModel extends BaseFilterDataModel {
    private Long userId;
}
