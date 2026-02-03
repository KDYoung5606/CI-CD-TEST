package umc.study.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import umc.study.service.MemberService.MemberCommandService;
import umc.study.web.dto.MemberRequestDTO;
import umc.study.web.dto.MemberResponseDTO;

@Controller
@RequiredArgsConstructor
public class MemberViewController {

    private final MemberCommandService memberCommandService;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/signup")
    public String signupPage(Model model){
        model.addAttribute("memberJoinDto", new MemberRequestDTO.JoinDTO());
        return "signup";
    }

    // 회원가입 폼 데이터를 처리하고 회원가입 API를 정의합니다. 회원가입 요청이 들어오면 memberCommandService를 사용해서
    // 회원가입 로직을 수행하며, 그 과정에서 발생할 수 있는 예외처리를 담당합니다.
    @PostMapping("members/signup")
    public String joinMember(@ModelAttribute("memberJoinDto") MemberRequestDTO.JoinDTO request
            , BindingResult bindingResult
            , Model model){
        // 뷰 데이터 바인딩이 실패할 경우 signup 페이지를 유지합니다.
        if(bindingResult.hasErrors()){
            return "signup";
        }
        try {
            memberCommandService.joinMember(request);
            return "redirect:/login";
        }catch (Exception e) {
            // 회원가입 과정에서 에러가 발생할 경우 에러메시지를 보내고 signup 페이지를 유지합니다.
            model.addAttribute("error", e.getMessage());
            return "signup";
        }
    }
}
