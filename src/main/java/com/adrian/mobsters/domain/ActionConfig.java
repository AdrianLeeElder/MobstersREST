package com.adrian.mobsters.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Describe how an {@link Action} is executed and validated.
 */
@Data
@Document
public class ActionConfig {
    @Id
    private final String id;
    @ApiModelProperty(notes = "The action name.", example = "200 Energy Link")
    private final String name;
    @ApiModelProperty(notes = "Describe text that should be present on the page when the action finishes.",
            example = "You have collected your daily energy")
    private final List<String> finishStrings;
    @ApiModelProperty(notes = "XPath for the button to click.", example = "//button[@id='logout']")
    private final String xPath;
}
