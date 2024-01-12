package custom.capstone.global.config;

import custom.capstone.global.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 정적 자원에는 Security 를 적용하지 않음
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/api-docs", "/swagger*/**", "/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .httpBasic().disable()
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()

                // 모든 접근 허용
                .antMatchers(HttpMethod.POST, "/api/members/login", "/api/members/join", "/ws/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/posts/**", "/api/notice/**", "/api/magazine/**", "/api/inquires/**", "/ws/**").permitAll()

                // 관리자만 접근 가능 ** 추후
                .antMatchers(HttpMethod.POST,  "/api/notice/**", "/api/magazine/**", "/api/inquires/**").permitAll()
                .antMatchers(HttpMethod.PATCH, "/api/categories/**", "/api/notice/**", "/api/magazine/**", "/api/inquires/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/categories/**", "/api/notice/**", "/api/magazine/**", "/api/inquires/**").permitAll()

                // 회원만 접근 가능
                .antMatchers(HttpMethod.POST, "/api/members/**", "/api/posts/**", "/api/interests/**", "/api/trading/**", "/api/reviews/**", "/api/inquires/{inquiryId}").hasRole("GENERAL")
                .antMatchers(HttpMethod.PATCH, "/api/members/**", "/api/posts/**", "/api/interests/**", "/api/trading/**", "/api/reviews/**", "/api/inquires", "/api/inquires/{inquiryId}").hasRole("GENERAL")
                .antMatchers(HttpMethod.DELETE, "/api/members/**", "/api/posts/**", "/api/interests/**", "/api/trading/**", "/api/reviews/**", "/api/inquires/{inquiryId}").hasRole("GENERAL")

                // 채팅
                .antMatchers(HttpMethod.POST, "/chat/**").hasRole("GENERAL")
                .antMatchers(HttpMethod.GET, "/chat/**").hasRole("GENERAL")

                // 그 외는 인증 요청
                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfig(jwtTokenProvider));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
