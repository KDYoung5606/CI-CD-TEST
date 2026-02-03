package umc.study.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import umc.study.domain.enums.Role;
import umc.study.validation.anotation.ExistCategories;

import java.util.*;

public class MemberRequestDTO {
    @Getter
    @Setter
    public static class JoinDTO{
        @NotBlank
        String name;
        @NotNull
        Integer gender;
        @NotNull
        Integer birthYear;
        @NotNull
        Integer birthMonth;
        @NotNull
        Integer birthDay;
        @Email
        String email;
        @NotBlank
        String password;
        @NotNull
        Role role;


        @Size(min = 5, max = 12)
        String address;
        @Size(min = 5, max = 12)
        String specAddress;
        @NotNull
        Integer age;
        @ExistCategories
        List<Long> preferCategory;

    }
}

