package com.freelife.basic;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * ClassPathResource Test
 * Created by mskwon on 2024. 10. 22..
 */
public class CryptoPathTest {

    @Test
    void testResource() throws IOException {
        Resource resource =  new ClassPathResource(Path.of("crypto", "hotel", "config.json").toString());
        System.out.println("test resource: " + resource.getFile().getAbsolutePath());
        // /Users/freelife/company/crypto/crypto-springboot-maven-example/target/classes/crypto/hotel/config.json
        System.out.println(Path.of("crypto", "hotel", "config.json").toAbsolutePath().toString());
        // /Users/freelife/company/crypto/crypto-springboot-maven-example/crypto/hotel/config.json
        Path resourcePath = Path.of("src","main", "resources","crypto", "config.json");
        System.out.println(resourcePath.toAbsolutePath() +" : "+ Files.exists(resourcePath.toAbsolutePath()));
        // /Users/freelife/company/crypto/crypto-springboot-maven-example/resources/crypto/config.json
        System.out.println(resourcePath + " : " + Files.exists(resourcePath));
        // build/resources/crypto/hotel/config.json
        // out/production/resources/crypto/hotel/config.json
        // target/classes/crypto/hotel/config.json
    }
}
