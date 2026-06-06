package com.cleantemplate.base.infrastructure.Security;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> buckets =
            new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String key =
                getKey(request)
                + "_"
                + request.getMethod()
                + "_"
                + request.getRequestURI();

        Bucket bucket =
                buckets.computeIfAbsent(
                        key,
                        k -> createBucket(request));

        if (bucket.tryConsume(1)) {

            response.addHeader(
                    "X-Rate-Limit-Remaining",
                    String.valueOf(bucket.getAvailableTokens()));

            filterChain.doFilter(request, response);

        } else {

            response.setStatus(429);
            response.setContentType("application/json");

            response.getWriter().write("""
                    {
                        "erro":"Muitas requisições"
                    }
                    """);
        }
    }

    private String getKey(HttpServletRequest request) {

        Authentication auth =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        if (auth != null
                && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {

            return "USER_" + auth.getName();
        }

        return "IP_" + request.getRemoteAddr();
    }

    private Bucket createBucket(HttpServletRequest request) {

        String uri = request.getRequestURI();

        Bandwidth limit;

        if (uri.startsWith("/login")) {

            limit = limit(5);

        } else if (uri.startsWith("/usuarios")) {

            limit = limit(20);

        } else if (uri.startsWith("/auth/refresh")) {

            limit = limit(2);

        } else {

            limit = limit(50);
        }

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    private Bandwidth limit(long requestsPerSecond) {

        return Bandwidth.builder()
                .capacity(requestsPerSecond)
                .refillGreedy(
                        requestsPerSecond,
                        Duration.ofSeconds(1))
                .build();
    }
}