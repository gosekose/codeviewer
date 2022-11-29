package codeview.main.auth.application;

import codeview.main.auth.application.certification.SelfCertification;
import codeview.main.businessservice.member.application.MemberService;
import codeview.main.businessservice.member.infra.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService extends AbstractOAuth2UserService implements UserDetailsService {
    public CustomUserDetailsService(MemberService memberService, MemberRepository memberRepository, SelfCertification certification) {
        super(memberService, memberRepository, certification);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        return null;
    }
}

