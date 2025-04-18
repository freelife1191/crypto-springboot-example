# 번외 - `CryptoSession` Factory 설정 및 사용 방법

---

## 🚦 1. `CryptoSessionInit<T extends Enum<T>>` 타입별 초기화 객체 사용 방법

---

`CryptoSessionInit<T extends Enum<T>>` 객체는 다중 `CryptoSession` 객체를 타입세이프하게 생성하기 위한 초기화 클래스로
`CryptoSessionFactory`, `CryptoSessionFactoryBean`, `CryptoSessionImpl` 클래스를 사용하여 다중 `CryptoSession` 객체를 초기화할 경우   
편리한 초기 설정을 돕고자 만들어졌다


### 📌 `CryptoSessionInit<T extends Enum<T>>` 구현

- CryptoSessionInit 구현 예시 코드: [${proejctDir}/src/main/java/com/freelife/factory/CryptoSessionInit.java](src/main/java/com/freelife/factory/CryptoSessionInit.java)
- CryptoSessionType Enum 클래스 구현 예시 코드: [${proejctDir}/src/main/java/com/freelife/factory/CryptoSessionType.java](src/main/java/com/freelife/factory/CryptoSessionType.java)

`CryptoSessionInit` 객체는 `CryptoSession` 에서 가능한 총 8가지 타입(`BASE_PATH`, `BYTES`, `INPUT_STREAM`, `PATH`, `MAP`, `PARAMS`, `LOCAL_MAP`, `LOCAL_PARAMS`)의  
`CryptoSession` 객체를 초기화할 수 있는 메서드를 제공한다

`CryptoSessionInit` 객체 사용을 위해 각 서비스에서 사용할 `CryptoSessionType` **Enum 클래스**를 커스텀하게 생성하여 타입을 지정해야 된다

`CryptoSessionType` 생성 예시

```java
public enum CryptoSessionType {
    HOTEL, // 호텔 서비스용 CryptoSession 타입
    AIR, // 항공 서비스용 CryptoSession 타입
    TOACT // TOACT 서비스용 CryptoSession 타입
}
```


### 📌 `CryptoSessionInit<T extends Enum<T>>` 객체 초기화 방법

설정하는 방법은 위에서 설명한 `CryptoSession` 객체 초기화를 위한 설정 방법과 비슷하므로 타입별 메서드를 사용하는 방법만 참고하면 된다

- 사용 예제 코드:  [${proejctDir}/src/test/java/com/freelife/factory/CryptoSessionInitTest.java](src/main/java/com/freelife/factory/CryptoSessionInit.java)

#### ► 1.  BASE_PATH `CryptoSessionInit` 초기화 예시

`BASE_PATH` 타입은 기본 설정 타입으로 단일 DB를 사용하는 경우에는 아래의 기본 경로에 `config.json` 파일을 위치시키고 사용할 수 있다

현재 지원되는 기본 경로는 아래와 같다

- `${projectDir}/crypto/config.json`
- `/opt/crypto/config.json`
- `/var/crypto/config.json`

`CryptoSession` 객체 초기화시 별도의 파라메터를 넘겨주지 않으면 기본 경로에 위치한 `config.json` 파일을 사용한다  
만약 기본 경로에 위치한 `config.json` 파일이 존재하지 않으면 에러가 발생하므로 기본 경로 타입으로 설정시  
반드시 기본 경로에 `config.json` 파일을 생성 후 사용하기 바란다

```java
CryptoSessionInit<CryptoSessionType> basePathInit = CryptoSessionInit.ofBasePath(CryptoSessionType.HOTEL);
```

#### ► 2.  BYTES `CryptoSessionInit` 초기화 예시

`BYTES` 타입은 `config.json` 파일을 바이트 배열로 변환하여 사용하는 타입이다

