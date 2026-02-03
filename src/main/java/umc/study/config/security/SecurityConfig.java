package umc.study.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@EnableWebSecurity // --> Spring Security 설정을 활성화 시키는 역할 즉 우리가 직접 작성한 보안설정이 spring security 기본 설정보다 우선 적용하게 만든다.
@Component
public class SecurityConfig {
    @Bean // 이 메서드는 SecurityFilterChain를 정의한다. --> httpSecurity 객체를 통해 다양한 보안설정을 구성 할 수 있다.
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.   // authorizeHttpRequests()는 http 요청에 대한 접근제어를 설정한다.
                // requestMatchers()는 메서드를 사용하여 특정 URL 패턴에 대한 접근권한을 설정한다
                // permitALL() 메서드는 인증 없이 접근 가능한 경로를 지정합니다.
                // hasRole("ADMIN") : 권한이 ADMIN 역할을 가진 사용자만 접근하도록 제한합니다,
                // anyRequest().authenticated() : 그 외 모든 요청에 대해 인증을 요구.
                authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/admin", "/signup", "/members/signup", "/css/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()

                // form 기반의 로그인에 대한 설정
                        /*
                            - 커스텀 로그인 페이지를 /login 결로로 지정
                            - 로그인 성공시 /home으로 리다이렉트 합니다.
                            - 로그인 페이지는 모든 사용자가 접근 가능하도록 설정합니다.
                         */
                ).formLogin((form)->form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                // 로그아웃 처리에 대한 설정입니다,
                        /*
                            - /logout 경로로 로그아웃을 처리합니다.
                            - 로그아웃 성공시 /login?logout으로 리다이렉트합니다,
                        */
                ).logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                ).oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
                .permitAll()
        );
        return http.build();
    }


    // 비밀번호를 암호화하여 저장하기 위해서 BCryptPasswordEncoder를 사용합니다.
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
