package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.DailyAction;
import com.adrian.mobsters.domain.DailyActionWrapper;
import com.adrian.mobsters.repository.DailyActionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Interact with {@link DailyAction}s.
 */
@RestController
@RequestMapping("api/v1/daily-actions")
@RequiredArgsConstructor
public class DailyActionController {
    private final DailyActionRepository dailyActionRepository;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("add")
    public DailyAction newAction(@RequestBody DailyAction dailyAction) {
        return dailyActionRepository.save(dailyAction);
    }

    /**
     * Add a list of daily actions.
     *
     * @param dailyActionContainer wrapper object for supplying multiple daily actions.
     * @return a list of daily actions
     */
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("addlist")
    public List<DailyAction> addList(@RequestBody DailyActionWrapper dailyActionContainer) {
        return dailyActionRepository.saveAll(dailyActionContainer.getDailyActions());
    }
}
