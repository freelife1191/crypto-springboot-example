package com.freelife.service.impl;

import com.freelife.domain.Member;
import com.freelife.domain.ResponseMemberDto;
import com.freelife.crypto.core.CryptoSession;
import com.freelife.repository.querydsl.MemberQuerydslRepository;
import com.freelife.service.CryptoService;
import com.freelife.util.GenerateUtils;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CryptoQuerydslService implements CryptoService {

    private final MemberQuerydslRepository memberRepository;

    @Resource
    private final CryptoSession basicCryptoSession;

    @Override
    public ResponseMemberDto save(Member member) {
        memberRepository.save(member);
        return findById(member.getId())
                .setEncPassword(basicCryptoSession.encrypt(member.getPassword())); // Querydsl save 후 조회 SQL이 실행되지 않아 암호회 되지 않음으로 인한 조치
    }

    @Override
    public int saveAll(Member member) {
        return 0;
    }

    @Override
    public ResponseMemberDto generateMember() {
        return ResponseMemberDto.fromEntity(
                memberRepository.save(GenerateUtils.generateMember()));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseMemberDto> findMembers() {
        return memberRepository.findAll().stream()
                .map(ResponseMemberDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseMemberDto findById(Long id) {
        ResponseMemberDto responseMemberDto = memberRepository.findById(id)
                .map(ResponseMemberDto::fromEntity)
                .orElseThrow();
        log.debug("responseMemberDto: {}", responseMemberDto);
        return responseMemberDto;
    }

    @Override
    public List<ResponseMemberDto> updateMember(Long id) {
        Member member = memberRepository.findById(id).orElse(null);
        if (member == null) return List.of();
        memberRepository.findById(member.getId())
                .map(m -> {
                    m.setName("홍길순");
                    m.setNickName("gilsoon");
                    m.setAge(20);
                    m.setPassword("5678");
                    return m;
                }).ifPresent(m -> m.updateMember(m));
        return findMembers().stream()
                .map(m -> m.setEncPassword(basicCryptoSession.encrypt(m.getPassword()))) // Querydsl update 후 시 조회 SQL이 실행되지 않아 암호회 되지 않음으로 인한 조치
                .toList();
    }

    @Override
    public List<ResponseMemberDto> deleteMember(Long id) {
        Member member = memberRepository.findById(id).orElse(null);
        if (member == null) return List.of();
        memberRepository.delete(member);
        return findMembers();
    }
}
