package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.ActionTemplate;
import com.adrian.mobsters.domain.Mobster;

import java.util.List;

public interface ActionJobCreator {
    List<ActionJob> createFromTemplate(ActionTemplate actionTemplate, List<Mobster> mobsters, String user);
}
