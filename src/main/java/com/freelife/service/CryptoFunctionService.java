package com.freelife.service;

import com.freelife.domain.Member;
import com.freelife.domain.ResponseMemberDto;
import com.freelife.domain.ResponseMemberNoEncDto;

import java.util.List;

/**
 * Created by mskwon on 2024. 10. 16..
 */
public interface CryptoFunctionService {

    ResponseMemberDto save(Member member);
    int saveAll(Integer count);
    int saveAllPetra(Integer count);
    int saveAllNoEnc(Integer count);
    ResponseMemberDto generateMember();
    List<ResponseMemberDto> findMembers(Integer count);
    List<ResponseMemberDto> findMembersPetra(Integer count);
    List<ResponseMemberNoEncDto> findMembersNoEnc(Integer count);
    ResponseMemberDto findById(Long id);
    List<ResponseMemberDto> updateMember(Long id);
    List<ResponseMemberDto> deleteMember(Long id);
}
