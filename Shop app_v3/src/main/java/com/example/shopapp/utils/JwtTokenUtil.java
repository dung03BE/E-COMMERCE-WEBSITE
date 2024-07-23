package com.example.shopapp.utils;

import com.example.shopapp.exception.InvalidParamException;
import com.example.shopapp.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    private final long expiration = 10*24*60*60*1000L;
    private final String secretKey = "odWMeCbWHf0wWLr5G/hNH+7qNzFz9fpxJIiU5n00JY4=";
    public String generateToken(User user) throws InvalidParamException {
        Map<String,Object> claims = new HashMap<>();
 //       this.generateSecretKey();
        claims.put("phoneNumber",user.getPhoneNumber());
        try
        {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis()+expiration))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        }
        catch(Exception e)
        {
           throw new InvalidParamException("Cannot create JwtToken,Error "+e.getMessage()) ;

        }
    }
    private Key getSignInKey()
    {
        byte[] bytes= Decoders.BASE64.decode(secretKey); //
        // Keys.hmacShaKeyFor(Decoders.BASE64.decode("odWMeCbWHf0wWLr5G/hNH+7qNzFz9fpxJIiU5n00JY4="))
        return Keys.hmacShaKeyFor(bytes);
    }
    private String generateSecretKey()
    {
        SecureRandom ramdom = new SecureRandom();
        byte[] keyBytes=new byte[32];
        ramdom.nextBytes(keyBytes);
        String secretKey = Encoders.BASE64.encode(keyBytes);
        return secretKey;
    }
    public Claims extractAllClaims(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver)
    {
        final  Claims claims=this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public boolean istokenExpired(String token)
    {
        Date expirationDate = this.extractClaim(token,Claims::getExpiration);
        return expirationDate.before(new Date());
    }
    public String extractPhoneNumber(String token)
    {
        return extractClaim(token,Claims::getSubject);
    }
    public boolean validateToken(String token, UserDetails userDetails)
    {
        String phoneNumber = extractPhoneNumber(token);
        return (phoneNumber.equals(userDetails.getUsername())) && !istokenExpired(token);
    }
}
