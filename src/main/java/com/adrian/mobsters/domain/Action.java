package com.adrian.mobsters.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import static com.adrian.mobsters.domain.StatusConstants.*;

@Data
@Builder
public class Action {
    @NonNull
    private final String name;
    private final int sequence;
    @NonNull
    @Builder.Default
    private String status = IDLE;

    @JsonIgnore
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

    @JsonIgnore
    public boolean isFrozen() {
        return this.status.equals(FROZEN);
    }

    @JsonIgnore
    public boolean isComplete() {
        return this.status.equals(COMPLETE);
    }

    @JsonIgnore
    public boolean isRunning() {
        return this.status.equals(RUNNING);
    }
}