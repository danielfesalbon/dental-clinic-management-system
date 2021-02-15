/**
 * 
 */
package com.rest.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rest.app.config.AuthEntrypoint;
import com.rest.app.config.AuthRepository;
import com.rest.app.config.RequestFilterUtil;

/**
 * @author danielf
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthEntrypoint authEntrypoint;
	@Autowired
	private AuthRepository authService;
	@Autowired
	private RequestFilterUtil reqFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// exposed to public
		// http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();

		http.cors().and().csrf().disable().authorizeRequests().antMatchers("/authenticate/user").permitAll()
				.antMatchers("/user/reset/password").permitAll().antMatchers("/user/validate/reset").permitAll()
				.antMatchers("/file/receipt").permitAll().antMatchers("/file/generate").permitAll()
				.antMatchers("/user/save").permitAll().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(authEntrypoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.addFilterBefore(reqFilter, UsernamePasswordAuthenticationFilter.class);

	}

}