```java
ClassPathResource resource = new ClassPathResource(Path.of("crypto", "hotel", "config.json").toString());
CryptoSessionInit<CryptoSessionType> bytesInit = CryptoSessionInit.ofBytes(
        CryptoSessionType.HOTEL,
        resource.getInputStream().readAllBytes()
);
```

#### ► 3.  INPUT_STREAM `CryptoSessionInit` 초기화 예시

`INPUT_STREAM` 타입은 `config.json` 파일을 `InputStream` 으로 변환하여 사용하는 타입이다

```java
ClassPathResource resource = new ClassPathResource(Path.of("crypto", "hotel", "config.json").toString());
CryptoSessionInit<CryptoSessionType> inputStreamInit = CryptoSessionInit.ofInputStream(
        CryptoSessionType.HOTEL,
        resource.getInputStream()
);
```

#### ► 4.  PATH `CryptoSessionInit` 초기화 예시

`PATH` 타입은 `config.json` 파일의 경로를 지정하여 사용하는 타입이다

```java
Resource resource = new ClassPathResource(Path.of("crypto", "hotel", "config.json").toString());
CryptoSessionInit<CryptoSessionType> pathInit = CryptoSessionInit.ofPath(
        CryptoSessionType.HOTEL,
        resource.getFile().getPath()
);
```

#### ► 5.  MAP `CryptoSessionInit` 초기화 예시

`MAP` 타입은 설정 정보를 `Map` 으로 변환하여 사용하는 타입이다

```java
String awsKmsKeyArn = "arn:aws:kms:ap-northeast-2:123456789012:key/12345678-1234-1234-1234-123456789012";
String awsAccessKeyId = "AXXXXXXXXXXXXXXXXXXX;
String awsSecretAccessKey = "53gXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX;
String seed = "AXXXXXXXXXXXXXXXXXX";
String credential = "BXXXXXXXXXXXXXXXXXXXX";
CryptoSessionInit<CryptoSessionType> mapInit = CryptoSessionInit.ofMap(
        CryptoSessionType.HOTEL,
        Map.of(
                "aws_kms_key_arn", awsKmsKeyArn,
                "aws_access_key_id", awsAccessKeyId,
                "aws_secret_access_key", awsSecretAccessKey,
                "seed", seed,
                "credential", credential
        )
);
```

#### ► 6.  PARAMS `CryptoSessionInit` 초기화 예시

`PARAMS` 타입은 설정 정보를 파라메터로 전달하여 사용하는 타입이다

```java
String awsKmsKeyArn = "arn:aws:kms:ap-northeast-2:123456789012:key/12345678-1234-1234-1234-123456789012";
String awsAccessKeyId = "AXXXXXXXXXXXXXXXXXXX;
String awsSecretAccessKey = "53gXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX;
String seed = "AXXXXXXXXXXXXXXXXXX";
String credential = "BXXXXXXXXXXXXXXXXXXXX";
CryptoSessionInit<CryptoSessionType> paramsInit = CryptoSessionInit.ofParams(
        CryptoSessionType.HOTEL,
        awsKmsKeyArn, awsAccessKeyId, awsSecretAccessKey, seed, credential
);
```

#### ► 5.  LOCAL_MAP `CryptoSessionInit` 초기화 예시

`LOCAL_MAP` 타입은 설정 정보를 AWS 정보를 제외하고 `key`, `iv`를 포함해 `Map` 으로 변환하여 사용하는 타입이다

```java
String key = "KXXXXXXXXXXXXXXXX";
String iv = "00000000000000000000000000000000;
String seed = "AXXXXXXXXXXXXXXXXXX";
String credential = "BXXXXXXXXXXXXXXXXXXXX";
CryptoSessionInit<CryptoSessionType> mapInit = CryptoSessionInit.ofLocalMap(
        CryptoSessionType.HOTEL,
        Map.of(
                "key", key,
                "iv", iv,
                "seed", seed,
                "credential", credential
        )
);
```

