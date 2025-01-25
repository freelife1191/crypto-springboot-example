package com.freelife.repository.querydsl;

import com.freelife.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface MemberQuerydslRepository extends JpaRepository<Member, Long>, MemberQuerydslRepositoryCustom, QuerydslPredicateExecutor<Member> {
    Member findByName(String name);
}
