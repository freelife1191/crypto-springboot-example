package com.freelife.controller;

import com.freelife.core.BaseTest;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * JPA 컨트롤러 테스트코드
 * Created by mskwon on 2024. 10. 17..
 */
@Transactional
public class CryptoControllerTest extends BaseTest {

    public CryptoControllerTest(WebApplicationContext ctx) {
        super(ctx);
    }

    private final String prefix = "/crypto";

    @BeforeAll
    static void setUp() {
        // 종합 테스트 성능 측정 상속 클래스에 protected 로 지정한 totalStopWatch 사용
        totalStopWatch = new StopWatch("API Controller Total Tests");
    }

    /**
     * 모든 테스트 수행 시작시마다 반복적으로 수행됨
     */
    @BeforeEach
    void setData() {
        // 개별 테스트 성능 측정용
        stopWatch = new StopWatch("Single API Test");
        stopWatch.start();
    }

    @Test
    @Order(1)
    @DisplayName("POST: Crypto Session Hotel 암호화 테스트")
    void saveMember() throws Exception {
        totalStopWatch.start("POST: Crypto Session Hotel 암호화");
        mockMvc.perform(post(prefix + "/hotel/encrypt")
                .param("encrypt", "Hello Crypto!")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(2)
    @DisplayName("GET: Crypto Session Hotel 복호화 테스트")
    void selectMember() throws Exception {
        totalStopWatch.start("GET: Crypto Session Hotel 복호화");
        mockMvc.perform(get(prefix + "/hotel/decrypt")
                .param("decrypt", "Bcmot2MRLbpnQM+met+Alw==")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
    }

    @Test
    @Order(3)
    @DisplayName("POST: Crypto Session Hotel Hash 테스트")
    void hashHotel() throws Exception {
        totalStopWatch.start("POST: Crypto Session Hotel Hash");
        mockMvc.perform(post(prefix + "/hotel/hash")
                .param("plaintext", "we are the champion")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(4)
    @DisplayName("POST: Crypto Session Air 암호화 테스트")
    void updateMember() throws Exception {
        totalStopWatch.start("POST: Crypto Session Air 암호화");
        mockMvc.perform(post(prefix + "/air/encrypt")
                .param("encrypt", "Hello Crypto!")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(5)
    @DisplayName("GET: Crypto Session Air 복호화 테스트")
    void deleteMember() throws Exception {
        totalStopWatch.start("GET: Crypto Session Air 복호화");
        mockMvc.perform(get(prefix + "/air/decrypt")
                .param("decrypt", "Ok/Qhm/sOyI2p7VFeWnpRw==")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(6)
    @DisplayName("POST: Crypto Session Hotel Hash 테스트")
    void hashHAir() throws Exception {
        totalStopWatch.start("POST: Crypto Session Air Hash");
        mockMvc.perform(post(prefix + "/air/hash")
                .param("plaintext", "we are the champion")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    /**
     * 모든 테스트 수행 종료시마다 반복적으로 수행됨
     */
    @AfterEach
    void stop() {
        stopWatch.stop();
        System.out.println("===================================================");
        System.out.println("개별 테스트 성능 측정 결과");
        System.out.println("===================================================");
        System.out.println("Time Seconds = "+stopWatch.getTotalTimeSeconds()+"s");
        System.out.println("Time Millis = "+stopWatch.getTotalTimeMillis()+"ms");
        System.out.println("Time Nanos = "+stopWatch.getTotalTimeNanos()+"ns");
        //System.out.println(stopWatch.shortSummary());
        System.out.println(stopWatch.prettyPrint());
        System.out.println("===================================================");

        totalStopWatch.stop();
    }

    /**
     * 모든 테스트 종료시 1회 수행
     */
    @AfterAll
    static void end() {
        System.out.println("===================================================");
        System.out.println("종합 테스트 성능 측정 결과");
        System.out.println("===================================================");
        System.out.println("Total Time Seconds = "+totalStopWatch.getTotalTimeSeconds()+"s");
        System.out.println("Total Time Millis = "+totalStopWatch.getTotalTimeMillis()+"ms");
        System.out.println("Total Time Nanos = "+totalStopWatch.getTotalTimeNanos()+"ns");
        //System.out.println(stopWatch.shortSummary());
        System.out.println(totalStopWatch.prettyPrint());
        System.out.println("===================================================");
    }

}
