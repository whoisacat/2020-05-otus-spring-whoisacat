package com.whoisacat.edu.book.springsecurityini.catalogue.security;

import com.whoisacat.edu.book.springsecurityini.catalogue.service.WHOUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final int ONE_DAY_SECONDS = 60 * 60 * 24;

    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(WHOUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/auth");
    }

    @Override protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            .and().authorizeRequests().antMatchers("/edit","/delete","/addBook").hasRole("ADMIN")
            .and().authorizeRequests().antMatchers("/**").hasRole("USER")
            .and().formLogin()
            .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .and().rememberMe().key("whoChatSecret").tokenValiditySeconds(ONE_DAY_SECONDS);
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService);
    }
    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(this.userDetailsService);
        return provider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder(13);
        return encoder;
    }
}
