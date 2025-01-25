package com.freelife.repository.mybatis;

import com.freelife.crypto.core.CryptoSession;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Crypto 암호화만 처리하는 TypeHandler
 * Created by mskwon on 2024. 10. 8..
 */
@Slf4j
@Component
public class CryptoNoDecHandler implements TypeHandler<String> {

    @Resource
    private CryptoSession hotelBasicCryptoSession;

    public String encrypt(String value) {
        if (StringUtils.isEmpty(value)) return value;
        String encrypt = hotelBasicCryptoSession.encrypt(value);
        log.debug("[Crypto] Converter Encrypt : {}", encrypt);
        return encrypt;
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        if ( StringUtils.isNotEmpty(parameter) )
            parameter = encrypt(parameter);
        ps.setString(i, parameter);
    }

    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getString(columnName);
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getString(columnIndex);
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getString(columnIndex);
    }
}
