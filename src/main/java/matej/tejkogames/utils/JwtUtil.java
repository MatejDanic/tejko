package matej.tejkogames.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import matej.tejkogames.api.services.ExceptionLogService;
import matej.tejkogames.models.ExceptionLog;
import matej.tejkogames.models.UserDetailsImpl;

@Component
public class JwtUtil {

	@Autowired
	ExceptionLogService exceptionLogService;

	@Value("${matej.tejkogames.jwtSecret}")
	private String jwtSecret;

	@Value("${matej.tejkogames.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				// .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.setExpiration(null)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUsernameFromHeader(String headerAuth) {
        if (headerAuth.startsWith("Bearer ")) {
			String token = headerAuth.substring(7, headerAuth.length());
			if (token != null && validateJwtToken(token)) {
				return getUsernameFromJwtToken(token);
			} 
		}
		return null;
	}

	public String getUsernameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	
	// public String getUsernameFromToken(String token) {
	// 	String username = "";
	// 	if (token != null && token != "" || validateJwtToken(token)) {
	// 		username = getUsernameFromJwtToken(token);
	// 	}
    //     return username;
    // }

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException exception) {
			exceptionLogService.save(new ExceptionLog("Invalid JWT signature: " + exception.getMessage()));
		} catch (MalformedJwtException exception) {
			exceptionLogService.save(new ExceptionLog("Invalid JWT token: " + exception.getMessage()));
		} catch (ExpiredJwtException exception) {
			exceptionLogService.save(new ExceptionLog("JWT token is expired: " + exception.getMessage()));
		} catch (UnsupportedJwtException exception) {			
			exceptionLogService.save(new ExceptionLog("JWT token is unsupported: " + exception.getMessage()));
		} catch (IllegalArgumentException exception) {
			exceptionLogService.save(new ExceptionLog("JWT claims string is empty: " + exception.getMessage()));
		}
		return false;
	}

	
}