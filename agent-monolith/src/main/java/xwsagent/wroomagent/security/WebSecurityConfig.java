package xwsagent.wroomagent.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import xwsagent.wroomagent.jwt.JwtAuthenticationEntryPoint;
import xwsagent.wroomagent.jwt.JwtAuthenticationFilter;
import xwsagent.wroomagent.jwt.SecurityEvaluationContextExtension;
import xwsagent.wroomagent.service.DomainUserDetailsService;

@Configuration
@EnableWebMvc
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DomainUserDetailsService domainUserDetailsService;

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Value("${server.ssl.enabled}")
	private boolean httpsRequired;

	@Bean
	public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
		return new SecurityEvaluationContextExtension();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(domainUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Value("${server.ssl.enabled}")
	private boolean requireHttps;
	
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors().and()
				.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).disable()
				.httpBasic().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				.authorizeRequests()
				.antMatchers("/api/auth/**").permitAll()
				.antMatchers("/api/stub/**").permitAll()
				.antMatchers("/api/user/**").permitAll()

				.anyRequest().authenticated().and()

				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.headers().contentSecurityPolicy("script-src 'self' https://localhost:4200; " +
				"img-src 'self' https://localhost:4200;")
		;
		if(httpsRequired) {
			http.requiresChannel().anyRequest().requiresSecure();
		}
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		//Da bi serviranje iz static foldera dozvolio pristup početnoj strani bez potrebe za JWTom
		web.ignoring().antMatchers(HttpMethod.GET,
				"/",
				"/webjars/**",
				"/*.html",
				"/favicon.ico",
				"/**/*.html",
				"/**/*.css",
				"/**/*.js",
				"/**/*.png",
				"/**/*.jpg");
		web.ignoring().antMatchers(HttpMethod.POST, "api/auth/login");
		web.ignoring().antMatchers(HttpMethod.GET, "api/auth/signup");
		web.ignoring().antMatchers(HttpMethod.GET, "api/auth/whoami");
	}
}
