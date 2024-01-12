package custom.capstone.global.config;

import custom.capstone.global.config.jwt.JwtAuthenticationFilter;
import custom.capstone.global.config.jwt.JwtTokenProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtTokenProvider jwtTokenProvider;

    public JwtSecurityConfig(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Security 로직에 JwtFilter 등록
     */
    @Override
    public void configure(final HttpSecurity http) {
        final JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtTokenProvider);

        http
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

