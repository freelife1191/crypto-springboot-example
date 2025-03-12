package com.freelife.factory;

import com.freelife.crypto.core.CryptoException;
import com.freelife.crypto.core.CryptoSession;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * CryptoSessionFactory 를 사용한 CryptoSession 테스트
 * Created by mskwon on 2024. 10. 23..
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CryptoFactoryConfigTest {

    @Resource
    private CryptoSessionFactory<CryptoSessionType> cryptoSessionFactory;

    @Test
    void base_Initializing_cryptoSession() {
        String plaintext = "Hello Crypto!";
        CryptoSession hotelCryptoSession = cryptoSessionFactory.of(CryptoSessionType.HOTEL);
        CryptoSession airCryptoSession = cryptoSessionFactory.of(CryptoSessionType.AIR);

        String encryptHotel = hotelCryptoSession.encrypt(plaintext);
        String decryptHotel = hotelCryptoSession.decrypt(encryptHotel);
        String hashHotel = hotelCryptoSession.encrypt_id(plaintext, 400);
        String encryptAir = airCryptoSession.encrypt(plaintext);
        String decryptAir = airCryptoSession.decrypt(encryptAir);
        String hashAir = airCryptoSession.encrypt_id(plaintext, 400);
        assertAll(
                () -> assertNotNull(hotelCryptoSession),
                () -> assertNotNull(airCryptoSession),
                () -> assertEquals(decryptHotel, plaintext),
                () -> assertEquals(decryptAir, plaintext),
                () -> assertTrue(hashHotel.length() >= 88),
                () -> assertTrue(hashAir.length() >= 88)
        );
    }

    @Test
    void multi_Initializing_cryptoSession() {
        String plaintext = "Hello Crypto!";
        CryptoSession hotelCryptoSession = cryptoSessionFactory.of(CryptoSessionType.HOTEL);
        CryptoSession airCryptoSession = cryptoSessionFactory.of(CryptoSessionType.AIR);

        String encryptHotel = hotelCryptoSession.encrypt(plaintext);
        String decryptHotel = hotelCryptoSession.decrypt(encryptHotel);
        String hashHotel = hotelCryptoSession.encrypt_id(plaintext, 400);
        String encryptAir = airCryptoSession.encrypt(plaintext);
        String decryptAir = airCryptoSession.decrypt(encryptAir);
        String hashAir = airCryptoSession.encrypt_id(plaintext, 400);
        assertAll(
                () -> assertNotNull(hotelCryptoSession),
                () -> assertNotNull(airCryptoSession),
                () -> assertEquals(decryptHotel, plaintext),
                () -> assertEquals(decryptAir, plaintext),
                () -> assertTrue(hashHotel.length() >= 88),
                () -> assertTrue(hashAir.length() >= 88)
        );
    }

    @Test
    void cryptoSessionDuplicateCallTest() {
        String plaintext = "Hello Crypto!";
        for (int i = 0; i < 10; i++) {
            CryptoSession hotelCryptoSession = cryptoSessionFactory.of(CryptoSessionType.HOTEL);
            CryptoSession airCryptoSession = cryptoSessionFactory.of(CryptoSessionType.AIR);

            String encryptHotel = hotelCryptoSession.encrypt(plaintext);
            log.debug("CryptoSession[{}] encrypt: {}", i, encryptHotel);
            String decryptHotel = hotelCryptoSession.decrypt(encryptHotel);
            log.debug("CryptoSession[{}] decrypt: {}", i, decryptHotel);
            String hashHotel = hotelCryptoSession.encrypt_id(plaintext, 400);
            log.debug("CryptoSession[{}] hash: {}", i, hashHotel);
            String encryptAir = airCryptoSession.encrypt(plaintext);
            log.debug("CryptoSession[{}] encrypt: {}", i, encryptAir);
            String decryptAir = airCryptoSession.decrypt(encryptAir);
            log.debug("CryptoSession[{}] decrypt: {}", i, decryptAir);
            String hashAir = airCryptoSession.encrypt_id(plaintext, 400);
            log.debug("CryptoSession[{}] hash: {}", i, hashAir);
            assertAll(
                    () -> assertNotNull(hotelCryptoSession),
                    () -> assertNotNull(airCryptoSession),
                    () -> assertEquals(decryptHotel, plaintext),
                    () -> assertEquals(decryptAir, plaintext),
                    () -> assertTrue(hashHotel.length() >= 88),
                    () -> assertTrue(hashAir.length() >= 88)
            );
        }
    }

    @Test
    @DisplayName("다른 타입의 CryptoSession을 사용하여 암호화된 문자열을 복호화하면 CryptoException이 발생")
    void failed_decrypt_test() {
        String plaintext = "Hello Crypto!";
        String encrypt = cryptoSessionFactory.of(CryptoSessionType.HOTEL).encrypt(plaintext);
        assertThrows(CryptoException.class, () -> cryptoSessionFactory.of(CryptoSessionType.AIR).decrypt(encrypt));
    }

    @Test
    @DisplayName("Hash 암호화된 문자열은 복호화 되지 않음")
    void failed_decrypt_hash_test() {
        String plaintext = "Hello Crypto!";
        String encrypt = cryptoSessionFactory.of(CryptoSessionType.HOTEL).encrypt_id(plaintext, 400);
        System.out.println("encrypt: " + encrypt);
        String decrypt = cryptoSessionFactory.of(CryptoSessionType.HOTEL).decrypt_id(encrypt, 400);
        System.out.println("decrypt: " + decrypt);
        assertThat(encrypt).isEqualTo(decrypt);
    }
}