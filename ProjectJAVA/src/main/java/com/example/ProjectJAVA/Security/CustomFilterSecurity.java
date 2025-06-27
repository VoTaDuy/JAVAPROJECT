package com.example.ProjectJAVA.Security;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class CustomFilterSecurity {

    @Autowired
    CustomUserDetailService customUserDetailService;
    @Autowired
    CustomJwtFilter customJwtFilter;
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();


    }
    @Bean
    public SecurityFilterChain customFilterSercurity(HttpSecurity http) throws Exception {

        http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request.requestMatchers(
                                "/user/**",
                                "/user/{userId}/delete_user",
                                "/login/sign_in/**",
                                "/login/Sign_up/**",
                                "/login/admin/sign_in/**",
                                "/category/get/**",
                                "/category/create/**",
                                "/category/delete/**",
                                "/product/get",
                                "/product/get/{product_id}",
                                "/product/create",
                                "/product/file/{filename:.+}",
                                "/cart/get/{id}/my-cart",
                                "/cart/delete/{id}/my-cart",
                                "/cart/{id}/cart/total-price",
                                "/cart/create/**",
                                "/cart/user/{userId}",
                                "cartItem/add/**",
                                "cartItem/{id}/remove/{itemId}",
                                "cartItem/cart/{id}/{itemId}/update/**",
                                "order/create/**",
                                "order/get/{orderId}",
                                "order/{userId}/get",
                                "payment/fake/**")
                        .permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}