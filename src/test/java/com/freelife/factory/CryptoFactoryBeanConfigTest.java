package com.freelife.factory;

import com.freelife.factory.factorybean.CryptoSessions;
import com.freelife.crypto.core.CryptoException;
import com.freelife.crypto.core.CryptoSession;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * CryptoSessionFactoryBean 을 사용한 CryptoSession 테스트
 * Created by mskwon on 2024. 10. 17..
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CryptoFactoryBeanConfigTest {

    @Resource
    private CryptoSession hotelCryptoSession;

    @Resource
    private CryptoSession airCryptoSession;

    @Resource
    private CryptoSessions<CryptoSessionType> cryptoSessions;

    @Test
    void hotelCryptoSessionTest() {
        String plaintext = "Hello Crypto!";
        String encrypt = hotelCryptoSession.encrypt(plaintext);
        String decrypt = hotelCryptoSession.decrypt(encrypt);
        String hash = hotelCryptoSession.encrypt_id(plaintext, 400);
        assertAll(
            () -> assertNotNull(hotelCryptoSession),
            () -> assertEquals(decrypt, plaintext),
            () -> assertTrue(hash.length() >= 88)
        );
    }

    @Test
    void airCryptoSessionTest() {
        String plaintext = "Hello Crypto!";
        String encrypt = airCryptoSession.encrypt(plaintext);
        String decrypt = airCryptoSession.decrypt(encrypt);
        String hash = airCryptoSession.encrypt_id(plaintext, 400);
        assertAll(
            () -> assertNotNull(airCryptoSession),
            () -> assertEquals(decrypt, plaintext),
            () -> assertTrue(hash.length() >= 88)
        );
    }

    @Test
    void multi_Initializing_cryptoSession() {
        String plaintext = "Hello Crypto!";
        String encryptHotel = cryptoSessions.getHotelCryptoSession().encrypt(plaintext);
        String decryptHotel = cryptoSessions.getHotelCryptoSession().decrypt(encryptHotel);
        String hashHotel = cryptoSessions.getHotelCryptoSession().encrypt_id(plaintext, 400);
        String encryptAir = cryptoSessions.getAirCryptoSession().encrypt(plaintext);
        String decryptAir = cryptoSessions.getAirCryptoSession().decrypt(encryptAir);
        String hashAir = cryptoSessions.getAirCryptoSession().encrypt_id(plaintext, 400);
        assertAll(
            () -> assertNotNull(cryptoSessions.getHotelCryptoSession()),
            () -> assertNotNull(cryptoSessions.getAirCryptoSession()),
            () -> assertEquals(decryptHotel, plaintext),
            () -> assertEquals(decryptAir, plaintext),
            () -> assertTrue(hashHotel.length() >= 88),
            () -> assertTrue(hashAir.length() >= 88)
        );
    }

    @Test
    void cross_valication_test() {
        String plaintext = "Hello Crypto!";
        String encryptHotel = cryptoSessions.getHotelCryptoSession().encrypt(plaintext);
        String encryptAir = cryptoSessions.getAirCryptoSession().encrypt(plaintext);
        assertThrows(CryptoException.class, () -> {
            cryptoSessions.getHotelCryptoSession().decrypt(encryptAir);
            cryptoSessions.getAirCryptoSession().decrypt(encryptHotel);
        });
    }

    @Test
    void failed_decrypt_test() {
        String plaintext = "Hello Crypto!";
        String encrypt = cryptoSessions.getHotelCryptoSession().encrypt(plaintext);
        assertThrows(CryptoException.class, () -> cryptoSessions.getAirCryptoSession().decrypt(encrypt));
    }

    @Test
    void failed_decrypt_hash_test() {
        String plaintext = "Hello Crypto!";
        String encrypt = cryptoSessions.getHotelCryptoSession().encrypt_id(plaintext, 400);
        String decrypt = cryptoSessions.getHotelCryptoSession().decrypt_id(encrypt, 400);
        assertThat(decrypt).isEqualTo(encrypt);
    }

}