package com.adrian.mobsters.domain;

import lombok.Data;

import java.util.List;

@Data
public class DailyActionWrapper {
    private List<DailyAction> dailyActions;
}
