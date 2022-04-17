package com.vadim.sneakerstore.config;

import com.vadim.sneakerstore.entity.enums.Permission;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(@Qualifier("customerDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()

//                    .antMatchers(HttpMethod.GET, "/**").permitAll()
//                    .antMatchers(HttpMethod.POST, "/api/**").permitAll()
//                    .antMatchers(HttpMethod.PUT, "/api/**").permitAll()
//                    .antMatchers(HttpMethod.DELETE, "/api/**").permitAll()


                .antMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-ui.html/**").permitAll()
                .antMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()

                .antMatchers(HttpMethod.GET, "/api/customers/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/cards/**").hasAuthority(Permission.WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/api/orders/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/photo/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/sizes/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/comments").permitAll()
                .antMatchers(HttpMethod.GET, "/api/addresses").permitAll()

                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/register").permitAll()
                //.antMatchers(HttpMethod.POST, "/api/customers").hasAuthority(Permission.WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/api/customers").permitAll()
                .antMatchers(HttpMethod.POST, "/api/comments").permitAll()
                .antMatchers(HttpMethod.POST, "/api/cards").authenticated()
                .antMatchers(HttpMethod.POST, "/api/forgot_password").permitAll()
                .antMatchers(HttpMethod.POST, "/api/reset_password").permitAll()
                .antMatchers(HttpMethod.POST, "/api/photos").hasAuthority(Permission.WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/api/sizes").hasAuthority(Permission.WRITE.getPermission())
             //   .antMatchers(HttpMethod.POST, "/api/products").hasAuthority(Permission.WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/api/products/**").hasAuthority(Permission.WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/api/orders").permitAll()
                .antMatchers(HttpMethod.POST, "/api/addresses").authenticated()

                .antMatchers(HttpMethod.PUT, "/api/change_password").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/cards").hasAuthority(Permission.READ.getPermission())
                .antMatchers(HttpMethod.PUT, "/api/comments").hasAuthority(Permission.READ.getPermission())
                .antMatchers(HttpMethod.PUT, "/api/customers").hasAuthority(Permission.READ.getPermission())
                .antMatchers(HttpMethod.PUT, "/api/orders").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/photos").authenticated()
                .antMatchers(HttpMethod.PUT, "/api/products").hasAuthority(Permission.UPDATE.getPermission())
                .antMatchers(HttpMethod.PUT, "/api/sizes").hasAuthority(Permission.UPDATE.getPermission())
                .antMatchers(HttpMethod.PUT, "/api/addresses").authenticated()

                .antMatchers(HttpMethod.DELETE, "/api/**").permitAll()
                .antMatchers(HttpMethod.GET, "/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST, "/h2-console/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
               // .and()
               // .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
              //  .oauth2Login()
               // .and()
              //  .oauth2Client();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    // oauth2/authorization/google
}
