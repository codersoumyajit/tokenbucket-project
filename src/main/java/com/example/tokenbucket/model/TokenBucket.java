package com.example.tokenbucket.model;

public class TokenBucket {

    private final long capacity;
    private final long refillRate;
    private long tokens;
    private long lastRefillTime;

    public TokenBucket(long capacity, long refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.tokens = capacity;
        this.lastRefillTime = System.currentTimeMillis();
    }

    public synchronized boolean tryConsume() {
        refill();

        if (tokens > 0) {
            tokens--;
            return true;
        }
        return false;
    }

    private void refill() {
        long now = System.currentTimeMillis();
        long secondsPassed = (now - lastRefillTime) / 1000;

        if (secondsPassed > 0) {
            long newTokens = secondsPassed * refillRate;
            tokens = Math.min(capacity, tokens + newTokens);
            lastRefillTime = now;
        }
    }
    public long getRemainingTokens() {
    return tokens;
}

public long getCapacity() {
    return capacity;
}
public long getNextRefillTime() {
    return lastRefillTime + 1000; // since refill rate is 1 per second
}
}