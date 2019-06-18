package com.adrian.mobsters.repository;

import com.adrian.mobsters.domain.Mobster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface MobsterRepository extends PagingAndSortingRepository<Mobster, String> {
    /**
     * Find all mobsters by the given mobster username, and for the given user.
     *
     * @param username
     * @param user
     * @return
     */
    Mobster findByUsernameAndUser(String username, String user);

    /**
     * Find all mobsters by the given mobster username regex, and for the given user.
     *
     * @param regex username regex to find mobsters by
     * @param user  the current user
     * @return
     */
    List<Mobster> findByUsernameRegexAndUser(String regex, String user);

    /**
     * Find a mobster by the given user and id.
     *
     * @param id   id of the mobster
     * @param user authenticated user
     * @return
     */
    Optional<Mobster> findAllByIdAndUser(String id, String user);

    Page<Mobster> findAllByUser(String user, Pageable pageable);

    /**
     * Find all mobsters by the given user
     *
     * @param name current authenticated user
     * @return
     */
    List<Mobster> findAllByUser(String name);

    Optional<Mobster> findByUsername(String username);
}
