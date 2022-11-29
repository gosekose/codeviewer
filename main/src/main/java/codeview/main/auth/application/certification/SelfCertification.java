package codeview.main.auth.application.certification;

import codeview.main.auth.domain.users.social.ProviderUser;
import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.member.infra.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SelfCertification {

    private final MemberRepository memberRepository;
    public void checkCertification(ProviderUser providerUser) {

        Member member = memberRepository.findByEmail(providerUser.getEmail());
        boolean bool = providerUser.getProvider().equals("none") || providerUser.getProvider().equals("naver");
        providerUser.isCertificated(bool);
//        }
    }

    public void certificate(ProviderUser providerUser) {

    }
}