#### ► 6.  LOCAL_PARAMS `CryptoSessionInit` 초기화 예시

`LOCAL_PARAMS` 타입은 LOCAL 설정 정보를 파라메터로 전달하여 사용하는 타입이다

```java
String key = "KXXXXXXXXXXXXXXXX";
String iv = "00000000000000000000000000000000;
String seed = "AXXXXXXXXXXXXXXXXXX";
String credential = "BXXXXXXXXXXXXXXXXXXXX";
CryptoSessionInit<CryptoSessionType> paramsInit = CryptoSessionInit.ofLocalParams(
        CryptoSessionType.HOTEL,
        key, iv, seed, credential
);
```


## 🚦 2. `CryptoSessionFactory<T extends Enum<T>>` 클래스 구현 및 설정 방법

---

`CryptoSessionFactory<T extends Enum<T>>` 는 `CryptoSessionInit<T extends Enum<T>>` 객체를 사용하여 `CryptoSession` 다중 `Bean` 설정을 지원하기 위한 **Factory** 클래스이다

자세한 사용법은 아래의 코드를 참고하기 바란다


### 📌 `CryptoSessionFactory<T extends Enum<T>>` 구성 및 `CryptoSession` 다중 `Bean` 설정 방법

- CryptoSessionFactory 구현 예시 코드: [${proejctDir}/src/main/java/com/freelife/factory/CryptoSessionFactory.java](src/main/java/com/freelife/factory/CryptoSessionFactory.java)
- 설정 예시 코드: [${proejctDir}/src/main/java/com/freelife/factory/CryptoFactoryConfig.java](src/main/java/com/freelife/factory/CryptoFactoryConfig.java)

```java
public enum CryptoSessionType {
    HOTEL, // 호텔 서비스용 CryptoSession 타입
    AIR, // 항공 서비스용 CryptoSession 타입
    TOACT // TOACT 서비스용 CryptoSession 타입
}

@Configuration
public class CryptoFactoryConfig {

    @Bean
    public CryptoSessionFactory<CryptoSessionType> cryptoSessionFactory() {
        Resource hotelConfig = new ClassPathResource(Path.of("crypto", "hotel", "config.json").toString());
        Resource airConfig = new ClassPathResource(Path.of("crypto", "air", "config.json").toString());
        List<CryptoSessionInit<CryptoSessionType>> cryptoSessionInits = List.of(
                // HOTEL CryptoSession 생성
                CryptoSessionInit.ofPath(CryptoSessionType.HOTEL, hotelConfig.getFile().getPath()),
                // AIR CryptoSession 생성
                CryptoSessionInit.ofPath(CryptoSessionType.AIR, airConfig.getFile().getPath())
        );
        return new CryptoSessionFactory<>(cryptoSessionInits);
    }

}
```


### 📌 `CryptoSessionFactory<T extends Enum<T>>` 사용 방법

