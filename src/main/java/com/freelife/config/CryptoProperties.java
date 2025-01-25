package com.freelife.config;

import com.freelife.crypto.core.CryptoException;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Crypto Configuration
 * application.yml 에서 crypto 프로퍼티를 읽어서 설정값을 가지고 있는 클래스
 * Created by mskwon on 2024. 10. 21..
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "crypto")
public class CryptoProperties {

    /**
     * Default Config
     */
    private String path;
    private String awsKmsKeyArn;
    private String awsAccessKeyId;
    private String awsSecretAccessKey;
    private String key;
    private String iv;
    private String seed;
    private String credential;
    /**
     * Hotel Config
     */
    private hotel hotel;
    /**
     * Air Config
     */
    private air air;

    @Data
    public final static class hotel {
        private String path;
        private String awsKmsKeyArn;
        private String awsAccessKeyId;
        private String awsSecretAccessKey;
        private String key;
        private String iv;
        private String seed;
        private String credential;

        public String getPath() {
            return convertPath(path);
        }

        public Map<String, String> configLocalMap() {
            return toLocalMap(key, iv, seed, credential);
        }

        public Map<String, String> configMap() {
            return toMap(awsKmsKeyArn, awsAccessKeyId, awsSecretAccessKey, seed, credential);
        }
    }

    @Data
    public final static class air {
        private String path;
        private String awsKmsKeyArn;
        private String awsAccessKeyId;
        private String awsSecretAccessKey;
        private String key;
        private String iv;
        private String seed;
        private String credential;

        public String getPath() {
            return convertPath(path);
        }

        public Map<String, String> configLocalMap() {
            return toLocalMap(key, iv, seed, credential);
        }

        public Map<String, String> configMap() {
            return toMap(awsKmsKeyArn, awsAccessKeyId, awsSecretAccessKey, seed, credential);
        }
    }

    public String getPath() {
        return convertPath(path);
    }

    public Map<String, String> configLocalMap() {
        return toLocalMap(key, iv, seed, credential);
    }

    public Map<String, String> configMap() {
        return toMap(awsKmsKeyArn, awsAccessKeyId, awsSecretAccessKey, seed, credential);
    }

    private static String convertPath(String path) {
        if (StringUtils.isNotBlank(path)) {
            return Path.of(path).toString();
        }
        return path;
    }

    private static Map<String, String> toLocalMap(String key, String iv, String seed, String credential) {
        List<String> errors = new ArrayList<>();
        if (key == null || key.isBlank()) errors.add("key");
        if (iv == null || iv.isBlank()) errors.add("iv");
        if (seed == null || seed.isBlank()) errors.add("seed");
        if (credential == null || credential.isBlank()) errors.add("credential");
        if (!errors.isEmpty())
            throw new CryptoException("The following parameters are required: " + errors);
        return Map.of(
                "key", key,
                "iv", iv,
                "seed", seed,
                "credential", credential);
    }

    private static Map<String, String> toMap(String awsKmsKeyArn, String awsAccessKeyId, String awsSecretAccessKey, String seed, String credential) {
        List<String> errors = new ArrayList<>();
        if (awsKmsKeyArn == null || awsKmsKeyArn.isBlank()) errors.add("aws_kms_key_arn");
        if (awsAccessKeyId == null || awsAccessKeyId.isBlank()) errors.add("aws_access_key_id");
        if (awsSecretAccessKey == null || awsSecretAccessKey.isBlank()) errors.add("aws_secret_access_key");
        if (seed == null || seed.isBlank()) errors.add("seed");
        if (credential == null || credential.isBlank()) errors.add("credential");
        if (!errors.isEmpty())
            throw new CryptoException("The following parameters are required: " + errors);
        return Map.of(
                "aws_kms_key_arn", awsKmsKeyArn,
                "aws_access_key_id", awsAccessKeyId,
                "aws_secret_access_key", awsSecretAccessKey,
                "seed", seed,
                "credential", credential);
    }

    @PostConstruct
    public void init() {
        log.info("CryptoProperties: {}", this);
    }
}
