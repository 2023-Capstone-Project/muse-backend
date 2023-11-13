package custom.capstone.global.config;

import custom.capstone.global.config.jwt.JwtAuthenticationFilter;
import custom.capstone.global.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .antMatchers("/api-docs", "/swagger*/**", "/h2-console/**", "/ws/**")
                .antMatchers(HttpMethod.POST, "/api/members/login", "/api/members/join")
                .antMatchers(HttpMethod.GET, "/api/posts/**", "/api/notice/**", "/api/magazine/**", "/api/inquires/**");
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

                // 모두 가능
                .antMatchers(HttpMethod.POST, "/api/members/join").permitAll()
                .antMatchers(HttpMethod.GET, "/api/posts/**", "/api/notice/**", "/api/magazine/**", "/api/inquires/**").permitAll()

                // 관리자만 접근 가능
                .antMatchers(HttpMethod.POST,  "/api/notice/**", "/api/magazine/**", "/api/inquires/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/categories/**", "/api/notice/**", "/api/magazine/**", "/api/inquires/**").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/categories/**", "/api/notice/**", "/api/magazine/**", "/api/inquires/**").hasAnyRole("ADMIN")

                // 회원만 접근 가능
                .antMatchers(HttpMethod.POST, "/api/members/**", "/api/posts/**", "/api/interests/**", "/api/trading/**", "/api/reviews/**", "/api/inquires/{inquiryId}").hasRole("GENERAL")
                .antMatchers(HttpMethod.PATCH, "/api/members/**", "/api/posts/**", "/api/interests/**", "/api/trading/**", "/api/reviews/**", "/api/inquires", "/api/inquires/{inquiryId}").hasRole("GENERAL")
                .antMatchers(HttpMethod.DELETE, "/api/members/**", "/api/posts/**", "/api/interests/**", "/api/trading/**", "/api/reviews/**", "/api/inquires/{inquiryId}").hasRole("GENERAL")

                // 그 외는 인증 요청
                .anyRequest().authenticated()

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }
}
