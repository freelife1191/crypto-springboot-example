package com.freelife.factory.factorybean;

import com.freelife.factory.CryptoSessionInit;
import com.freelife.crypto.core.CryptoSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.AbstractFactoryBean;


/**
 * CryptoSession 를 안전하게 다중 설정하기 위한 FactoryBean
 * CryptoSessionFactoryBean 을 사용하기 위해서 CryptoSessionType 을 Enum 으로 생성해서
 * 각 서비스 별로 CryptoSessionType 을 정의하고 CryptoSessionFactoryBean 을 생성하여 사용
 *
 * Spring 에서 FactoryBean 구현을 위해 간단한 템플릿 슈퍼클래스로 제공되는 AbstractFactoryBean 을 사용하여 구현
 * https://www.baeldung.com/spring-factorybean
 * Created by mskwon on 2024. 10. 17..
 */
@Slf4j
public class CryptoSessionFactoryBean<T extends Enum<T>> extends AbstractFactoryBean<CryptoSession> {

    private CryptoSessionInit<T> cryptoSessionInit;

    CryptoSessionFactoryBean(CryptoSessionInit<T> cryptoSessionInit) {
        this.cryptoSessionInit = cryptoSessionInit;
    }

    @Override
    public Class<?> getObjectType() {
        return CryptoSession.class;
    }

    @Override
    protected CryptoSession createInstance() throws Exception {
        log.info("Initialize CryptoSession FactoryBean: {}: {}", cryptoSessionInit.getCryptoSessionType(),  cryptoSessionInit.getInitType());
        return CryptoSessionInit.initCryptoSession(cryptoSessionInit);
    }

    public static <T extends Enum<T>> CryptoSessionFactoryBean<T> of(CryptoSessionInit<T> cryptoSessionInit) {
        if (cryptoSessionInit == null)
            throw new IllegalArgumentException("CryptoSessionInit must not be null");
        return new CryptoSessionFactoryBean<>(cryptoSessionInit);
    }

}
