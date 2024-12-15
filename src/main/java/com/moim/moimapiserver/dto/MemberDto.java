package com.moim.moimapiserver.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
public class MemberDto implements UserDetails {
    private int m_no;
    private String m_social_id;
    private String m_profile_img;
    private String m_id;
    private String m_pw;
    private String m_name;
    private String m_nickname;
    private String m_phone;
    private String m_gender;
    private String m_age;
    private String m_address;
    private String m_mail;
    private String m_category;
    private int m_grade;
    private int m_status;
    private String m_reg_date;
    private String m_mod_date;
    private String m_start_membership;
    private String m_end_membership;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return m_pw;
    }

    @Override
    public String getUsername() {
        return m_id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
