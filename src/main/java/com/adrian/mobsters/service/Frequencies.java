package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.ActionTemplate;
import lombok.experimental.UtilityClass;

import java.time.Duration;

/**
 * Describes {@link ActionTemplate} action job creation frequencies.
 */
@UtilityClass
public class Frequencies {
    public static final String DAILY = "Daily";
    public static final String WEEKLY = "Weekly";
    public static final String MONTHLY = "Monthly";

    /**
     * Converts a frequency to a {@link Duration} for easy comparison.
     *
     * @param frequency frequency string. e.g 'Daily'
     * @return duration in milliseconds
     */
    public static long toDuration(String frequency) {
        switch (frequency) {
            case DAILY:
                return Duration.ofDays(1).toMillis();
            case WEEKLY:
                return Duration.ofDays(7).toMillis();
            case MONTHLY:
                return Duration.ofDays(30).toMillis();
            default:
                throw new IllegalStateException("unknown frequency provided: " + frequency);
        }
    }
}
