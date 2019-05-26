package com.adrian.mobsters.domain;

import lombok.Data;

import java.util.List;

@Data
public class MobsterWrapper {
    private final List<Mobster> mobsters;
}
