package org.aptrust.security;

import java.io.Serializable;
import java.util.Collection;

import org.aptrust.security.domain.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;


public class AptrustPermissionEvaluator implements PermissionEvaluator{
    private Logger log = LoggerFactory.getLogger(AptrustPermissionEvaluator.class);
    @Override
    public boolean hasPermission(Authentication auth, Object targetDomainObject, Object permission) {
        throw new UnsupportedOperationException("Use other method");
    }

    @Override
    public boolean hasPermission(Authentication auth,
                                 Serializable targetId,
                                 String targetType,
                                 Object permission) {
        
        if(log.isDebugEnabled()) {
            log.debug("checking that {} has permission to {} on {} ({})",
                      new Object[] {
                          auth.getName(), permission, targetId, targetType });
        }
        
        AptrustUser user = (AptrustUser)auth.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        
        if(authorities.contains(Role.ROLE_ROOT.authority())){
            return true;
        }else if(authorities.contains(Role.ROLE_USER.authority())){
            if(targetType.equals("institutionId")){
                if(user.getInstitutionIds().contains(targetId)){
                    return true;
                }
            }
        } 
        
        return false;
    }

}
