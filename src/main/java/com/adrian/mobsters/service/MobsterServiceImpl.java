package com.adrian.mobsters.service;

import com.adrian.mobsters.domain.ActionJob;
import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.domain.StatusConstants;
import com.adrian.mobsters.exception.MobsterNotFoundException;
import com.adrian.mobsters.repository.ActionJobRepository;
import com.adrian.mobsters.repository.MobsterRepository;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MobsterServiceImpl implements MobsterService {
    private final MobsterRepository mobsterRepository;
    private final ActionJobRepository actionJobRepository;

    @Override
    public String retrieveMobsterPassword(String username) {
        Optional<Mobster> mobster = mobsterRepository.findByUsername(username);

        if (!mobster.isPresent()) {
            throw new MobsterNotFoundException(username);
        }

        return mobster.get().getPassword();
    }

    @Override
    public Page<Mobster> getMobsters(String user, Pageable pageable, String status) {
        List<Mobster> mobstersPage = mobsterRepository.findAllByUser(user);
        setActionJobStatus(mobstersPage);

        String statuses = String.join("|", status.split(","));

        return mobsterRepository.findAllByUserAndActionJobStatusRegex(user, pageable, statuses);
    }

    private void setActionJobStatus(List<Mobster> mobstersList) {
        List<ActionJob> jobList = actionJobRepository.findByMobster_IdIn(mobstersList.stream()
                .map(Mobster::getId)
                .collect(Collectors.toList()));

        for (Mobster mobster : mobstersList) {
            mobster.setActionJobStatus(getActionJobStatus(jobList
                    .stream()
                    .filter(job -> job.getMobster().getId().equals(mobster.getId()))
                    .collect(Collectors.toList())));
        }
    }

    private String getActionJobStatus(List<ActionJob> actionJobs) {
        if (actionJobs == null || actionJobs.isEmpty()) {
            return "";
        }

        List<String> statuses = actionJobs.stream().map(ActionJob::getStatus).collect(Collectors.toList());

        if (statuses.contains(StatusConstants.RUNNING)) {
            return StatusConstants.RUNNING;
        } else if (statuses.contains(StatusConstants.QUEUED)) {
            return StatusConstants.QUEUED;
        } else if (statuses.contains(StatusConstants.IDLE)) {
            return StatusConstants.IDLE;
        }

        return statuses.stream().findFirst().orElse("");
    }

    @Override
    public List<Mobster> createMobsters(List<Mobster> mobster) {
        return Lists.newArrayList(mobsterRepository.saveAll(mobster));
    }
}
