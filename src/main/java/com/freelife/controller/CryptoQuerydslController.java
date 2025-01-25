package com.freelife.controller;

import com.freelife.domain.RequestMemberDto;
import com.freelife.domain.ResponseMemberDto;
import com.freelife.service.CryptoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/querydsl")
@Tag(name = "Crypto Example Querydsl API", description = "Querydsl(Converter) 암복호화 예제 API")
public class CryptoQuerydslController {

    private final CryptoService cryptoService;

    public CryptoQuerydslController(@Qualifier("cryptoQuerydslService") CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @Operation(summary = "Querydsl 회원 추가", description = "# Querydsl 회원 추가 설명\n" +
        "- `password` 는 `Converter` 를 통해 암호화되서 저장되고 조회시 복호화되어 반환됨\n" +
        "- `encPassword` 를 통해 암호화된 `password` 를 확인할 수 있음"
    )
    @PostMapping("/member")
    public ResponseEntity<ResponseMemberDto> saveMember(@RequestBody RequestMemberDto requestMemberDto) {
        return ResponseEntity.ok(cryptoService.save(requestMemberDto.toEntity()));
    }

    @Operation(summary = "Querydsl 회원 조회", description = "# Querydsl 회원 조회 설명\n" +
        "- `password` 는 `Converter` 를 통해 조회시 복호화되어 반환됨\n" +
        "- `encPassword` 를 통해 암호화된 `password` 를 확인할 수 있음"
    )
    @GetMapping("/member")
    public ResponseEntity<List<ResponseMemberDto>> selectMember() {
        return ResponseEntity.ok(cryptoService.findMembers());
    }

    @Operation(summary = "Querydsl 회원 수정", description = "# Querydsl 회원 수정 설명\n" +
        "-`password` 는 `Converter` 를 통해 암호화되서 저장되고 조회시 복호화되어 반환됨\n" +
        "- `encPassword` 를 통해 암호화된 password 를 확인할 수 있음"
    )
    @PutMapping("/member/{id}")
    public ResponseEntity<List<ResponseMemberDto>> updateMember(
            @Parameter(description = "회원 ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(cryptoService.updateMember(id));
    }

    @Operation(summary = "Querydsl 회원 삭제")
    @DeleteMapping("/member/{id}")
    public ResponseEntity<List<ResponseMemberDto>> deleteMember(
            @Parameter(description = "회원 ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(cryptoService.deleteMember(id));
    }
}
