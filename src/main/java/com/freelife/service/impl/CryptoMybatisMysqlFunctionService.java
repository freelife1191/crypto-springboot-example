package com.freelife.service.impl;

import com.freelife.domain.Member;
import com.freelife.domain.ResponseMemberDto;
import com.freelife.domain.ResponseMemberNoEncDto;
import com.freelife.repository.mybatis.MemberMysqlFunctionMapper;
import com.freelife.service.CryptoFunctionService;
import com.freelife.util.GenerateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.freelife.util.GenerateUtils.generateMembers;
import static com.freelife.util.GenerateUtils.generateMembersNoEnc;

@Profile("dev")
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CryptoMybatisMysqlFunctionService implements CryptoFunctionService {

    private final MemberMysqlFunctionMapper memberMapper;

    @Override
    public ResponseMemberDto save(Member member) {
        memberMapper.save(member);
        return findById(member.getId());
    }

    @Override
    public int saveAll(Integer count) {
        return memberMapper.saveAll(generateMembers(count));
    }

    @Override
    public int saveAllPetra(Integer count) {
        return memberMapper.saveAllPetra(generateMembers(count));
    }

    @Override
    public int saveAllNoEnc(Integer count) {
        return memberMapper.saveAllNoEnc(generateMembersNoEnc(count));
    }

    @Override
    public ResponseMemberDto generateMember() {
        Member member = GenerateUtils.generateMember();
        memberMapper.save(member);
        return findById(member.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseMemberDto> findMembers(Integer count) {
        return memberMapper.findAll(count).stream()
                .map(ResponseMemberDto::fromEntity)
                .toList();
    }

    @Override
    public List<ResponseMemberDto> findMembersPetra(Integer count) {
        return memberMapper.findAllPetra(count).stream()
                .map(ResponseMemberDto::fromEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseMemberNoEncDto> findMembersNoEnc(Integer count) {
        return memberMapper.findAllNoEnc(count).stream()
                .map(ResponseMemberNoEncDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseMemberDto findById(Long id) {
        return ResponseMemberDto.fromEntity(memberMapper.findById(id));
    }

    @Override
    public List<ResponseMemberDto> updateMember(Long id) {
        Member member = memberMapper.findById(id);
        if (member == null) return List.of();
        member.setName("홍길순");
        member.setNickName("gilsoon");
        member.setAge(20);
        member.setPassword("5678");
        member.setEncPassword("5678");
        memberMapper.update(member);
        return findMembers(100);
    }

    @Override
    public List<ResponseMemberDto> deleteMember(Long id) {
        memberMapper.delete(id);
        return findMembers(100);
    }
}
