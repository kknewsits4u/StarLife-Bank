package com.starLife.in.starLifeConfig.CustomerConfiguration;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.starLife.in.Entity.Customer;

public class CustomerInfoDetails implements UserDetails {
	
	private Customer cstmr;
	
	private String name;
	private String password;
	private List<GrantedAuthority> authorities;
	
	public CustomerInfoDetails(Customer cstmr) {
		this.cstmr=cstmr;
	}

  @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+ cstmr.getRole()));
    }



	@Override
	public String getPassword() {
		return cstmr.getCpassword();
	}

	@Override
	public String getUsername() {
	
		return cstmr.getUserId();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}
	

}