- 사용 예제 코드: [${proejctDir}/src/test/java/com/freelife/factory/CryptoFactoryConfigTest.java](src/test/java/com/freelife/factory/CryptoFactoryConfigTest.java)

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CryptoFactoryConfigTest {

    // @Autowired: 필드 타입을 기준으로 빈을 찾음
    // @Resource: 필드 이름을 기준으로 빈을 찾음
    // 지정된 bean 이름으로 사용하기 위해서 `@Resource` 어노테이션을 사용하여 Bean을 찾아 사용할 수 있다
    @Resource
    private CryptoSessionFactory<CryptoSessionType> cryptoSessionFactory;

    @Test
    void cryptoSessionFactoryTest() {
        String plaintext = "Hello Crypto!";
        // HOTEL CryptoSession 사용
        CryptoSession hotelCryptoSession = cryptoSessionFactory.of(CryptoSessionType.HOTEL);
        // AIR CryptoSession 사용
        CryptoSession airCryptoSession = cryptoSessionFactory.of(CryptoSessionType.AIR);
        // HOTEL CryptoSession 암복호화
        String encryptHotel = hotelCryptoSession.encrypt(plaintext);
        String decryptHotel = hotelCryptoSession.decrypt(encryptHotel);
        // HOTEL CryptoSession Hash
        String hashHotel = hotelCryptoSession.encrypt_id(plaintext, 400);
        // AIR CryptoSession 암복호화
        String encryptAir = airCryptoSession.encrypt(plaintext);
        String decryptAir = airCryptoSession.decrypt(encryptAir);
        // Air CryptoSession Hash
        String hashAir = airCryptoSession.encrypt_id(plaintext, 400);
    }
}
```


## 🚦 3. `CryptoSessionFactoryBean<T extends Enum<T>>` 클래스 구현 및 설정 방법

---

`CryptoSessionFactoryBean<T extends Enum<T>>` 는 **SpringFramework** 의 `AbstractFactoryBean<T>` 추상 클래스를 상속받아
쉽게 **FactoryBean** 구현할 수 있도록 도와주는 클래스이다

**Crypto 암복호화 라이브러리**에서는 종속성 문제로 인해 직접 제공하지 않고 사용을 위해서는 별도의 구성이 필요하다

자세한 사용법은 아래의 코드를 참고하기 바란다


### 📌 `CryptoSessionFactoryBean<T extends Enum<T>>` 구성 및 `CryptoSession` 다중 `Bean` 설정 방법

- `CryptoSessionFactoryBean` 구현 예시 코드: [${proejctDir}/src/main/java/com/freelife/factory/factorybean/CryptoSessionFactoryBean.java](src/main/java/com/freelife/factory/factorybean/CryptoSessionFactoryBean.java)
- `CryptoSessions` 구현 예시 코드: [${proejctDir}/src/main/java/com/freelife/factory/factorybean/CryptoSessions.java](src/main/java/com/freelife/factory/factorybean/CryptoSessions.java)
- 설정 예시 코드: [${proejctDir}/src/main/java/com/freelife/factory/factorybean/CryptoFactoryBeanConfig.java](src/main/java/com/freelife/factory/factorybean/CryptoFactoryBeanConfig.java)

`AbstractFactoryBean<T>` 추상 클래스를 상속받아 Crypto 객체 초기화를 위한 `CryptoSessionFactoryBean` 클래스를 구성

```java
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
        log.info("Initialize CryptoSession FactoryBean: {}: {}",cryptoSessionInit.getCryptoSessionType(),  cryptoSessionInit.getInitType());
        return CryptoSession.of(cryptoSessionInit);
    }

    public static <T extends Enum<T>> CryptoSessionFactoryBean<T> of(CryptoSessionInit<T> cryptoSessionInit) {
        if (cryptoSessionInit == null)
            throw new IllegalArgumentException("CryptoSessionInit must not be null");
        return new CryptoSessionFactoryBean<>(cryptoSessionInit);
    }

}
```

다중 `CryptoSession` 객체를 저장하기 위한 `CryptoSessions` 객체 생성

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoSessions<T extends Enum<T>> {
    private Map<T, CryptoSession> cryptoSessionMap = new HashMap<>();

    private CryptoSession hotelCryptoSession;
    private CryptoSession airCryptoSession;
}
```

`CryptoSessionFactoryBean` 클래스를 사용하여 `CryptoSessionFactory` Bean 과 `CryptoSessions` Bean 설정

