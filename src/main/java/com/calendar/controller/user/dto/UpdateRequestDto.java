package com.calendar.controller.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequestDto {

    private static final String PATTERN = "^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>])(?=.*\\d)(?=.*[a-zA-Z])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,}$";

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 10, message = "이름은 2자 이상 10자 이하로 입력해주세요.")
    private String name;

    @NotBlank
    @Size(min = 4, max = 20, message = "비밀번호는 4자 이상 20자 이하로 입력해주세요.")
    @Pattern(
            regexp = PATTERN,
            message = "비밀번호는 영대문자와 특수문자를 한 개 이상 포함하며, 영소문자와 숫자로만 구성 가능합니다."
    )
    private String newPassword;

    private String currentPassword;
}
