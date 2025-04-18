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

    @Operation(summary = "Crypto Session(Hotel) Hash", description = "## 문자열을 `Hotel CryptoSession` 으로 Hash\n" +
                "- 암호화된 **Hash Key**와 알고리즘으로 문자열을 Hash 처리")
    @PostMapping("/hotel/hash")
    public ResponseEntity<String> hashHotel(
            @Parameter(description = "Hash 문자열", example = "we are the champion", required = true)
            @RequestParam(value = "plaintext") String hash) {
        return ResponseEntity.ok(hotelBasicCryptoSession.encrypt_id(hash, 400));
    }

    /*
    @Operation(summary = "Crypto Session(Hotel) Hash", description = "## 문자열을 `Hotel CryptoSession` 으로 Hash\n" +
            "- Hash 알고리즘(`SHA256`)만을 이용해 Hash 처리")
    @PostMapping("/hotel/hash")
    public ResponseEntity<String> hashHotel(
            @Parameter(description = "Hash 문자열", example = "we are the champion", required = true)
            @RequestParam(value = "plaintext") String plaintext) {
        return ResponseEntity.ok(hotelBasicCryptoSession.hash(plaintext));
    }

    @Operation(summary = "Crypto Session(Hotel) Algorithm Hash", description = "## 문자열을 `Hotel CryptoSession` 으로 Algorithm을 선택하여 Hash\n" +
            "- 입력된 Hash 알고리즘(`SHA256`, `SHA384`, `SHA512`, `SHA512_256`)을 이용해 Hash 처리")
    @PostMapping("/hotel/hash-algorithm")
    public ResponseEntity<String> hashAlgorithmHotel(
            @Parameter(description = "Hash 문자열", example = "we are the champion", required = true)
            @RequestParam(value = "plaintext") String plaintext,
            @Parameter(description = "Hash Algorithm (Allowed Algorithms: SHA256, SHA384, SHA512, SHA512_256)", example = "SHA512" , required = true)
            @RequestParam(value = "algorithm") String algorithm) {
        return ResponseEntity.ok(hotelBasicCryptoSession.hash(plaintext, algorithm));
    }

    @Operation(summary = "Crypto Session(Hotel) AlgorithmKey Hash", description = "## 문자열을 `Hotel CryptoSession` 으로 **Hash Key** 입력과 Algorithm을 선택하여 Hash\n" +
            "- **Hash Key**와 입력된 Hash 알고리즘(SHA256, SHA384, SHA512, SHA512_256)을 이용해 Hash 처리\n" +
            "- **Hash Key**가 외부에 노출되므로 **Hash Key**를 안전하게 관리해야 함\n" +
            "- 보안성을 강화하기 위해서는 **Encrypt Hash** 사용을 권장")
    @PostMapping("/hotel/hash-key-algorithm")
    public ResponseEntity<String> hashAlgorithmKeyHotel(
            @Parameter(description = "Hash 문자열", example = "we are the champion", required = true)
            @RequestParam(value = "plaintext") String plaintext,
            @Parameter(description = "Hash Key(Hex Encoded Data)", example = "2759e92adb361376ad8ef4610fbf5597" , required = true)
            @RequestParam(value = "hash_key") String hashKey,
            @Parameter(description = "Hash Algorithm (Allowed Algorithms: SHA256, SHA384, SHA512, SHA512_256)", example = "SHA512" , required = true)
            @RequestParam(value = "algorithm") String algorithm) {
        byte[] decodedHashKey;
        try {
            decodedHashKey = EncryptUtils.decodeHex(hashKey);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Hash Key is not Hex Encoded Data");
        }
        return ResponseEntity.ok(hotelBasicCryptoSession.hash(plaintext, algorithm, decodedHashKey));
    }
    */

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

    @Operation(summary = "Crypto Session(Air) Hash", description = "## 문자열을 `Air CryptoSession` 으로 Hash\n" +
                "- 암호화된 **Hash Key**와 알고리즘으로 문자열을 Hash 처리")
    @PostMapping("/air/hash")
    public ResponseEntity<String> hashAir(
            @Parameter(description = "Hash 문자열", example = "we are the champion", required = true)
            @RequestParam(value = "plaintext") String hash) {
        return ResponseEntity.ok(airBasicCryptoSession.encrypt_id(hash, 400));
    }
    
    /*
    @Operation(summary = "Crypto Session(Air) Hash", description = "## 문자열을 `Hotel CryptoSession` 으로 Hash\n" +
            "- Hash 알고리즘(`SHA256`)만을 이용해 Hash 처리")
    @PostMapping("/air/hash")
    public ResponseEntity<String> hashAir(
            @Parameter(description = "Hash 문자열", example = "we are the champion", required = true)
            @RequestParam(value = "plaintext") String plaintext) {
        return ResponseEntity.ok(airBasicCryptoSession.hash(plaintext));
    }

    @Operation(summary = "Crypto Session(Air) Algorithm Hash", description = "## 문자열을 `Hotel CryptoSession` 으로 Algorithm을 선택하여 Hash\n" +
            "- 입력된 Hash 알고리즘(`SHA256`, `SHA384`, `SHA512`, `SHA512_256`)을 이용해 Hash 처리")
    @PostMapping("/air/hash-algorithm")
    public ResponseEntity<String> hashAlgorithmAir(
            @Parameter(description = "Hash 문자열", example = "we are the champion", required = true)
            @RequestParam(value = "plaintext") String plaintext,
            @Parameter(description = "Hash Algorithm (Allowed Algorithms: SHA256, SHA384, SHA512, SHA512_256)", example = "SHA512" , required = true)
            @RequestParam(value = "algorithm") String algorithm) {
        return ResponseEntity.ok(airBasicCryptoSession.hash(plaintext, algorithm));
    }

    @Operation(summary = "Crypto Session(Air) AlgorithmKey Hash", description = "문자열을 `Hotel CryptoSession` 으로 **Hash Key** 입력과 Algorithm을 선택하여 Hash\n" +
            "- **Hash Key**와 입력된 Hash 알고리즘(`SHA256`, `SHA384`, `SHA512`, `SHA512_256`)을 이용해 Hash 처리\n" +
            "- **Hash Key**가 외부에 노출되므로 **Hash Key**를 안전하게 관리해야 함\n" +
            "- 보안성을 강화하기 위해서는 **Encrypt Hash** 사용을 권장")
    @PostMapping("/air/hash-key-algorithm")
    public ResponseEntity<String> hashAlgorithmKeyAir(
            @Parameter(description = "Hash 문자열", example = "we are the champion", required = true)
            @RequestParam(value = "plaintext") String plaintext,
            @Parameter(description = "Hash Key(Hex Encoded Data)", example = "2759e92adb361376ad8ef4610fbf5597" , required = true)
            @RequestParam(value = "hash_key") String hashKey,
            @Parameter(description = "Hash Algorithm (Allowed Algorithms: SHA256, SHA384, SHA512, SHA512_256)", example = "SHA512" , required = true)
            @RequestParam(value = "algorithm") String algorithm) {
        byte[] decodedHashKey;
        try {
            decodedHashKey = Hex.decodeHex(hashKey);
        } catch (DecoderException e) {
            return ResponseEntity.badRequest().body("Hash Key is not Hex Encoded Data");
        }
        return ResponseEntity.ok(airBasicCryptoSession.hash(plaintext, algorithm, decodedHashKey));
    }
    */
}
