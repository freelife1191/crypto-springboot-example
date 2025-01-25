package com.freelife.factory;

import com.freelife.config.CryptoProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.freelife.factory.CryptoSessionInit.initCryptoSession;

/**
 * CryptoSessionFactory 객체를 사용해 CryptoSessionFactory Bean을 생성
 * Created by mskwon on 2024. 10. 17..
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class CryptoFactoryConfig {

    private final CryptoProperties cryptoProperties;

    /**
     * CryptoSessionFactory Bean 을 생성하여 다중 세션을 관리
     */
    @Bean
    public CryptoSessionFactory<CryptoSessionType> cryptoSessionFactory() {
        List<CryptoSessionInit<CryptoSessionType>> cryptoSessionInits = List.of(
                // HOTEL CryptoSession 생성
                CryptoSessionInit.ofLocalParams(CryptoSessionType.HOTEL,
                        cryptoProperties.getHotel().getKey(),
                        cryptoProperties.getHotel().getIv(),
                        cryptoProperties.getHotel().getSeed(),
                        cryptoProperties.getHotel().getCredential()),
                // AIR CryptoSession 생성
                CryptoSessionInit.ofLocalMap(CryptoSessionType.AIR, cryptoProperties.getAir().configLocalMap())
                // CryptoSessionInit.ofBasePath(CryptoSessionType.AIR)
        );
        return new CryptoSessionFactory<>(cryptoSessionInits);
    }
}