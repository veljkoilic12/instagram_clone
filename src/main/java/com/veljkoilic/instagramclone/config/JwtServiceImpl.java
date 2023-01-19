package com.veljkoilic.instagramclone.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.function.Function;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceImpl implements JwtService {

	@Value("${secret.key}")
	private String SECRET_KEY;

	// Extract only a username field from the token that is passed to it.
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// Extract a single claim.
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	// Generate token without any extra claims.
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	// Generate token with some extra claims we want to add.
	public String generateToken(Map<String, String> extraClaims, UserDetails userDetails) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 100))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256).compact();
	}

	// Validate is the token valid. Does it belong to the user.
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
	}

	// Return whether the token that we passed is expired. Because return type of
	// called method "extractExpiration" is date, we can
	// call before method on it.
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Extracts expiration date from a token and returns it as a Date data type.
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	// Extracting all claims (properties) from the token that is passed to it and
	// returning them.
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
	}

	// Generating Sign In key
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
