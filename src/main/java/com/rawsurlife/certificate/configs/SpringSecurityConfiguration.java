package com.rawsurlife.certificate.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
	CustomUserDetailsService userDetailsService;
	
	@Autowired
	private CustomJwtAuthenticationFilter customJwtAuthenticationFilter;
	
	@Autowired
  	private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.cors().and()
		.authorizeRequests()
		.antMatchers("/api/v1/currency/**").authenticated()
		.antMatchers("/api/v1/customer/**").authenticated()
		.antMatchers("/api/v1/package/**").authenticated()
		.antMatchers("/api/v1/policy/**").authenticated()
		.antMatchers("/api/v1/subscriber/**").authenticated()
		//.anyRequest().authenticated()
		//if any exception occurs call this
		.and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //Add a filter to validate the tokens with every request
		.and().addFilterBefore(customJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
	}


	
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		web.ignoring()
		.antMatchers( HttpMethod.POST , "/authenticate")
		.antMatchers(HttpMethod.GET, "/api/v1/report/policy/**");
	}


	
	

}