```java
@Configuration
@RequiredArgsConstructor
public class CryptoFactoryBeanConfig {

    private final CryptoProperties cryptoProperties;

    /**
     * Hotel CryptoSession Bean 생성
     * FactoryBean 타입으로 리턴하지만 실제 사용시에는 CryptoSession 타입으로 사용
     * ex1: @Resource(name = "hotelCryptoSession") CryptoSession hotelCryptoSession;
     * ex2: @Autowired CryptoSession hotelCryptoSession;
     */
    @Bean
    public CryptoSessionFactoryBean<CryptoSessionType> hotelCryptoSession() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(cryptoProperties.getHotel().getPath());
        return CryptoSessionFactoryBean.of(
                CryptoSessionInit.ofInputStream(
                        CryptoSessionType.HOTEL,
                        classPathResource.getInputStream()));
    }

    /**
     * Air CryptoSession Bean 생성
     * FactoryBean 타입으로 리턴하지만 실제 사용시에는 CryptoSession 타입으로 사용
     * ex1: @Resource(name = "airCryptoSession") CryptoSession airCryptoSession;
     * ex2: @Autowired CryptoSession airCryptoSession;
     */
    @Bean
    public CryptoSessionFactoryBean<CryptoSessionType> airCryptoSession() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(cryptoProperties.getAir().getPath());
        return CryptoSessionFactoryBean.of(
                CryptoSessionInit.ofBytes(
                        CryptoSessionType.AIR,
                        classPathResource.getInputStream().readAllBytes()));
    }

    /**
     * 생성된 FactoryBean 을 CryptoSessions 에 등록하여 다중 세션을 관리
     */
    @Bean
    public CryptoSessions<CryptoSessionType> cryptoSessions() throws Exception {
        CryptoSessions<CryptoSessionType> cryptoSessions = new CryptoSessions<>();
        cryptoSessions.setHotelCryptoSession(hotelCryptoSession().getObject());
        cryptoSessions.setAirCryptoSession(airCryptoSession().getObject());
        return cryptoSessions;
    }
}
```


### 📌 `CryptoSessionFactoryBean<T extends Enum<T>>` 사용 방법

- 사용 예제 코드: [${proejctDir}/src/test/java/com/freelife/factory/CryptoFactoryBeanConfigTest.java](src/test/java/com/freelife/config/CryptoFactoryBeanConfigTest.java)

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CryptoFactoryBeanConfigTest {

    @Resource
    private CryptoSession hotelCryptoSession;

    @Resource
    private CryptoSession airCryptoSession;

    @Resource
    private CryptoSessions<CryptoSessionType> cryptoSessions;

    @Test
    void CryptoFactoryBeanHotelTest() {
        String plaintext = "Hello Crypto!";
        // HOTEL CryptoSession(FactoryBean) 사용
        String encrypt = hotelCryptoSession.encrypt(plaintext);
        String decrypt = hotelCryptoSession.decrypt(encrypt);
        // Hash
        String hash = hotelCryptoSession.encrypt_id(plaintext, 400);
    }

    @Test
    void CryptoFactoryBeanAirTest() {
        String plaintext = "Hello Crypto!";
        // AIR CryptoSession(FactoryBean) 사용
        String encrypt = airCryptoSession.encrypt(plaintext);
        String decrypt = airCryptoSession.decrypt(encrypt);
        // Hash
        String hash = airCryptoSession.encrypt_id(plaintext, 400);
    }

    @Test
    void cryptoSessionsTest() {
        String plaintext = "Hello Crypto!";
        // HOTEL CryptoSession(CryptoSessions) 사용
        String encryptHotel = cryptoSessions.getHotelCryptoSession().encrypt(plaintext);
        String decryptHotel = cryptoSessions.getHotelCryptoSession().decrypt(encryptHotel);
        // Hash
        String hashHotel = cryptoSessions.getHotelCryptoSession().encrypt_id(plaintext, 400);
        // AIR CryptoSession(CryptoSessions) 사용
        String encryptAir = cryptoSessions.getAirCryptoSession().encrypt(plaintext);
        String decryptAir = cryptoSessions.getAirCryptoSession().decrypt(encryptAir);
        // Hash
        String hashAir = cryptoSessions.getAirCryptoSession().encrypt_id(plaintext, 400);
    }
}
```