package com.med.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.med.services.impl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityCustomUserDetailService userDetailService;
    /* here it uses userDetails which contain load user method then it comapare that load user with new user data if 
     there password matches it login successfully*/


     @Autowired
     private  OAuthAuthenicationSuccessHandler handler;


    // configuraiton of authentication providerfor spring security
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
 /*The DaoAuthenticationProvider in Spring Security is used to authenticate a user against a database using the 
   UserDetailsService and a password encoder. 
   
   when user enter using username and password it authenticate user here*/    

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    // user detail service ka object: it called SecurityCustomUserDetailService which implement UserDetailsService 
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
    // password encoder ka object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // configuration

        // urls configure kiay hai ki koun se public rangenge aur koun se private
        // rangenge
        httpSecurity.authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        // form default login
        // agar hame kuch bhi change karna hua to hama yaha ayenge: form login se
        // related
        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login"); //use this login perform
            formLogin.loginProcessingUrl("/authenticate"); //login form submit at this url for processing
            formLogin.successForwardUrl("/user/profile");
         //in login form name must be same i.e email and password   
            formLogin.usernameParameter("email"); 
            formLogin.passwordParameter("password");


        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        //oauth configurations

        httpSecurity.oauth2Login(oauth -> {
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });

        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        return httpSecurity.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}