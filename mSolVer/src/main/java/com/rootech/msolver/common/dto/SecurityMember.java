package com.rootech.msolver.common.dto;

import org.springframework.security.core.userdetails.User;

public class SecurityMember extends User {

    private static final long serialVersionUID = 1L;
    
    private String ip;
    
    public SecurityMember(CustomUserDetails member) {
        super(member.getUsername(), member.getPassword(), member.getAuthorities());
    }
 
    public String getIp() {
        return ip;
    }
 
    public void setIp(String ip) {
        this.ip = ip;
    }

}
