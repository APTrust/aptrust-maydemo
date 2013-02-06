package org.aptrust.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.aptrust.security.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 * @author Daniel Bernstein
 * 
 */
public class AptrustUser implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Collection<String> institutionIds;
    
    public AptrustUser(String username, String password, String role, String institutionId){
        this.username = username;
        this.password = password;
        this.authorities =
            Arrays.asList(new GrantedAuthority[] { Role.valueOf(role)
                                                       .authority() });
       this.institutionIds = new LinkedList<String>();
        this.institutionIds.add(institutionId);
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    
    public Collection<String> getInstitutionIds(){
        return this.institutionIds;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
