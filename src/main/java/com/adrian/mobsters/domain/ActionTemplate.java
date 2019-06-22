package com.adrian.mobsters.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Describes a group of {@link Action}s, and the order that they should be
 * executed. This is different from an {@link ActionJob} because it is not an
 * actual job, but a job description.
 */
@Data
@Document
@Builder(toBuilder = true)
@ApiModel(description = "A Configuration for groups of actions and their sequence. This is used to define ActionJobs.")
public class ActionTemplate {
    @Id
    @ApiModelProperty(hidden = true)
    private String id;
    @ApiModelProperty(notes = "A list of actions for this template")
    private final List<ActionTemplateAction> actions;
    @ApiModelProperty(notes = "The name of this template.", example = "Daily Actions")
    private final String name;
    @ApiModelProperty(notes = "The user that this template belongs to.", example = "bob@yahoo.com", hidden = true)
    private final String user;
    @ApiModelProperty(notes = "Frequency to execute this action template",
            example = "Daily",
            allowableValues = "Daily, Weekly, Monthly")
    private final String frequency;
    @ApiModelProperty(notes = "When this template was last ran.")
    private LocalDateTime lastRan;
    @ApiModelProperty(notes = "The mobsters to assign the action jobs to.")
    private List<Mobster> mobsters;
}
