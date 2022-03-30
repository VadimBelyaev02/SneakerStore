package com.vadim.sneakerstore.config;

import com.vadim.sneakerstore.entity.enums.Permission;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {//} implements WebMvcConfigurer{

    @Order(1)
    @Configuration
    public static class OAuth2Config extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .requestMatchers()
                    .antMatchers(
                            "/oauth2/authorization/google",
                            "/api/customers/export",
                            "/api/customers/export/last",
                            "/login/oauth2/code/google/**")
                    .and()
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .oauth2Login();
        }
    }

    @Order(2)
    @Configuration
    public static class BasicConfig extends WebSecurityConfigurerAdapter {

        private final UserDetailsService userDetailsService;

        public BasicConfig(@Qualifier("customerDetailsServiceImpl") UserDetailsService userDetailsService) {
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
                    // .antMatchers(HttpMethod.GET, "/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/swagger-ui.html/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()

                    .antMatchers(HttpMethod.GET, "/api/customers/**").authenticated()
                    .antMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/cards/**").hasAuthority(Permission.WRITE.getPermission())
                    .antMatchers(HttpMethod.GET, "/api/orders/**").authenticated()
                    .antMatchers(HttpMethod.GET, "/api/photo/**").authenticated()
                    .antMatchers(HttpMethod.GET, "/api/sizes/**").authenticated()
                    .antMatchers(HttpMethod.GET, "/api/comments").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/addresses").authenticated()

                    .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/register").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/customers").hasAuthority(Permission.WRITE.getPermission())
                    .antMatchers(HttpMethod.POST, "/api/comments").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/cards").authenticated()
                    .antMatchers(HttpMethod.POST, "/api/forgot_password").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/reset_password").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/photos").hasAuthority(Permission.WRITE.getPermission())
                    .antMatchers(HttpMethod.POST, "/api/sizes").hasAuthority(Permission.WRITE.getPermission())
                    .antMatchers(HttpMethod.POST, "/api/products").hasAuthority(Permission.WRITE.getPermission())
                    //    .antMatchers(HttpMethod.POST, "/api/orders").hasAuthority(Permission.READ.getPermission())
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
                    //   .antMatchers(HttpMethod.GET, "/oauth2/authorization/google").permitAll()

                    .anyRequest()
                    .authenticated()
                    .and()
                    .httpBasic();
            //         .and()
//                .exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            //          .oauth2Login()
            //          .redirectionEndpoint()
            //          .baseUri("/api/customers");
            //      .and()
            //      .oauth2Client()
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

    }
        // oauth2/authorization/google

//    @Bean
//    public PrincipalExtractor principalExtractor(CustomerRepository repository) {
//        return map -> {
//            String email = (String) map.get("email");
//            Customer customerD = repository.findByEmail(email).orElseGet(() ->{
//                Customer customer = new Customer();
//                customer.setEmail(email);
//                customer.setFirstName((String) map.get("given_name"));
//                customer.setLastName((String) map.get("family_name"));
//
//                return customer;
//
//            });
//
//            repository.save(customerD); // incorrect
//
//            return customerD;
//        };
//    }

    }
