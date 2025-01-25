package com.freelife.factory.factorybean;

import com.freelife.crypto.core.CryptoSession;
import com.freelife.factory.CryptoSessionInit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;


/**
 * CryptoSession 를 안전하게 다중 설정하기 위해 FactoryBean Interface 를 구현한 FactoryBean
 * CryptoSessionJavaFactoryBean 을 사용하기 위해서 CryptoSessionType 을 Enum 으로 생성해서
 * 각 서비스 별로 CryptoSessionType 을 정의하고 CryptoSessionJavaFactoryBean 을 생성하여 사용
 * Created by mskwon on 2024. 10. 17..
 */
@Slf4j
public class CryptoSessionJavaFactoryBean<T extends Enum<T>> implements FactoryBean<CryptoSession>, InitializingBean {

      private final CryptoSessionInit<T> cryptoSessionInit;
      private CryptoSession cryptoSession;

      private CryptoSessionJavaFactoryBean(CryptoSessionInit<T> cryptoSessionInit) {
          this.cryptoSessionInit = cryptoSessionInit;
      }

      @Override
      public CryptoSession getObject() {
          return cryptoSession;
      }

      @Override
      public Class<CryptoSession> getObjectType() {
          return CryptoSession.class;
      }

      @Override
      public boolean isSingleton() {
          return FactoryBean.super.isSingleton();
      }

      @Override
      public void afterPropertiesSet() {
          log.info("Initialize CryptoSession FactoryBean: {}: {}", cryptoSessionInit.getCryptoSessionType(),  cryptoSessionInit.getInitType());
          cryptoSession = CryptoSessionInit.initCryptoSession(cryptoSessionInit);
      }

      public static <E extends Enum<E>> CryptoSessionFactoryBean<E> of(CryptoSessionInit<E> cryptoSessionInit) {
          if (cryptoSessionInit == null)
              throw new IllegalArgumentException("CryptoSessionInit must not be null");
          return new CryptoSessionFactoryBean<>(cryptoSessionInit);
      }
}
