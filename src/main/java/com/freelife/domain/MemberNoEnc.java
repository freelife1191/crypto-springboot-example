package com.freelife.domain;

import com.freelife.repository.converter.CryptoConverter;
import com.freelife.repository.converter.CryptoNoDecConverter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class MemberNoEnc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "nick_name", nullable = false)
    private String nickName;
    @Column(name = "age", nullable = false)
    private Integer age;
    /**
     * CryptoConverter 를 사용하여 암복호화 처리
     */
    @Convert(converter = CryptoConverter.class)
    @Column(nullable = false)
    private String password;

    @Convert(converter = CryptoNoDecConverter.class)
    @Column(name = "enc_password", nullable = false)
    private String encPassword;

    @Builder
    public MemberNoEnc(String name, String nickName, Integer age, String password) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
        this.password = password;
        this.encPassword = password;
    }

    public void updateMember(MemberNoEnc member) {
        this.name = member.getName();
        this.nickName = member.getNickName();
        this.age = member.getAge();
        this.password = member.getPassword();
        this.encPassword = member.getPassword();
    }
}