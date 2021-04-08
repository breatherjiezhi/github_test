package com.dhc.rad.modules.sys.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {  
    @Override  
    public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {  
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;  
        String cardID= token.getCardID();
        if(!"".equals(cardID) || cardID!=null)
        {
        	return true;  
        }
        return false;  
        
    }  

}  
