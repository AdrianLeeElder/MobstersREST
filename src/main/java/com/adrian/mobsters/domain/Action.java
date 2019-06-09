package com.adrian.mobsters.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

import static com.adrian.mobsters.domain.StatusConstants.*;

@Document
@Data
public class Action {

    @Id
    private String id;
    @NonNull
    private final String name;
    @NonNull
    private String status = "";

    public void setQueued() {
        this.status = QUEUED;
    }

    public void setRunning() {
        this.status = RUNNING;
    }

    public void setComplete() {
        this.status = COMPLETE;
    }

    public void setFrozen() {
        this.status = FROZEN;
    }

    public boolean isFrozen() {
        return this.status.equals(FROZEN);
    }

    public boolean isComplete() {
        return this.status.equals(COMPLETE);
    }

    public boolean isRunning() {
        return this.status.equals(RUNNING);
    }
}