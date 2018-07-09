package com.valid.demo.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/kapcha","/reg","/css/*","/js/*")//不会拦截的资源
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/reg").permitAll().and().logout().permitAll();//被拦截后的跳转路径
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder().username("name").password("password").roles("USER").build();

        return new InMemoryUserDetailsManager(user);
    }
}
//TODO:身份认证待解决，注册完成依旧返回登陆界面