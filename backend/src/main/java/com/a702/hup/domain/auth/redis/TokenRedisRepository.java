package com.a702.hup.domain.auth.redis;

import org.springframework.data.repository.CrudRepository;

interface TokenRedisRepository extends CrudRepository<Token, String> {
}
