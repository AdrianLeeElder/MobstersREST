package com.adrian.mobsters.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.adrian.mobsters.domain.StatusConstants.*;

@Data
@Builder
public class ActionJobStatistics {
    @NonNull
    @Builder.Default
    private final List<ActionJob> actionJobList = Collections.emptyList();

    public long getTotalRunning() {
        return getCountForStatus(RUNNING);
    }

    public long getTotalCompleted() {
        return getCountForStatus(COMPLETE);
    }

    public long getTotalIdle() {
        return getCountForStatus(IDLE);
    }

    public long getTotalQueued() {
        return getCountForStatus(QUEUED);
    }

    public long getCompletionProgress() {
        int size = actionJobList.size();
        return (getTotalCompleted() * 100) / size;
    }

    public double getAverageCompletionTime() {
        return getActionsWithStatus(COMPLETE)
                .stream()
                .mapToDouble(ActionJob::getCompletionTime)
                .average().orElse(0);
    }

    private long getCountForStatus(String status) {
        return getActionsWithStatus(status).size();
    }

    private List<ActionJob> getActionsWithStatus(String status) {
        return actionJobList
                .stream()
                .filter(job -> job.getStatus().equals(status)).collect(Collectors.toList());
    }
}
