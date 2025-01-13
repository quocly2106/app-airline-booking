package com.airline.airlinebooking.config;

import com.airline.airlinebooking.sercurity.JWTAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    private final JWTAuthFilter jwtAuthFilter;

    public SecurityConfig(JWTAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/register","/customers/register", "/auth/login").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/staffs/update/{id}","/staffs/{id}","/staffs/{id}/change-password","/staffs/all", "/customers/all").hasAnyRole("ADMIN","STAFF")
                        .requestMatchers("/customers/update/{id}","/customers/{id}","/customers/{id}/change-password").permitAll()
                        .requestMatchers("/services/all").permitAll()
                        .requestMatchers("/feedbacks/all").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/news/add", "/news/update/", "news/delete/{id}").hasAnyRole("ADMIN")
                        .requestMatchers("/news/all", "/news/{id}", "/news/increment-views/{id}").permitAll()
                        .requestMatchers("/staffs/**","services/**").hasRole("ADMIN")
                        .requestMatchers("/feedbacks/**").hasAnyRole("CUSTOMER")
                        .requestMatchers("/customers/**","/planes/**").hasAnyRole("ADMIN","STAFF")
                        .requestMatchers("/seats/add", "/seats/delete/{id}", "/seats/update/{seatId}/status/{status}","seats/delete/flight/{flightId}").hasRole("ADMIN")
                        .requestMatchers("/seats/all", "/seats/{id}", "/seats/status/{status}", "/seats/type/{type}", "/seats/flight/{flightId}").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/airports/add","/airports//delete/{id}","/airports/update/{id}").hasRole("ADMIN")
                        .requestMatchers("/airports/search", "/airports/update/{id}/status", "/airports/status").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers("/seats/cancel/{seatId}").hasAnyRole("ADMIN", "STAFF", "CUSTOMER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Không sử dụng session
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);  // Thêm JWTAuthFilter trước UsernamePasswordAuthenticationFilter

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();  // Cung cấp AuthenticationManager
    }

//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Chỉ cho phép origin này
//        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(List.of("*")); // Hoặc chỉ định các header cụ thể
//        configuration.setAllowCredentials(true); // Nếu bạn cần gửi cookie
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}
