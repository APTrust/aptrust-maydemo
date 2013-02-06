package org.aptrust.security;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;

import org.aptrust.security.domain.Role;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AptrustPermissionEvaluatorTest {
    private AptrustPermissionEvaluator pe;
    private AptrustUser user;
    private Authentication auth;
    
    @Before
    public void setUp() throws Exception {
        user = EasyMock.createMock(AptrustUser.class);
        auth = EasyMock.createMock(Authentication.class);

        pe = new AptrustPermissionEvaluator();
    }
    
    @After
    public void tearDown(){
        EasyMock.verify(user, auth);
    }
    
    @Test
    public void testHasPermissionAuthenticationObjectObject() {
        try{
            replay();
            pe.hasPermission(null, null, null);
            fail();
        }catch(UnsupportedOperationException ex){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testRoot() {
        setupAuthorization(Role.ROLE_ROOT);
        replay();
        Assert.assertTrue(pe.hasPermission(auth, "testId", "institutionId", "read"));
    }
    

    @Test
    public void testAnonymous() {
        setupAuthorization(Role.ROLE_ANONYMOUS);
        replay();
        Assert.assertFalse(pe.hasPermission(auth, "testId", "institutionId", "read"));
    }

    @Test
    public void testUserSuccess() {
        setupAuthorization(Role.ROLE_USER);
        EasyMock.expect(user.getInstitutionIds())
                .andReturn(Arrays.asList(new String[] { "testId" }));
        replay();
        Assert.assertTrue(pe.hasPermission(auth, "testId", "institutionId", "read"));
    }

    @Test
    public void testUserFailure() {
        setupAuthorization(Role.ROLE_USER);
        EasyMock.expect(user.getInstitutionIds())
                .andReturn(Arrays.asList(new String[] { "nonMatchingId" }));
        replay();
        Assert.assertFalse(pe.hasPermission(auth, "testId", "institutionId", "read"));
    }

    
    protected void setupAuthorization(Role role) {
        user.getAuthorities();
        EasyMock.expectLastCall().andReturn(Arrays.asList(new GrantedAuthority[]{role.authority()}));
        EasyMock.expect(auth.getPrincipal()).andReturn(user);
    }
    
    public void replay(){
        EasyMock.replay(user, auth);
    }
}
