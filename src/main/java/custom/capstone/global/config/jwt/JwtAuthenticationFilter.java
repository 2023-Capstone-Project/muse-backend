package custom.capstone.global.config.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain
    ) throws ServletException, IOException {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        final String token = resolveToken(httpServletRequest);

        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            final Authentication authentication = jwtTokenProvider.getAuthentication(token);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    // Request 의 Header 에서 token 값을 가져온다. "Authorization" : "Bearer {TOKEN 값}'
    private String resolveToken(final HttpServletRequest request) {
        final String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);     // Bearer 를 제외한 TOKEN 값만 반환
        }

        return null;
    }
}
