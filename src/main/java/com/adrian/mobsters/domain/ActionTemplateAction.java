package com.adrian.mobsters.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@Data
@ApiModel(description = "A action configuration for an ActionTemplate.")
@Builder
public class ActionTemplateAction {
    @ApiModelProperty(notes = "The name of the Action, corresponding to the name of the ActionConfig.")
    private final String name;
    @ApiModelProperty(notes = "The sequence of this action in the template")
    private final int sequence;
}
