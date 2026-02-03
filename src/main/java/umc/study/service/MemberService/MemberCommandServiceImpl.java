package umc.study.service.MemberService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import umc.study.apiPayLoad.code.status.ErrorStatus;
import umc.study.apiPayLoad.exception.handler.FoodCategoryHandler;
import umc.study.converter.MemberConverter;
import umc.study.converter.MemberPreferConverter;
import umc.study.domain.FoodCategory;
import umc.study.domain.Member;
import umc.study.domain.mapping.MemberPrefer;
import umc.study.repository.FoodCategoryRepository.FoodCategoryRepository;
import umc.study.repository.MemberRepository.MemberRepository;
import umc.study.web.dto.MemberRequestDTO;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService{

    private final MemberRepository memberRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Member joinMember(MemberRequestDTO.JoinDTO request) {

        // 회원가입 로직에서 비밀번호를 암호화하여 저장하기 위해 PasswordEncoder를 사용합니다.
        /*
            @. passwordEncoder를 사용하는 이유

            1. 보안강화 : 평문비밀번호를 데이터베이스에 저장하지 않아 보안을 강화합니다.
            2. 단방향 해시 : BCryptPasswordEncoder는 단방향 해시 함수를 사용하여 원본 비밀번호를 복원할 수 없게 만듭니다
            3. 솔트(Salt)사용 : BCrypt는 자동으로 솔트를 생성하여 레인보우 테이블 공격을 방지합니다.


         */
        Member newMember = MemberConverter.toMember(request);
        newMember.encodePassWord(passwordEncoder.encode(request.getPassword()));


        List<FoodCategory> foodCategoryList = request.getPreferCategory()
                .stream()
                .map(category->{return foodCategoryRepository.findById(category)
                        .orElseThrow(()->new FoodCategoryHandler(ErrorStatus._FOOD_CATEGORY_NOT_FOUND));})
                .collect(Collectors.toList());

        List<MemberPrefer> memberPreferList = MemberPreferConverter.toMemberPreferList(foodCategoryList);
        memberPreferList.forEach(memberPrefer -> {memberPrefer.setMember(newMember);});
        return memberRepository.save(newMember);
    }



}
