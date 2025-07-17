package com.example.TaskTracker.Service;
import com.example.TaskTracker.Entity.Task;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.jsonwebtoken.Jwts.claims;

@Service
public class JwtService {
    private String secretKey=null;
    public String generateToken(Task task)
    {
        Map<String,Object> claims=new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(task.getEmail())
                .issuer("DCB")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+10*60*1000))
                .and()
                .signWith(generateKey())
                .compact();

    }
    private SecretKey generateKey()
    {
        byte[] keybyte=Decoders.BASE64.decode(getSecretKey());
        return Keys.hmacShaKeyFor(keybyte);
    }
    public String getSecretKey()
    {
        return "J37aUcYINH3RRD/TDIVBCkMk+UBYz39D0NlnDKkpc18=";
    }

    public String extractusername(String token) {
        return extractClaims(token, Claims::getSubject);
    }
    private <T> T extractClaims(String token, Function<Claims,T> claimResolver)
    {
        Claims claims=extractClaims(token);
        return claimResolver.apply(claims);
    }
    private Claims extractClaims(String token)
    {
        return Jwts.parser().verifyWith(generateKey()).build().parseSignedClaims(token).getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userdetails) {
        final String username=extractusername(token);
        System.out.println("Username in token: " + username);
        System.out.println("Is expired: " + isTokenExpired(token));
        System.out.println("Username in userDetails: " + userdetails.getUsername());
        return (username.equals(userdetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token)
    {
        return extractClaims(token,Claims::getExpiration);
    }
}