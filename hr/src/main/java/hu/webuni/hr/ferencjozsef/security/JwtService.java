package hu.webuni.hr.ferencjozsef.security;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import hu.webuni.hr.ferencjozsef.config.HrConfigProperties;


@Service
public class JwtService {
	
	@Autowired
	HrConfigProperties hrConfigProperties;

	private static final String AUTH = "auth";
	//private String secret = hrConfigProperties.getJwt().getSecret();
	//private Algorithm algorithm = hrConfigProperties.getJwt().getAlgorithm();
	private Algorithm alg = Algorithm.HMAC256("hrsecret");
	private String issuer = hrConfigProperties.getJwt().getIssuer();
	//private String issuer = "HrApp";
	//private int expiresAt = hrConfigProperties.getJwt().getExpires();
	private int expiresAt = 10;
	
	public String createJwtToken(UserDetails principal) {
		
		return JWT.create()
				.withSubject(principal.getUsername())
				.withArrayClaim(AUTH, principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
				.withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(expiresAt)))
				.withIssuer(issuer)
				.sign(alg);
	}
	
	public UserDetails parseJwt(String jwtToken) {
		
		DecodedJWT decodedJwt = JWT.require(alg)
			.withIssuer(issuer)
			.build()
			.verify(jwtToken);
		return new User(decodedJwt.getSubject(), "dummy", 
				decodedJwt.getClaim(AUTH).asList(String.class)
				.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
				);	
	}

}
