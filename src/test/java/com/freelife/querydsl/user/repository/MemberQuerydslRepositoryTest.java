package com.freelife.querydsl.user.repository;

import com.freelife.domain.Member;
import com.freelife.repository.querydsl.MemberQuerydslRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberQuerydslRepositoryTest {

    @Autowired
    private MemberQuerydslRepository memberQuerydslRepository;

    @BeforeEach
    void setUp() {
        Member user1 = Member.builder()
                .name("수미")
                .nickName("sumi")
                .password("1111")
                .age(10)
                .build();
        Member user2 = Member.builder()
                .name("동건")
                .nickName("dong")
                .password("1111")
                .age(20)
                .build();

        Member user3 = Member.builder()
                .name("몽미")
                .nickName("mong")
                .password("1111")
                .age(30)
                .build();

        Member user4 = Member.builder()
                .name("주희")
                .nickName("juhee")
                .password("1111")
                .age(40)
                .build();

        Member user5 = Member.builder()
                .name("냥이")
                .nickName("cat")
                .password("1111")
                .age(50)
                .build();

        memberQuerydslRepository.save(user1);
        memberQuerydslRepository.save(user2);
        memberQuerydslRepository.save(user3);
        memberQuerydslRepository.save(user4);
        memberQuerydslRepository.save(user5);
    }

    @Test
    @DisplayName("이름이 수미인 회원을 찾을 때")
    public void findEqualNameUser() {
        // when
        Member result = memberQuerydslRepository.findEqualNameUser("수미");
        log.debug("result: {}", result);
        assertThat(result.getPassword()).isEqualTo("1111");
    }

    @Test
    @DisplayName("전체 회원 조회")
    public void findMembers() {
        // when
        List<Member> members = memberQuerydslRepository.findMembers();
        log.debug("members: {}", members);
        assertThat(members.size()).isGreaterThan(0);
    }

}
