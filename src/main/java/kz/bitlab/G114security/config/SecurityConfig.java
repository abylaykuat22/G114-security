package kz.bitlab.G114security.config;

import kz.bitlab.G114security.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // активирует конфигурационный класс security
@EnableMethodSecurity // активирует возможность использовать аннотацию @PreAuthorize
public class SecurityConfig {

  @Bean
  public UserService userService() {
    return new UserService();
  }

  @Bean
  public PasswordEncoder passwordEncoder() { // чтобы работать с зашифрованными паролями
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    var authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

    authenticationManagerBuilder
        .userDetailsService(
            userService()) // security требует UserDetailsService -> в нашем случае, это UserService, который имплементится от интерфейса.
        .passwordEncoder(passwordEncoder()); // проверка пароля.

    http.csrf(AbstractHttpConfigurer::disable);

    http.formLogin(flc -> flc.loginProcessingUrl("/auth") // происходит проверка данных
        .usernameParameter("user_email") // данные почты введенные с фронта
        .passwordParameter("user_password") // данные пароля введенные с фронта
        .loginPage("/sign-in") // страница где происходит аутентификация
        .defaultSuccessUrl("/profile", true) // endpoint при успешной аутентификации, true - чтобы активировать
        .failureUrl("/sign-in?error")); // endpoint при не успешной аутентификации

    http.logout(logout -> logout.logoutUrl("/logout")
        .logoutSuccessUrl("/sign-in"));

    return http.build();
  }
}
