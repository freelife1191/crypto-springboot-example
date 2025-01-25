package com.freelife.core;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


/**
 * BASE 컨트롤러 테스트 클래스
 * Created by KMS on 06/03/2020.
 */
@ActiveProfiles("local")
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
public class BaseTest {
    protected final MockMvc mockMvc;
    protected final WebApplicationContext ctx;
    protected StopWatch stopWatch; //개별 테스트 성능 측정용
    protected static StopWatch totalStopWatch; //종합 테스트 성능 측정용

    public BaseTest(WebApplicationContext ctx) {
        this.ctx = ctx;
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
                .alwaysDo(print())
                .build();
    }
}