package xwsagent.wroomagent.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import xwsagent.wroomagent.security.auth.RestAuthenticationEntryPoint;
import xwsagent.wroomagent.security.auth.TokenAuthenticationFilter;
import xwsagent.wroomagent.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService jwtUserDetailsService;
	
	@Autowired
	TokenUtils tokenUtils;
	
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	// Nacin autentifikacije
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

    //csrf dodat zbog bezbednosti, ali kad se front upakuje u .jar ne igra nikakvu ulogu
   @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic().disable()
                
//                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
//
//                .authorizeRequests().antMatchers("/api/auth/**").permitAll().and()
//                .authorizeRequests().antMatchers("/api/stub/**").permitAll()
//
//                .anyRequest().authenticated().and()
//                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService),
//						BasicAuthenticationFilter.class);
        ;

    }
}
