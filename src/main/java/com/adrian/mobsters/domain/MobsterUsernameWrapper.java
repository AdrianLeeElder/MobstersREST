package com.adrian.mobsters.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@ApiModel(description = "Wrapper class for a list of mobster login names.")
public class MobsterUsernameWrapper {
    @ApiModelProperty(notes = "A list of mobster login names.")
    @NonNull
    private final List<String> usernames;

    public String asRegex() {
        return String.join("|", usernames);
    }
}
