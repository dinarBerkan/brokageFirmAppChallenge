package com.challenge.brokagefirmapp;

import com.challenge.brokagefirmapp.service.ApplicationAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {
    //private final ApplicationAuthenticationProvider applicationAuthenticationProvider;

    /*public SecurityConfig(ApplicationAuthenticationProvider applicationAuthenticationProvider) {
        this.applicationAuthenticationProvider = applicationAuthenticationProvider;
    }*/

    @Bean
    UserDetailsService userDetailsService() {
        var user = User.withUsername("adminUser").password("adminPassword").authorities("ROLE_ADMIN").build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    /*@Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());
        http.authenticationProvider(applicationAuthenticationProvider);
        http.authorizeHttpRequests(c -> c.anyRequest().authenticated());
        return http.build();
    }*/
}
