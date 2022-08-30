package com.example.TesinaProjectBank.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.TesinaProjectBank.models.User;
import com.example.TesinaProjectBank.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenUtil {
	private String SECRET_KEY="secret";
	private long timeExpire=1000*60*30;
	@Autowired
	UserRepository userRepository;
	
	public String extractUsername(String token) {
		return extractClaim(token,Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	private <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
		final Claims claims= extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(token).getBody();
	}
	
	public String getKey() {
		return SECRET_KEY;
	}
	
	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public String generateToken(UserDetails userDetails) {
		User user=userRepository.findByUsername(userDetails.getUsername());
		Map<String, Object> claims = new HashMap<>();
		claims.put("authorities", userDetails.getAuthorities());
		claims.put("userdataid", user.getId());
		return createToken(claims, userDetails.getUsername());
	}
	
	private String createToken(Map<String , Object> claims, String subject) {
		return Jwts.builder().setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+timeExpire))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
				.compact();
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username= extractUsername(token);
		return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
	}
	public String refreshToken(Map<String , Object> claims, String subject) {
		return createToken(claims, subject);
	}
}
