package com.adrian.mobsters.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Document
@Data
@Builder
public class Mobster {
    @Id
    private final String id;
    /**
     * The mobster account name.
     */
    @NotBlank
    private final String username;
    @NotBlank
    private final String password;
    /**
     * The authorized user this mobster belongs to.
     */
    private final String user;
    @ApiModelProperty(notes = "Priority for running this account.", example = "0, 1, 2")
    @Builder.Default
    private final int priority = 1;
    @ApiModelProperty(notes = "Shows the status of the action jobs. Goes from Running -> Complete -> Queued -> Idle.",
            example = "complete")
    @Builder.Default
    private String actionJobStatus;
}