package umc.study.converter;

import umc.study.domain.Member;
import umc.study.domain.enums.Gender;
import umc.study.web.dto.MemberRequestDTO;
import umc.study.web.dto.MemberResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static umc.study.domain.enums.Gender.*;

public class MemberConverter {



    public static MemberResponseDTO.JoinResultDTO toJoinResultDTO(Member member){
        return MemberResponseDTO.JoinResultDTO.builder()
                .memberId(member.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Member toMember(MemberRequestDTO.JoinDTO request){
        Gender gender = null;

        switch (request.getGender()){
            case 1 :
                gender = MALE;
                break;
            case 2 :
                gender = FEMALE;
                break;
            case 3 :
                gender = NONE;
        }

        return Member.builder()
                .address(request.getAddress())
                .gender(gender)
                .specAddress(request.getSpecAddress())
                .name(request.getName())
                .memberPreferList(new ArrayList<>())
                .age(request.getAge())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .build();
    }
}
