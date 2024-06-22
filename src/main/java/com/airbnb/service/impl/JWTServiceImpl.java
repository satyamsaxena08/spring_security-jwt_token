package com.airbnb.service.impl;


import com.airbnb.entity.PropertyUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class JWTServiceImpl {
      @Value("${jwt.algorithm.Key}")
      private String algorithmKey;
      @Value("${jwt.issuer}")
      private String issuer;
      @Value("${jwt.expiryDuration}")
      private int expiryTime;

      private final static String USER_NAME= "username";

      private Algorithm algorithm;

    @PostConstruct
    public void postConstruct(){
        algorithm= Algorithm.HMAC256(algorithmKey);
    }

    public String generateToken(PropertyUser propertyUser){//5. it generating token

        return JWT.create().withClaim(USER_NAME,propertyUser.getUsername())//5.1 it generating token based on                .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))
                .withIssuer(issuer)
                .sign(algorithm);//6.token generate return back to controller
    }

    public String getUserName(String token){
        DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return decodedJWT.getClaim(USER_NAME).asString();
    }
}
