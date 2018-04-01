package com.farelcigar.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * Spring Security Http Session Configuration for session based security
 */
@Configuration
@EnableWebSecurity
@Import({
        AuthenticationHandlers.class,
})
public class SessionSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final AuthenticationSuccessHandler successHandler;

    private final AuthenticationFailureHandler failureHandler;

    private final LogoutSuccessHandler logoutSuccessHandler;

    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(SessionSecurityConfiguration.class);

    @Autowired
    public SessionSecurityConfiguration(
            UserDetailsService userDetailsService,
            AuthenticationEntryPoint authenticationEntryPoint,
            AuthenticationSuccessHandler successHandler,
            AuthenticationFailureHandler failureHandler,
            LogoutSuccessHandler logoutSuccessHandler,
            PasswordEncoder passwordEncoder) {

        logger.debug("Configuring Spring Session Security Configuration");
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.debug("Configuring HttpSecurity");

        http.csrf().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);

        http.formLogin()
                .loginProcessingUrl("/api/login")
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
                .headers()
                .frameOptions()
                .disable();

        http.authorizeRequests()
                .antMatchers("/api/public*//**//**")
                .permitAll();

        http.authorizeRequests()
                .antMatchers("/api/admin*//**//**")
                .authenticated();
    }
}
