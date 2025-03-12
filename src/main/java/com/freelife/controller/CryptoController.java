package com.freelife.controller;

import com.freelife.crypto.core.CryptoSession;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/crypto")
@Tag(name = "Crypto Test API", description = "Crypto 암복호화 테스트 API")
@RequiredArgsConstructor
public class CryptoController {

    @Resource
    private final CryptoSession hotelBasicCryptoSession;
    @Resource
    private final CryptoSession airBasicCryptoSession;

    @Operation(summary = "Crypto Session(Hotel) 암호화", description = "문자열을 `Hotel CryptoSession` 으로 암호화")
    @PostMapping("/hotel/encrypt")
    public ResponseEntity<String> encryptHotel(
            @Parameter(description = "암호화할 문자열", example = "Hello Crypto!", required = true)
            @RequestParam(value = "encrypt") String encrypt) {
        return ResponseEntity.ok(hotelBasicCryptoSession.encrypt(encrypt));
    }

    @Operation(summary = "Crypto Session(Hotel) 복호화", description = "## Crypto Session(Hotel) 복호화 설명\n" +
            "- `Hotel CryptoSession` 으로 암호화된 문자열을 `Hotel CryptoSession` 으로 복호화\n" +
            "- `Hotel CryptoSession` 은 암호화된 문자열을 `Air CryptoSession` 으로 **복호화할 수 없음**"
    )
    @GetMapping("/hotel/decrypt")
    public ResponseEntity<String> decryptHotel(
            @Parameter(description = "복호화할 문자열", example = "oe/Pz3CJLWZ7+hdK2nP9tQ==", required = true)
            @RequestParam(value = "decrypt") String decrypt) {
        return ResponseEntity.ok(hotelBasicCryptoSession.decrypt(decrypt));
    }

    @Operation(summary = "Crypto Session(Hotel) Hash", description = "문자열을 `Hotel CryptoSession` 으로 Hash")
    @PostMapping("/hotel/hash")
    public ResponseEntity<String> hashHotel(
            @Parameter(description = "Hash 문자열", example = "we are the champion", required = true)
            @RequestParam(value = "hash") String hash) {
        return ResponseEntity.ok(hotelBasicCryptoSession.encrypt_id(hash, 400));
    }

    @Operation(summary = "Crypto Session Air 암호화", description = "문자열을 `Air CryptoSession` 으로 암호화")
    @PostMapping("/air/encrypt")
    public ResponseEntity<String> encryptAir(
            @Parameter(description = "암호화할 문자열", example = "Hello Crypto!", required = true)
            @RequestParam(value = "encrypt") String encrypt) {
        return ResponseEntity.ok(airBasicCryptoSession.encrypt(encrypt));
    }

    @Operation(summary = "Crypto Session Air 복호화", description = "## Crypto Session(Air) 복호화 설명\n" +
            "- `Air CryptoSession` 으로 암호화된 문자열을 `Air CryptoSession` 으로 복호화\n" +
            "- `Air CryptoSession` 은 암호화된 문자열을 `Hotel CryptoSession` 으로 **복호화할 수 없음**"
    )
    @GetMapping("/air/decrypt")
    public ResponseEntity<String> decrypt(
            @Parameter(description = "복호화할 문자열", example = "JOrVlbSeeMkCvCuvjzaCDQ==", required = true)
            @RequestParam(value = "decrypt") String decrypt) {
        return ResponseEntity.ok(airBasicCryptoSession.decrypt(decrypt));
    }

    @Operation(summary = "Crypto Session(Air) Hash", description = "문자열을 `Air CryptoSession` 으로 Hash")
    @PostMapping("/air/hash")
    public ResponseEntity<String> hashAir(
            @Parameter(description = "Hash 문자열", example = "we are the champion", required = true)
            @RequestParam(value = "hash") String hash) {
        return ResponseEntity.ok(airBasicCryptoSession.encrypt_id(hash, 400));
    }
}
