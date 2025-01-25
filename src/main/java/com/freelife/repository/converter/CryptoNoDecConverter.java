package com.freelife.repository.converter;

import com.freelife.crypto.core.CryptoSession;
import jakarta.annotation.Resource;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

/**
 * Crypto 암호화만 처리하는 Converter
 * Created by mskwon on 2024. 10. 8..
 */
@Slf4j
@RequiredArgsConstructor
@Converter
public class CryptoNoDecConverter implements AttributeConverter<String, String> {

    @Resource
    private final CryptoSession hotelBasicCryptoSession;

    /**
     * 엔티티 -> DB 시 데이터 변환
     * Base64 인코딩
     * @param value
     * @return
     */
    @Override
    public String convertToDatabaseColumn(String value) {
        if (StringUtils.isEmpty(value)) return value;
        String encrypt = hotelBasicCryptoSession.encrypt(value);
        log.debug("[Crypto] Converter Encrypt : {}", encrypt);
        return encrypt;
    }

    /**
     * DB -> 엔티티 시 데이터 변환
     * @param value
     * @return
     */
    @Override
    public String convertToEntityAttribute(String value) {
        return value;
    }

}