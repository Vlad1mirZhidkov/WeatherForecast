package com.example.demo.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    // Обрабатывает ошибки аунтефикации
    private final JwtAuthEntryPoint jwtAuthEntryPoint;

    // Загружает данные пользователя
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Отключает защиту
                .csrf().disable()
                .exceptionHandling()
                // Настраивает обработку исключений
                .authenticationEntryPoint(jwtAuthEntryPoint)
                .and()
                .sessionManagement()
                // Политика создания сессий без сохранения на сервере
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                // Разрешает доступ без аутентификации
                .requestMatchers("/api/auth/**",
                        "/swagger-ui/**", "/v3/api-docs/**", "/docs/**").permitAll()
                // Доступ определенным ролям
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                // Все остальные требуют аутентификации
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        // Добавляет фильтр jwtAuthFilter перед Username... фильтром
        http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // Возрвращает AuthenticationManager для аутентификации
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Возвращает BCryptPasswordEncoder для хеширования паролей
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Возвращает экземпляр JwtAuthFilter для обработки JWT токенов
    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter();
    }
}
