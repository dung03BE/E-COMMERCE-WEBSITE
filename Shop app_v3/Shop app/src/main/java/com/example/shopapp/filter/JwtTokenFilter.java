package com.example.shopapp.filter;


import com.example.shopapp.utils.JwtTokenUtil;
import com.example.shopapp.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(@NonNull  HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try
        {
            if(isBypassToken(request))
            {
                filterChain.doFilter(request,response);
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            if(authHeader == null || !authHeader.startsWith("Bearer "))
            {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
                return;
            }
            final String token = authHeader.substring(7);
            final String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
            if(phoneNumber!=null && SecurityContextHolder.getContext().getAuthentication()==null)
            {
                User userDetails = (User) userDetailsService.loadUserByUsername(phoneNumber);
                if(jwtTokenUtil.validateToken(token,userDetails))
                {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request,response);
        }
        catch(Exception e)
        {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized");
        }

    }

    private boolean isBypassToken(@NonNull  HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of("api/v1/roles","GET"),
                Pair.of("api/v1/products","GET"),
                Pair.of("api/v1/categories","GET"),
                Pair.of("api/v1/categories/clear-cache","GET"),
                Pair.of("api/v1/users/login","POST"),
                Pair.of("api/v1/users/register","POST"),
                Pair.of("api/v1/actuator", "GET")
        );
        for(Pair<String, String> bypassToken: bypassTokens) {
            if (request.getServletPath().contains(bypassToken.getFirst()) &&
                    request.getMethod().equals(bypassToken.getSecond())) {
                return true;
            }
        }
//        String requestPath = request.getServletPath();
//        String requestMethod = request.getMethod();
//        for(Pair<String, String> bypassToken: bypassTokens) {
//            String tokenPath = bypassToken.getFirst();
//            String tokenMethod = bypassToken.getSecond();
//            if (tokenPath.contains("**")) {
//                String regexPath = tokenPath.replace("**", ".*");
//                Pattern pattern = Pattern.compile(regexPath);
//                Matcher matcher = pattern.matcher(requestPath);
//                if (matcher.matches() && requestMethod.equals(tokenMethod)) {
//                    return true;
//                }
//            } else if (requestPath.equals(tokenPath) && requestMethod.equals(tokenMethod)) {
//                return true;
//            }
//        }

        return false;
    }
}
