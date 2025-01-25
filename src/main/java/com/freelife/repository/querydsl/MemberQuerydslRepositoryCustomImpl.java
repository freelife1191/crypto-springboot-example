package com.freelife.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.freelife.domain.Member;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.freelife.domain.QMember.member;

public class MemberQuerydslRepositoryCustomImpl extends QuerydslRepositorySupport implements MemberQuerydslRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public MemberQuerydslRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        super(Member.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Member findEqualNameUser(String name) {
        return from(member)
                .where(member.name.eq((name)))
                .fetchOne();
    }

    @Override
    public List<Member> findMembers() {
        return from(member)
                .fetch();
    }
}
