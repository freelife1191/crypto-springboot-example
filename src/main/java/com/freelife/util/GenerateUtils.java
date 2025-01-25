package com.freelife.util;

import com.freelife.domain.Member;
import com.freelife.domain.MemberNoEnc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mskwon on 2024. 11. 19..
 */
public class GenerateUtils {

    public static Member generateMember() {
        return Member.builder()
                .name("홍길동")
                .nickName("hong")
                .age(10)
                .password("1111")
                .build();
    }

    public static List<Member> generateMembers() {
        return generateMembers(generateMember(), 1000);
    }

    public static List<Member> generateMembers(Member member) {
        return generateMembers(member, 1000);
    }

    public static List<Member> generateMembers(int count) {
        return generateMembers(generateMember(), count);
    }

    public static List<Member> generateMembers(Member member, int count) {
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            members.add(
                    Member.builder()
                            .name(member.getName() + i)
                            .nickName(member.getNickName() + i)
                            .age(member.getAge())
                            .password(member.getPassword())
                            .build());
        }
        return members;
    }

    public static MemberNoEnc generateMemberNoEnc() {
        return MemberNoEnc.builder()
                .name("홍길동")
                .nickName("hong")
                .age(10)
                .password("1111")
                .build();
    }

    public static List<MemberNoEnc> generateMembersNoEnc() {
        return generateMembersNoEnc(generateMemberNoEnc(), 1000);
    }

    public static List<MemberNoEnc> generateMembersNoEnc(MemberNoEnc member) {
        return generateMembersNoEnc(member, 1000);
    }

    public static List<MemberNoEnc> generateMembersNoEnc(int count) {
        return generateMembersNoEnc(generateMemberNoEnc(), count);
    }

    public static List<MemberNoEnc> generateMembersNoEnc(MemberNoEnc member, int count) {
        List<MemberNoEnc> members = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            members.add(
                    MemberNoEnc.builder()
                            .name(member.getName() + i)
                            .nickName(member.getNickName() + i)
                            .age(member.getAge())
                            .password(member.getPassword())
                            .build());
        }
        return members;
    }
}
