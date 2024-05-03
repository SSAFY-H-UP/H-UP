package com.a702.hup.domain.auth;

import com.a702.hup.domain.auth.redis.Token;
import com.a702.hup.domain.auth.redis.TokenRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {
    private final TokenRedisRepository tokenRedisRepository;

    /**
     * @author 이경태
     * @date 2024-05-01
     * @description refresh token 저장
     **/
    public void save(String userId, String refreshToken) {
        tokenRedisRepository.save(Token.builder()
                .userId(userId)
                .refreshToken(refreshToken)
                .build());
    }
}
