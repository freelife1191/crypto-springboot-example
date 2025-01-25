package com.freelife.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Created by mskwon on 2024. 10. 8..
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberMybatisServiceTest {

    private final CryptoPerformanceService cryptoService;

    public MemberMybatisServiceTest(@Qualifier("cryptoMybatisService") CryptoPerformanceService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @Test
    @Order(1)
    void generateMember() {
        cryptoService.generateMember();
    }

    @Test
    @Order(2)
    void findMembers() {
        cryptoService.findMembers(100);
    }

    @Test
    @Order(3)
    void updateMember() {
        cryptoService.updateMember(1L);
    }

    @Test
    @Order(4)
    void deleteMember() {
        cryptoService.deleteMember(1L);
    }
}