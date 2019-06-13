package com.adrian.mobsters.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

import static com.adrian.mobsters.domain.StatusConstants.*;

@Document(collection = "actionJobs")
@Data
@Builder
public class ActionJob {
    @Id
    private String id;
    private final Mobster mobster;
    private final int priority;
    private final List<Action> actionList;
    private final String user;
    private final LocalDateTime createdDate;
    private final ActionTemplate template;
    private LocalDateTime runStart;
    private long completionTime;
    private int failureCount;
    @Builder.Default
    private String status = IDLE;

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

    public boolean isIdle() {
        return this.status.equals(IDLE);
    }

    public boolean isRunning() {
        return this.status.equalsIgnoreCase(RUNNING);
    }
}
