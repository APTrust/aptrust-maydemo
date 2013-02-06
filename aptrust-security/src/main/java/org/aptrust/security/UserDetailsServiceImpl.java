package org.aptrust.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService{
    private Map<String,AptrustUser> users = new HashMap<String,AptrustUser>();
    
    public UserDetailsServiceImpl(List<AptrustUser> users) {
        for(AptrustUser u : users){
            this.users.put(u.getUsername(), u);
        }
    }

    
    @Override
public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException {
        AptrustUser user = this.users.get(username);
        if(user == null){
            throw new UsernameNotFoundException(username + " not found.");
        }
        
        return user;
}



}
