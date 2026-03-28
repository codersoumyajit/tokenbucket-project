package com.example.tokenbucket.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisBucketService {

    private static final int CAPACITY = 5;
    private static final long REFILL_TIME = 60000; // 1 minute

    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean allowRequest(String userId) {

        String key = "bucket:" + userId;

        String tokens = redisTemplate.opsForValue().get(key);

        if (tokens == null) {
            redisTemplate.opsForValue().set(key, String.valueOf(CAPACITY), Duration.ofMillis(REFILL_TIME));
            tokens = String.valueOf(CAPACITY);
        }

        int currentTokens = Integer.parseInt(tokens);

        if (currentTokens > 0) {
            redisTemplate.opsForValue().decrement(key);
            return true;
        }

        return false;
    }
    // ---------- BLACKLIST ----------

public boolean isBlacklisted(String userId) {
    return Boolean.TRUE.equals(
        redisTemplate.opsForSet().isMember("blacklist_users", userId)
    );
}

public void addToBlacklist(String userId) {
    redisTemplate.opsForSet().add("blacklist_users", userId);
}

public void removeFromBlacklist(String userId) {
    redisTemplate.opsForSet().remove("blacklist_users", userId);
}


// ---------- TEMPORARY BAN ----------

public void banUser(String userId, long minutes) {
    redisTemplate.opsForValue()
        .set("ban:" + userId, "1", Duration.ofMinutes(minutes));
}

public boolean isBanned(String userId) {
    return redisTemplate.hasKey("ban:" + userId);
}

public void unbanUser(String userId) {
    redisTemplate.delete("ban:" + userId);
}
}
