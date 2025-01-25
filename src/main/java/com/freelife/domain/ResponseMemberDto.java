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
@Schema(description = "회원 정보 조회 DTO")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMemberDto {
    @Schema(description = "ID", example = "1")
    private Long id;
    @Schema(description = "이름", example = "홍길동")
    private String name;
    @Schema(description = "별칭", example = "hong")
    private String nickName;
    @Schema(description = "나이", example = "10")
    private Integer age;
    @Schema(description = "비밀번호 (Converter를 적용하여 암복호화 처리)", example = "1111")
    private String password;
    @Schema(description = "암호화된 비밀번호 (복호화되지 않은 상태)", example = "c2b6d76200f0d6bb37fe68b1b5bb8515")
    private String encPassword;

    public static ResponseMemberDto fromEntity(Member member) {
        return ResponseMemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .nickName(member.getNickName())
                .age(member.getAge())
                .password(member.getPassword())
                .encPassword(member.getEncPassword())
                .build();
    }

    public ResponseMemberDto setEncPassword(String encPassword) {
        this.encPassword = encPassword;
        return this;
    }
}
