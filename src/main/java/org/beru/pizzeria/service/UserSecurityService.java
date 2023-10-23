package org.beru.pizzeria.service;

import java.util.ArrayList;
import java.util.List;

import org.beru.pizzeria.persistence.entity.UserEntity;
import org.beru.pizzeria.persistence.entity.UserRoleEntity;
import org.beru.pizzeria.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService{
	private final UserRepository userRepository;

	@Autowired
	public UserSecurityService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserEntity userEntity = this.userRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
		String[] roles = userEntity.getRoles().stream().map(UserRoleEntity::getRole).toArray(String[]::new);
		return User
				.builder()
				.username(userEntity.getUsername())
				.password(userEntity.getPassword())
				.authorities(this.grantedAuthorities(roles))
				.accountLocked(userEntity.getLocked())
				.disabled(userEntity.getDisabled())
				.build()
				;
	}

	private String[] getAuthorities(String role) {
		if("ADMIN".equals(role) || "CUSTOMER".equals(role))
			return new String[] {"random_order"};

		return new String[] {};
	}

	private List<GrantedAuthority> grantedAuthorities (String[] roles){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(roles.length);

		for(String role : roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
			for(String authority : this.getAuthorities(role)) {
				authorities.add(new SimpleGrantedAuthority(authority));
			}
		}

		return authorities;
	}

}
