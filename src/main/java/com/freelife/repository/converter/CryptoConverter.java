package com.freelife.repository.converter;

import com.freelife.crypto.core.CryptoSession;
import jakarta.annotation.Resource;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Crypto 암복호화 Converter
 * Created by mskwon on 2024. 10. 8..
 */
@Slf4j
@RequiredArgsConstructor
@Converter
public class CryptoConverter implements AttributeConverter<String, String> {

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
        if (StringUtils.isEmpty(value)) return value;
        String decrypt = hotelBasicCryptoSession.decrypt(value);
        log.debug("[Crypto] Converter Decrypt : {}", decrypt);
        return decrypt;
    }

}