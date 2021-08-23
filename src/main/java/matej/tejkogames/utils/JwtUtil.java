package matej.tejkogames.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import matej.tejkogames.models.general.UserDetailsImpl;

@Component
public class JwtUtil {

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

	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (Exception exception) {	
			return false;
		} 
	}

}