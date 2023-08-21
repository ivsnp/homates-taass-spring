package com.homates.usermanager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private CustomUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // https://docs.spring.io/spring-security/reference/servlet/configuration/java.html
        // https://stackoverflow.com/questions/74753700/cannot-resolve-method-antmatchers-in-authorizationmanagerrequestmatcherregis

        http
                .csrf().disable()
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults());

        /*http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();

        /*http
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        //.requestMatchers(HttpMethod.GET).authenticated()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        //.requestMatchers("/vertretungsplan").hasAnyRole("SCHUELER", "LEHRER", "VERWALTUNG")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll)
                .httpBasic();*/

        return http.build();
    }

    @Bean
    public UserDetailsService users(){
        // TODO: do not use clear passwords
        UserDetails admin = User.builder()
                .username("admin")
                .password("password")
                .roles("ADMIN")
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

