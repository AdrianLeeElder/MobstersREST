package com.adrian.mobsters.repository;

import com.adrian.mobsters.domain.Proxy;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProxyRepository extends MongoRepository<Proxy, String> {
    Proxy findFirstByInUse(boolean inUse);

    Proxy findByHost(String host);
}
