package xwsagent.wroomagent.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import xwsagent.wroomagent.jwt.JwtAuthenticationEntryPoint;
import xwsagent.wroomagent.jwt.JwtAuthenticationFilter;
import xwsagent.wroomagent.jwt.SecurityEvaluationContextExtension;
import xwsagent.wroomagent.service.DomainUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true
	)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private DomainUserDetailsService domainUserDetailsService;

	@Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	@Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension(){
        return new SecurityEvaluationContextExtension();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
            .userDetailsService(domainUserDetailsService)
            .passwordEncoder(passwordEncoder());
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
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http    	
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		
		.authorizeRequests()

		.antMatchers("/api/auth/**").permitAll()
		.antMatchers("/api/stub/**").permitAll()
		.antMatchers("/api/user/**").permitAll()
		
		.anyRequest().authenticated().and()
		
		.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    	
    	http.csrf().disable();
    	
    }
	

    //csrf dodat zbog bezbednosti, ali kad se front upakuje u .jar ne igra nikakvu ulogu
//   @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .cors().and()
//                .csrf()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .httpBasic().disable()
//                
//                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
//
//                .authorizeRequests().antMatchers("/api/auth/**").permitAll().and()
//                .authorizeRequests().antMatchers("/api/user/**").permitAll()
////                .authorizeRequests().antMatchers("/api/stub/**").permitAll()
//
//                .anyRequest().authenticated().and()
//                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService),
//						BasicAuthenticationFilter.class);
//        ;
//
//    }
}
