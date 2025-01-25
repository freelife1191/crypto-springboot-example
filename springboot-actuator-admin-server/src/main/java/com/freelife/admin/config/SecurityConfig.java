package com.freelife.admin.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.web.client.HttpHeadersProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

import static com.freelife.admin.util.SecurityUtils.getClientIP;

/**
 * https://github.com/codecentric/spring-boot-admin/blob/master/spring-boot-admin-samples/spring-boot-admin-sample-servlet/src/main/java/de/codecentric/boot/admin/sample/SecuritySecureConfig.java
 */
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class SecurityConfig {

    private final AdminServerProperties adminServer;

    @Value("${ip.local.address}")
    private String myLocalIpAddress;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 로그인 성공 시 메인페이지로 redirect
        final SavedRequestAwareAuthenticationSuccessHandler loginSuccessHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        loginSuccessHandler.setTargetUrlParameter("redirectTo");
        loginSuccessHandler.setDefaultTargetUrl(this.adminServer.path("/"));

        http
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        // 로그인 페이지와 assets 리소스는 누구나 접근할 수 있도록 허용
                        .requestMatchers(this.adminServer.path("/assets/**")).permitAll()
                        .requestMatchers(this.adminServer.path("/assets/**")).permitAll()
                        // .requestMatchers(this.adminServer.path("/actuator/info")).permitAll()
                        // .requestMatchers(this.adminServer.path("/actuator/health")).permitAll()
                        .requestMatchers("/actuator/**").access(hasIpAddress(myLocalIpAddress))
                        .requestMatchers(
                                this.adminServer.path("/login"),
                                this.adminServer.path("/assets"),
                                this.adminServer.path("/actuator/info"),
                                this.adminServer.path("/actuator/health"))
                        .permitAll()
                        // 그 외에는 접근 권한이 필요함
                        .anyRequest().authenticated())
                // 로그인 URL 및 success handler 설정
                .formLogin((formLogin) -> formLogin
                        .loginPage(this.adminServer.path("/login"))
                        .successHandler(loginSuccessHandler))
                // 로그아웃 URL 설정
                .logout((logout) -> logout
                        .logoutUrl(this.adminServer.path("/logout")))
                // Client 등록을 위한 HTTP-Basic 지원 사용
                .httpBasic(Customizer.withDefaults())
                .csrf((csrf) -> csrf
                        // CSRF 토큰을 Cookie 기반으로 사용 (쿠키를 사용하여 CSRF 보호
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .ignoringRequestMatchers(
                                this.adminServer.path("/instances"),
                                this.adminServer.path("/actuator/**"))
                )
                ;

        return http.build();
    }

    private static AuthorizationManager<RequestAuthorizationContext> hasIpAddress(String ipAddress) {
        IpAddressMatcher ipAddressMatcher = new IpAddressMatcher(ipAddress);
        return (authentication, context) -> {
            HttpServletRequest request = context.getRequest();
            return new AuthorizationDecision(ipAddressMatcher.matches(getClientIP(request)));
        };
    }

    @Bean
    public HttpHeadersProvider customHttpHeadersProvider() {
        return (instance) -> {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("X-CUSTOM", "My Custom Value");
            return httpHeaders;
        };
    }
}
