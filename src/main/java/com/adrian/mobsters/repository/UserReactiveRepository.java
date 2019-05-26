package com.adrian.mobsters.repository;

import com.adrian.mobsters.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserReactiveRepository extends MongoRepository<User, String> {

}
