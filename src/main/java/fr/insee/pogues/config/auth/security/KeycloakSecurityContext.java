package fr.insee.pogues.config.auth.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import fr.insee.pogues.config.auth.security.conditions.OpenIDConnectAuthCondition;
import fr.insee.pogues.config.auth.security.keycloak.PoguesKeycloakConfigResolver;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
@Conditional(value = OpenIDConnectAuthCondition.class)
public class KeycloakSecurityContext extends KeycloakWebSecurityConfigurerAdapter {
	
	final static Logger logger = LogManager.getLogger(KeycloakSecurityContext.class);
	
	@Value("${fr.insee.pogues.force.ssl}")
	Boolean requiresSSL;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
		http.csrf().disable();
		if (requiresSSL) http.antMatcher("/**").requiresChannel().anyRequest().requiresSecure();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.sessionAuthenticationStrategy(sessionAuthenticationStrategy())
			.and().authorizeRequests()
			.antMatchers("/pogues/auth/init").permitAll()
			.antMatchers("/swagger-ui/**").permitAll()
			.antMatchers("/pogues/swagger.json").permitAll()
			.anyRequest().authenticated();
	}
	
	/**
	 * Registers the KeycloakAuthenticationProvider with the authentication
	 * manager.
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) {
		KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
		keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
		auth.authenticationProvider(keycloakAuthenticationProvider);
	}

	/**
	 * Defines the keycloak configuration
	 */
    @Bean
	public KeycloakConfigResolver keycloakConfigResolver() {
		logger.info("adding Pogues keycloak config resolver");
        return new PoguesKeycloakConfigResolver();
	}

	/**
	 * Defines the session authentication strategy.
	 */
	@Bean
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new NullAuthenticatedSessionStrategy();
	}

}
