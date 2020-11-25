package fr.insee.pogues.config.auth.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import fr.insee.pogues.config.auth.security.conditions.NoOpenIDConnectAuthCondition;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = false)
@Conditional(value = NoOpenIDConnectAuthCondition.class)
public class DefaultSecurityContext extends WebSecurityConfigurerAdapter {

	@Value("${fr.insee.pogues.force.ssl}")
	Boolean requiresSSL;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests()
			.anyRequest().permitAll();
		if (requiresSSL) {
			http.antMatcher("/**").requiresChannel().anyRequest().requiresSecure().and().csrf().disable();;
		} else {
			http.csrf().disable();
		}
	}

}
