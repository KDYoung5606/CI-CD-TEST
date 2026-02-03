package umc.study.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import umc.study.domain.Member;
import umc.study.domain.enums.Gender;
import umc.study.domain.enums.Role;
import umc.study.repository.MemberRepository.MemberRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*

    ★. CustomOAuth2UserService 클래스는 카카오 로그인 후 받은 사용자 정보를 처리하고 데이터 베이스에 저장하는 역할

    주요기능 설명

        1. OAuth2User 정보 로드 : super.loadUser(userRequest)를 통해 카카오에서 제공하는 사용자 정보를 OAuth2User 객체로
                                 받아 옵니다.

                                 이 객체는 attributes에는 사용자의 닉네임 등 기본 정보가 포함되어 잇습니다.

        2. 사용자 정보 추출 : 카카오 API에서 제공하는 사용자 정보 중 nickName을 추출합니다.
                            이메일 정보를 받기 위한 따로 설정을 하지 않았기 때문에 닉네임을 이용해 임시 이메일 주소를 생성


        3. 사용자 정보 저장 및 업데이트 :  saveOrUpdateUser 메서드를 통해 사용자 정보를 데이터베이스에 저장하거나 업데이트 합니다.
                                       임시 이메일을 기준으로 기존 사용자를 찾거나 새 사용자를 생성합니다.
                                       카카오에서 제공하지 않는 정보(성별, 주소 등)은 기본값으로 설정합니다,

        4. Spring Security 용 OAuth2User 반환 : DefaultOAuth2User 객체를 생성하여 반환합니다.
                                                사용자의 권한, 속성, 그리고 주요 식별자를 설정합니다.
                                                이를 통해 spring security가 인증된 사용자를 올바르게 식병하고 처리할 수 있습니다.



 */

/*
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        String nickName = (String) properties.get("nickname");
        String email = nickName + "@kakao.com"; // 임시 이메일 생성

        // 사용자 정보 저장 또는 업데이트
        Member member = saveOrUpdateUser(email, nickName);

        //이메일을 Principal로 사용하기 위해 attributes 수정
        Map<String, Object> modifiedAttributes = new HashMap<>(attributes);
        modifiedAttributes.put("email", email);

        return new DefaultOAuth2User(oAuth2User.getAuthorities(), modifiedAttributes, "email");
    }

    private Member saveOrUpdateUser(String email, String nickName) {
        Member member = memberRepository.findByEmail(email).orElse(Member
                .builder()
                        .email(email)
                        .name(nickName)
                        .password(passwordEncoder.encode("OAUTH_USER_" + UUID.randomUUID()))
                        .gender(Gender.NONE)
                        .address("소셜 로그인")
                        .specAddress("소셜 로그인")
                        .role(Role.USER)
                .build());
        return memberRepository.save(member);
    }
}


 */

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        String nickname = (String) properties.get("nickname");
        String email = nickname + "@kakao.com"; // 임시 이메일 생성

        // 사용자 정보 저장 또는 업데이트
        Member member = saveOrUpdateUser(email, nickname);

        // 이메일을 Principal로 사용하기 위해 attributes 수정
        Map<String, Object> modifiedAttributes = new HashMap<>(attributes);
        modifiedAttributes.put("email", email);

        return new DefaultOAuth2User(
                oAuth2User.getAuthorities(),
                modifiedAttributes,
                "email"  // email Principal로 설정
        );
    }

    private Member saveOrUpdateUser(String email, String nickname) {
        Member member = memberRepository.findByEmail(email)
                .orElse(Member.builder()
                        .email(email)
                        .name(nickname)
                        .password(passwordEncoder.encode("OAUTH_USER_" + UUID.randomUUID()))
                        .gender(Gender.NONE)  // 기본값 설정
                        .address("소셜로그인")  // 기본값 설정
                        .specAddress("소셜로그인")  // 기본값 설정
                        .role(Role.USER)
                        .build());

        return memberRepository.save(member);
    }
}