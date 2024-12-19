package com.calendar.dto.request.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CurrentPasswordRequestDto {

    //현재 password (수정, 삭제용)
    private String currentPassword;
}
