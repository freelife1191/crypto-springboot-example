package com.freelife.repository.mybatis;

import com.freelife.domain.Member;
import com.freelife.domain.MemberNoEnc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberOracleFunctionMapper {

    int save(@Param("member") Member member);
    int saveAll(@Param("list") List<Member> list);
    int saveAllPetra(@Param("list") List<Member> list);
    int saveAllNoEnc(@Param("list") List<MemberNoEnc> list);
    List<Member> findAll(@Param("limit") Integer count);
    List<Member> findAllPetra(@Param("limit") Integer count);
    List<MemberNoEnc> findAllNoEnc(@Param("limit") Integer count);
    Member findById(@Param("id") Long id);
    void update(@Param("member") Member member);
    void delete(@Param("id") Long id);
}
