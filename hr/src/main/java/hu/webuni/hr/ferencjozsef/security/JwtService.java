package hu.webuni.hr.ferencjozsef.security;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import hu.webuni.hr.ferencjozsef.config.HrConfigProperties;
import hu.webuni.hr.ferencjozsef.model.Employee;


@Service
public class JwtService {
	
	@Autowired
	HrConfigProperties hrConfigProperties;

	private static final String AUTH = "auth";
	private static final String ID = "id";
	private static final String EMPLOYEE_NAME = "employeeName";
	private static final String USERNAME = "USERNAME";
	private static final String BOSS = "boss";
	private static final String BOSS_EMPLOYEE_IDS = "bossEmployeeIds";
	private static final String BOSS_EMPLOYEE_NAMES = "bossEmployeeNames";
	private String secret;
	private Algorithm alg;
	private String issuer;
	private int expiresAt;
	
	@PostConstruct
	public void loadPropertiesSecurity() throws SecurityException {
		secret = hrConfigProperties.getJwt().getSecret();
		issuer = hrConfigProperties.getJwt().getIssuer();
		expiresAt = hrConfigProperties.getJwt().getExpires();
		//alg = Algorithm.HMAC256("hrsecret");
		try {
			alg = (Algorithm) Algorithm.class.getMethod(hrConfigProperties.getJwt().getAlgorithm(), String.class)
				.invoke(Algorithm.class, secret);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public String createJwtToken(UserDetails principal) {
		Employee employee = ((HrUser) principal).getEmployee();
		Employee boss = employee.getBoss();
		List<Employee> bossEmployees = employee.getBossEmployees();

		Builder jwtBuilder = JWT.create()
								.withSubject(principal.getUsername())
								.withArrayClaim(AUTH, principal.getAuthorities().stream()
										.map(GrantedAuthority::getAuthority).toArray(String[]::new))
								.withClaim(EMPLOYEE_NAME, employee.getName())
								.withClaim(ID, employee.getId());

		if(boss != null) {
			jwtBuilder
				.withClaim(BOSS, Map.of(
									  ID, boss.getId(),
									  USERNAME, boss.getName()
									));
		}
		
		if(bossEmployees != null && !bossEmployees.isEmpty()) {
			jwtBuilder
				.withArrayClaim(BOSS_EMPLOYEE_IDS, 
						bossEmployees.stream().map(Employee::getId).toArray(Long[]::new)
						)
				.withArrayClaim(BOSS_EMPLOYEE_NAMES,
						bossEmployees.stream().map(Employee::getUsername).toArray(String[]::new)
						);
		}
						
		return jwtBuilder
				.withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(expiresAt)))
				.withIssuer(issuer)
				.sign(alg);
	}
	
	public UserDetails parseJwt(String jwtToken) {
		
		DecodedJWT decodedJwt = JWT.require(alg)
			.withIssuer(issuer)
			.build()
			.verify(jwtToken);
		
		Employee employee = new Employee();
		
		employee.setId(decodedJwt.getClaim(ID).asLong());
		employee.setUsername(decodedJwt.getSubject());
		employee.setName(decodedJwt.getClaim(EMPLOYEE_NAME).asString());
		
		Claim bossClaim = decodedJwt.getClaim(BOSS);
		if (bossClaim != null) {
			Employee boss = new Employee();
			boss.setBoss(boss);
			Map<String, Object> bossData = bossClaim.asMap();
			if (bossData != null) {
				boss.setId(((Integer) bossData.get(ID)).longValue());
				boss.setUsername((String) bossData.get(USERNAME));
			}
		}
		
		Claim bossEmployeeUserNamesClaim = decodedJwt.getClaim(BOSS_EMPLOYEE_NAMES);
		if (bossEmployeeUserNamesClaim != null) {
			employee.setBossEmployees(new ArrayList<>());
			List<String> bossEmployeeUserNames = bossEmployeeUserNamesClaim.asList(String.class);
			if (bossEmployeeUserNames != null && bossEmployeeUserNames.isEmpty()) {
				List<Long> bossEmployeeIds = decodedJwt.getClaim(BOSS_EMPLOYEE_IDS).asList(Long.class);
				for (int i = 0; i < bossEmployeeUserNames.size(); i++) {
					Employee bossEmployee = new Employee();
					bossEmployee.setId(bossEmployeeIds.get(i));
					bossEmployee.setUsername(bossEmployeeUserNames.get(i));
					employee.getBossEmployees().add(bossEmployee);
				}
			}
			
		}
		
		
		return new HrUser(
				decodedJwt.getSubject(), 
				"dummy", 
				decodedJwt.getClaim(AUTH).asList(String.class).stream()
					.map(SimpleGrantedAuthority::new).collect(Collectors.toList()),
				employee
				);

	}

}
