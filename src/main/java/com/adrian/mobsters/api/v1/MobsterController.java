package com.adrian.mobsters.api.v1;

import com.adrian.mobsters.domain.Mobster;
import com.adrian.mobsters.exception.MobsterNotFoundException;
import com.adrian.mobsters.repository.MobsterRepository;
import com.adrian.mobsters.service.MobsterService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/mobsters")
@AllArgsConstructor
public class MobsterController {
    private static final int RESULTS_PER_PAGE = 10;
    private MobsterRepository mobsterRepository;
    private final MobsterService mobsterService;

    @GetMapping("/all")
    public List<Mobster> getAllMobsters(Principal principal) {
        return mobsterRepository.findAllByUser(principal.getName());
    }

    @GetMapping
    public List<Mobster> getMobsters(int pageNumber, Principal principal) {
        Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "username"));

        return mobsterService.getMobsters(principal.getName(),
                new PageRequest(pageNumber, RESULTS_PER_PAGE, sort));
    }

    @GetMapping("/total-pages")
    public double getTotalPages(Principal principal) {
        List<Mobster> mobsters = mobsterRepository.findAllByUser(principal.getName());
        if (mobsters == null || mobsters.isEmpty()) {
            return 0;
        }

        return Math.ceil(mobsters.size() / (double) RESULTS_PER_PAGE);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Mobster saveMobster(@RequestBody Mobster mobster) {
        return mobsterRepository.save(mobster);
    }

    @GetMapping("{username}")
    public Mobster getMobsterByUserName(@PathVariable String username, Principal principal) {
        return mobsterRepository.findByUsernameAndUser(username, principal.getName());
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteMobster(@PathVariable String id, Principal principal) {
        Optional<Mobster> mobsterOptional = mobsterRepository.findAllByIdAndUser(id, principal.getName());
        if (!mobsterOptional.isPresent()) {
            throw new MobsterNotFoundException(id);
        }
        mobsterRepository.deleteById(id);
    }
}
