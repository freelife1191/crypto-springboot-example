package com.freelife.controller;

import com.freelife.domain.RequestMemberDto;
import com.freelife.domain.ResponseMemberDto;
import com.freelife.domain.ResponseMemberNoEncDto;
import com.freelife.service.CryptoPerformanceService;
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
@RequestMapping("/mybatis")
@Tag(name = "Crypto Example Mybatis API", description = "Mybatis(TypeHandler) 암복호화 예제 API")
public class CryptoMybatisController {

    private final CryptoPerformanceService cryptoService;

    public CryptoMybatisController(@Qualifier("cryptoMybatisService") CryptoPerformanceService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @Operation(summary = "Mybatis 회원 추가", description = "# Mybatis 회원 추가 설명\n" +
            "- `password` 는 `TypeHandler` 를 통해 암호화되서 저장되고 조회시 복호화되어 반환됨\n" +
            "- `encPassword` 를 통해 암호화된 `password` 를 확인할 수 있음"
    )
    @PostMapping("/member")
    public ResponseEntity<ResponseMemberDto> saveMember(@RequestBody RequestMemberDto requestMemberDto) {
        return ResponseEntity.ok(cryptoService.save(requestMemberDto.toEntity()));
    }

    @Operation(summary = "Mybatis Bulk 회원 추가", description = "# Mybatis Bulk 회원 추가 설명\n" +
            "- `password` 는 `TypeHandler` 를 통해 암호화되서 저장되고 조회시 복호화되어 반환됨\n" +
            "- `encPassword` 를 통해 암호화된 `password` 를 확인할 수 있음"
    )
    @PostMapping("/members")
    public ResponseEntity<Integer> saveAll(
            @Parameter(description = "회원 수", required = true, example = "100")
            @RequestParam(value = "count", defaultValue = "100") Integer count
    ) {
        return ResponseEntity.ok(cryptoService.saveAll(count));
    }

    @Operation(summary = "Mybatis Bulk 회원 추가 (암호화 X)", description = "# Mybatis Bulk 회원 추가 설명\n" +
            "- `password` 는 암호화 하지 않고 저장되고 조회시 복호화하지 않음\n" +
            "- `encPassword` 와 `password` 는 동일함"
    )
    @PostMapping("/members-no-enc")
    public ResponseEntity<Integer> saveAllNoEnc(
            @Parameter(description = "회원 수", required = true, example = "100")
            @RequestParam(value = "count", defaultValue = "100") Integer count
    ) {
        return ResponseEntity.ok(cryptoService.saveAllNoEnc(count));
    }

    @Operation(summary = "Mybatis 회원 조회", description = "# Mybatis 회원 조회 설명\n" +
            "- `password` 는 `TypeHandler` 를 통해 조회시 복호화되어 반환됨\n" +
            "- `encPassword` 를 통해 암호화된 `password` 를 확인할 수 있음"
    )
    @GetMapping("/member")
    public ResponseEntity<List<ResponseMemberDto>> selectMember(
            @Parameter(description = "회원 수", required = true, example = "100")
            @RequestParam(value = "count", defaultValue = "100") Integer count
    ) {
        return ResponseEntity.ok(cryptoService.findMembers(count));
    }

    @Operation(summary = "Mybatis 회원 조회 (복호화 X)", description = "# Mybatis 회원 조회 설명\n" +
            "- `password` 는 암호화 하지 않고 저장되고 조회시 복호화하지 않음\n" +
            "- `encPassword` 와 `password` 는 동일함"
    )
    @GetMapping("/member-no-enc")
    public ResponseEntity<List<ResponseMemberNoEncDto>> selectMemberNoEnc(
            @Parameter(description = "회원 수", required = true, example = "100")
            @RequestParam(value = "count", defaultValue = "100") Integer count
    ) {
        return ResponseEntity.ok(cryptoService.findMembersNoEnc(count));
    }

    @Operation(summary = "Mybatis 회원 수정", description = "# Mybatis 회원 수정 설명\n" +
            "- `password` 는 `TypeHandler` 를 통해 암호화되서 저장되고 조회시 복호화되어 반환됨\n" +
            "- `encPassword` 를 통해 암호화된 `password` 를 확인할 수 있음"
    )
    @PutMapping("/member/{id}")
    public ResponseEntity<List<ResponseMemberDto>> updateMember(
            @Parameter(description = "회원 ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(cryptoService.updateMember(id));
    }

    @Operation(summary = "Mybatis 회원 삭제")
    @DeleteMapping("/member/{id}")
    public ResponseEntity<List<ResponseMemberDto>> deleteMember(
            @Parameter(description = "회원 ID", required = true, example = "1")
            @PathVariable("id") Long id) {
        return ResponseEntity.ok(cryptoService.deleteMember(id));
    }

}
