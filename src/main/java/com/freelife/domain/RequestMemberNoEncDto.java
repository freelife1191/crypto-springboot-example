package com.freelife.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by mskwon on 2024. 10. 24..
 */
@Data
@Schema(description = "회원 등록 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestMemberNoEncDto {
    @Schema(description = "이름", defaultValue = "홍길동", example = "홍길동")
    private String name;
    @Schema(description = "별칭", defaultValue = "hong", example = "hong")
    private String nickName;
    @Schema(description = "나이", defaultValue = "10", example = "10")
    private Integer age;
    @Schema(description = "비밀번호 (Converter or TypeHandler 를 적용하여 암복호화 처리)", defaultValue = "1111", example = "1111")
    private String password;

    public MemberNoEnc toEntity() {
        return MemberNoEnc.builder()
                .name(name)
                .nickName(nickName)
                .age(age)
                .password(password)
                .build();
    }
}
