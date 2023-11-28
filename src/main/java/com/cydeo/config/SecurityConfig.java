package com.cydeo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    /**
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){

        //we want to create more than 1 user
        List<UserDetails> userList = new ArrayList<>();
        userList.add(
        new User("mike",encoder.encode("password"),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))));

        userList.add( new User("ozzy",encoder.encode("password"),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER"))));

       return new InMemoryUserDetailsManager(userList);

    }
    */

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .authorizeRequests()
                //.antMatchers("/user/**").hasRole("Admin")  //ROLE_ADMIN
                .antMatchers("/user/**").hasAuthority("Admin")
                .antMatchers("/project/**").hasAuthority("Manager")
                .antMatchers("/task/employee/**").hasAuthority("Employee")
                .antMatchers("/task/**").hasAuthority("Manager")
               //.antMatchers("/task/**").hasAnyRole("EMPLOYEE","ADMIN") //multiple ppl can have an access for this end points
               //.antMatchers("/task/**").hasAuthority("ROLE_EMPLOYEE")
                .antMatchers(
                        "/",
                        "/login",
                        "/fragments/**",
                        "/assets/**",
                        "/images/**"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
               //.httpBasic() //we don't want to use httpBasic() box
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/welcome")
                    .failureUrl("/login?error=true")
                    .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .and().build();
    }


}
