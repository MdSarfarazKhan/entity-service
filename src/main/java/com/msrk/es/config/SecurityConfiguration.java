package com.msrk.es.config;

/**
 * @author sarfaraz
 *
 */

import com.msrk.es.service.SpringInMemUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;



@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = "com.msrk.es")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService);
	}
	
	@Override
	  protected void configure(HttpSecurity http) throws Exception {
	    http.httpBasic().and().authorizeRequests()
	    .antMatchers("/web/**","/static/**","resources/static/**","/webjars/**","/**").permitAll()
	    .anyRequest().authenticated().and().
	    csrf().disable();
	
	  }

}
