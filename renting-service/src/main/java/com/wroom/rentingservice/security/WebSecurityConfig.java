package com.wroom.rentingservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.wroom.rentingservice.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors().and()
				.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).disable()
				
				.httpBasic().disable()
				
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

//				.exceptionHandling().authenticationEntryPoint(this.unauthorizedHandler).and()

				.authorizeRequests()
				.antMatchers("/**").permitAll()
				.antMatchers("/hello/**").permitAll()

				.anyRequest().authenticated().and()
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

//				.headers()
//				.contentSecurityPolicy(
//						"script-src 'self' https://localhost:4200; " + "img-src 'self' https://localhost:4200;");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// Da bi serviranje iz static foldera dozvolio pristup početnoj strani bez
		// potrebe za JWTom
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
				"/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg");
//        web.ignoring().antMatchers(HttpMethod.POST, "/login");
//        web.ignoring().antMatchers(HttpMethod.GET, "/signup");
//        web.ignoring().antMatchers(HttpMethod.GET, "/whoami");
	}

}