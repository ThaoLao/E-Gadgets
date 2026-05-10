package com.thuc.giadung.config;

import com.thuc.giadung.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Phân luồng thông báo lỗi đăng nhập theo testcase (email không tồn tại vs sai mật khẩu).
 */
@Component
@RequiredArgsConstructor
public class LoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        String raw = request.getParameter("username");
        String email = raw == null ? "" : raw.trim();
        if (email.isEmpty()) {
            getRedirectStrategy().sendRedirect(request, response,
                    request.getContextPath() + "/login?error=required");
            return;
        }
        if (userRepository.findByEmail(email) == null) {
            getRedirectStrategy().sendRedirect(request, response,
                    request.getContextPath() + "/login?error=user_not_found");
        } else {
            getRedirectStrategy().sendRedirect(request, response,
                    request.getContextPath() + "/login?error=bad_credentials");
        }
    }
}
