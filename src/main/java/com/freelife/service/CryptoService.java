package com.freelife.service;

import com.freelife.domain.Member;
import com.freelife.domain.ResponseMemberDto;

import java.util.List;

/**
 * Created by mskwon on 2024. 10. 16..
 */
public interface CryptoService {

    ResponseMemberDto save(Member member);
    int saveAll(Member member);
    ResponseMemberDto generateMember();
    List<ResponseMemberDto> findMembers();
    ResponseMemberDto findById(Long id);
    List<ResponseMemberDto> updateMember(Long id);
    List<ResponseMemberDto> deleteMember(Long id);
}
