package com.freelife.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.freelife.core.BaseTest;
import com.freelife.domain.Member;
import com.freelife.domain.RequestMemberDto;
import com.freelife.service.CryptoService;
import com.freelife.util.JsonUtils;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * JPA 컨트롤러 테스트코드
 * Created by mskwon on 2024. 10. 17..
 */
@Transactional
public class CryptoJpaControllerTest extends BaseTest {

    public CryptoJpaControllerTest(WebApplicationContext ctx) {
        super(ctx);
    }

    private final String prefix = "/jpa";

    // @Autowired
    // @Qualifier("memberJpaService")
    @Resource(name = "cryptoJpaService")
    private CryptoService cryptoService;

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
        Member member = Member.builder()
                .name("홍길동")
                .nickName("hong")
                .age(10)
                .password("1111")
                .build();
        Member member1 = Member.builder()
                .name("수미")
                .nickName("sumi")
                .age(10)
                .password("1111")
                .build();
        Member member2 = Member.builder()
                .name("동건")
                .nickName("dong")
                .age(10)
                .password("1111")
                .build();

        Member member3 = Member.builder()
                .name("몽미")
                .nickName("mong")
                .age(10)
                .password("1111")
                .build();

        Member member4 = Member.builder()
                .name("주희")
                .nickName("juhee")
                .age(10)
                .password("1111")
                .build();

        Member member5 = Member.builder()
                .name("냥이")
                .nickName("cat")
                .age(10)
                .password("1111")
                .build();

        cryptoService.save(member);
        cryptoService.save(member1);
        cryptoService.save(member2);
        cryptoService.save(member3);
        cryptoService.save(member4);
        cryptoService.save(member5);
    }

    @Test
    @Order(1)
    @DisplayName("POST: JPA 회원 추가 테스트")
    void saveMember() throws Exception {
        totalStopWatch.start("POST: JPA 회원 추가 테스트");
        mockMvc.perform(post(prefix + "/member")
                .content(JsonUtils.getObjectMapper().writeValueAsString(RequestMemberDto.builder()
                        .name("홍길동")
                        .nickName("hong")
                        .age(10)
                        .password("1111")
                        .build()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(2)
    @DisplayName("GET: JPA 회원 조회 테스트")
    void selectMember() throws Exception {
        totalStopWatch.start("GET: JPA 회원 조회");
        MvcResult result = mockMvc.perform(get(prefix + "/member")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        List<Member> members = JsonUtils.toMapperObject(content, new TypeReference<>() {
        });
        assert members != null;
        members.stream().findFirst().ifPresent(member -> {
            assertThat(member.getPassword()).isEqualTo("1111");
        });
        // System.out.println(JsonUtils.toMapperPretty(members));
    }

    @Test
    @Order(3)
    @DisplayName("PUT: JPA 회원 수정 테스트")
    void updateMember() throws Exception {
        totalStopWatch.start("PUT: JPA 회원 수정");
        mockMvc.perform(put(prefix + "/member/{id}", 14)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Order(4)
    @DisplayName("DELETE: JPA 회원 삭제 테스트")
    void deleteMember() throws Exception {
        totalStopWatch.start("DELETE: JPA 회원 삭제");
        mockMvc.perform(delete(prefix + "/member/{id}", 20)
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
