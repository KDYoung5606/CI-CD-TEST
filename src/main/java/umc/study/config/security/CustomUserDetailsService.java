package umc.study.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import umc.study.domain.Member;
import umc.study.repository.MemberRepository.MemberRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
/*
    ★. 동작과정
    1. 사용자가 로그인 폼에서 이메일과 비밀번호를 입력하고 제출합니다.
    2. Spring Security가 CustomUserDetailsService의 localUserByUsername 메서드를 호충
    3. 이 메서드는 입력받은 이메일으로 데이터베이스에서 사용자를 조회합니다.
    4. 사용자가 존재하면 Spring Security의 User 객체로 변환하여 반환합니다.
    5. Spring Security는 반환된 User 객체의 정보를 사용하여 인증을 수행합니다.
 */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("해당 이메일을 가진 유저가 존재하지 않습니다."));
        return org.springframework.security.core.userdetails.User
                .withUsername(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().name())
                .build();
    }
}
