package org.beru.pizzeria.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
	private final JwtFilter jwtFilter;
	@Autowired
	public SecurityConfig(JwtFilter jwtFilter) {
		this.jwtFilter = jwtFilter;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
		.authorizeHttpRequests(customizeRequest -> {
			customizeRequest
			.requestMatchers("/auth/**").permitAll()
			.requestMatchers("/customers/**").hasAnyRole("ADMIN","CUSTOMER")
			.requestMatchers(HttpMethod.GET, "/pizzas/**").hasAnyRole("ADMIN","CUSTOMER")
			.requestMatchers(HttpMethod.POST, "/pizzas/**").hasRole("ADMIN")
			.requestMatchers(HttpMethod.PUT, "/pizzas/**").hasRole("ADMIN")
			.requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
			.requestMatchers("/orders/random").hasAuthority("random_order")
			.requestMatchers("/orders/**").hasRole("ADMIN")
			.anyRequest()
			.authenticated();
		})
		.csrf(AbstractHttpConfigurer::disable)
		.cors(Customizer.withDefaults())
		.sessionManagement(ses -> ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
