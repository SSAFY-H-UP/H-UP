package com.a702.hup.domain.auth.redis;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;

@EnableRedisRepositories
public interface TokenRedisRepository extends CrudRepository<Token, String> {
}
