package com.example.article.secret;


import com.example.article.entity.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

//    @Value("${app.jwtSecret}")
//    private String secretKey;
//
//    @Value("${app.jwtExpirationInMs}")
//    private Long expireDate;
//
//    public String generateJwtToken(User user){
////        List<String> roles=user.getRoles().stream().map(role -> role.getRoleName()).collect(Collectors.toList());
////        return Jwts.builder()
////                .setSubject(user.getId().toString())
////                .setAudience(roles.toString())
////                .setIssuedAt(new Date())
////                .setExpiration(new Date(new Date().getTime()+expireDate))
////                .signWith(SignatureAlgorithm.HS512,secretKey)
////                .compact();
////
//        Date date = new Date();
//        long tokenExpireTime = 604800000L;
//        long l = date.getTime() + tokenExpireTime;
//        Date expireDate = new Date(l);
//
//        return Jwts
//                .builder()
//                .setSubject(user.getId().toString())
//                .setIssuedAt(date)
//                .claim("roles", user.getRoles())
//                .setExpiration(expireDate)
//                .signWith(SignatureAlgorithm.HS512, secretKey)
//                .compact();
//    }


    @Value("${app.jwtSecret}")
    private String secretKey;

    public String generateJwtToken(User user) {
        Date date = new Date();
        long tokenExpireTime = 604800000L;
        long l = date.getTime() + tokenExpireTime;
        Date expireDate = new Date(l);

        return Jwts
                .builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(date)
                .claim("roles", user.getRoles())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }


    public boolean validateJwtToken(String token){
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException e) {
            System.err.println("Muddati o'tgan");
        } catch (MalformedJwtException malformedJwtException) {
            System.err.println("Buzilgan token");
        } catch (UnsupportedJwtException unsupportedJwtException) {
            System.err.println("Qo'llanilmagan token");
        } catch (IllegalArgumentException ex) {
            System.err.println("Bo'sh token");
        }
        return false;
    }


    public String getUserIdFromToken(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


}
