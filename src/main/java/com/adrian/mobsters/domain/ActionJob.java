package com.adrian.mobsters.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import static com.adrian.mobsters.domain.StatusConstants.*;

@Document(collection = "actionJobs")
@Data
@RequiredArgsConstructor
public class ActionJob {

    @Id
    private String id;
    private final Mobster mobster;
    private final List<Action> actionList;
    private final boolean daily;
    private final boolean buyProperty;
    private int failureCount;
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
}
