package com.moim.moimapiserver.member;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MemberDetailService implements UserDetailsService {

    final private IMemberMapper iMemberMapper;

    public MemberDetailService(IMemberMapper iMemberMapper) {
        this.iMemberMapper = iMemberMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String m_id) throws UsernameNotFoundException {
        MemberDto selectMemberDto = iMemberMapper.selectMemberForLogin(m_id);
        log.info(selectMemberDto);

        if(selectMemberDto != null) {
            return selectMemberDto;
        }

        throw new UsernameNotFoundException("User not found with id: " + m_id);

    }

}

