package com.freelife.repository.querydsl;

import com.freelife.domain.Member;

import java.util.List;

public interface MemberQuerydslRepositoryCustom {
    // 동일 여부
    Member findEqualNameUser(String name);

    List<Member> findMembers();
}
