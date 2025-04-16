package nhn.academy.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nhn.academy.service.LoginAttemptService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.UUID;


public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private LoginAttemptService loginAttemptService;
    private RedisTemplate<String, Object> sessionRedisTemplate;
    public CustomAuthenticationSuccessHandler(LoginAttemptService loginAttemptService, RedisTemplate<String, Object> sessionRedisTemplate) {
        this.loginAttemptService = loginAttemptService;
        this.sessionRedisTemplate = sessionRedisTemplate;

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        // 로그인 성공 시 추가 작업을 수행하는 로직
        String sessionId = UUID.randomUUID().toString();
        Cookie sessionCookie = new Cookie("SESSIONID", sessionId);
        sessionCookie.setHttpOnly(true); // 보안 설정
        sessionCookie.setMaxAge(60 * 60); // 쿠키 유효시간 (1시간)
        sessionCookie.setPath("/"); // 모든 경로에서 쿠키 접근 가능
        response.addCookie(sessionCookie);
        sessionRedisTemplate.opsForValue().set(sessionId, authentication.getName());

        loginAttemptService.success(authentication.getName());
        // 원래 요청한 URL로 리다이렉트
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
