package com.msrk.es.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SpringInMemUserDetailsService implements  UserDetailsService{

	@Value("${com.msrk.er.auth.user:test}")
	private String user;
	@Value("${com.msrk.er.auth.pass:test}")
	private String password;
	@Value("${com.msrk.er.auth.role:test}")
	private String role;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new User(user, password,
				AuthorityUtils.createAuthorityList(role));
	}

}
