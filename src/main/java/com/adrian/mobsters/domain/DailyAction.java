package com.adrian.mobsters.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "dailyActions")
@NoArgsConstructor
public class DailyAction implements Comparable{

    @Id
    private String id;
    private String name;
    private int sequence;

    public DailyAction(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null) {
            return 1;
        }

        DailyAction otherDailyAction = (DailyAction) o;
        if (sequence > otherDailyAction.getSequence()) {
            return 1;
        } else if (sequence < otherDailyAction.getSequence()) {
            return - 1;
        }

        return 0;
    }
}
