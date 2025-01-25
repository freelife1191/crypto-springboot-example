package com.freelife.config;

import com.freelife.crypto.core.CryptoSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * CryptoSession 기본 Bean 설정 클래스
 * Created by mskwon on 2024. 10. 23..
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class CryptoConfig {

    private final CryptoProperties cryptoProperties;

    /**
     * Singleton CryptoSession Bean 을 생성
     * @return CryptoSession instance
     */
    @Primary
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public CryptoSession basicCryptoSession() throws Exception {
        Resource resource =  new ClassPathResource(cryptoProperties.getPath());
        CryptoSession cryptoSession = new CryptoSession(resource.getFile().getAbsolutePath());
        log.info("Initializing basicCryptoSession: {}", cryptoSession);
        return cryptoSession;
    }

    /**
     * Singleton hotelBasicCryptoSession Bean 을 생성
     * @return CryptoSession instance
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public CryptoSession hotelBasicCryptoSession() {
        CryptoSession cryptoSession = new CryptoSession(cryptoProperties.getHotel().configLocalMap(), cryptoProperties.getHotel().getKey(), cryptoProperties.getHotel().getIv());
        // Resource resource =  new ClassPathResource(cryptoProperties.getPath());
        // CryptoSession cryptoSession = new CryptoSession(resource.getFile().getAbsolutePath());
        log.info("Initializing hotelBasicCryptoSession: {}", cryptoSession);
        return cryptoSession;
    }

    /**
     * Singleton airBasicCryptoSession Bean 을 생성
     * @return CryptoSession instance
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public CryptoSession airBasicCryptoSession() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(cryptoProperties.getAir().getPath());
        CryptoSession cryptoSession = new CryptoSession(classPathResource.getInputStream());
        log.info("Initializing airBasicCryptoSession: {}", cryptoSession);
        return cryptoSession;
    }
}