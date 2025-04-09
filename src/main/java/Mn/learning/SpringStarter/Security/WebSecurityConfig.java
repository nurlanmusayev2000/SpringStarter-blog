package Mn.learning.SpringStarter.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import Mn.learning.SpringStarter.util.constants.Privilage;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] WHITELIST = {
            "/", "/login", "/register", "/db-console/**",
            "/css/**", "/fonts/**", "/images/**", "/js/**","/post/**","/forgot-password"
		};

		@Bean
		public static PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers(WHITELIST).permitAll()
                .requestMatchers("/profile/**").authenticated()
                .requestMatchers("/posts/add").authenticated()
                .requestMatchers("/admin/**").hasAuthority(Privilage.ACCESS_ADMIN_PANEL.getPrivilage())
                .requestMatchers("/editor/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_EDITOR")
              )
            .formLogin(form -> form
								.loginPage("/login")
								.loginProcessingUrl("/login")
								.usernameParameter("username")
								.passwordParameter("password")
								.defaultSuccessUrl("/", true)
								.failureUrl("/login?error")
								.permitAll()
            )
            .rememberMe(remember -> remember
                .key("uniqueAndSecretKey")
                .tokenValiditySeconds(1209600) // 14 days
                .rememberMeParameter("remember-me") // name of checkbox input
            )
						.logout(logout -> logout
								.logoutUrl("/logout")
								.logoutSuccessUrl("/")
                .permitAll()
            )

            .headers(headers -> headers
                .frameOptions(frame -> frame.disable())
						);


        return http.build();
    }
}
