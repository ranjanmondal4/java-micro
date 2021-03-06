package com.micro.zuul.configuration;

import com.micro.zuul.domain.RedisUserRole;
import com.micro.zuul.repo.RedisUserRoleRepo;
import com.micro.zuul.repo.RedisUserRoleTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MongoTemplate mongoTemplate;

//    @Autowired
//    RedisUserRoleTemplate redisUserRoleTemplate;
//
    @Autowired
    RedisUserRoleRepo redisUserRoleRepo;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().disable().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().anyRequest()
                .authenticated();

        http.addFilterBefore(new AuthenticationFilter(mongoTemplate, redisUserRoleRepo, jwtTokenProvider), BasicAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                "/configuration/security", "/swagger-ui.html", "/webjars/**", "/static/**");

        web.ignoring().antMatchers("/hystrix/images/*");
        web.ignoring().antMatchers("/hystrix");

        web.ignoring().antMatchers("/api/v1/user/register", "/api/v1/user/login");
        web.ignoring().antMatchers("/api/v1/admin/register", "/api/v1/admin/login");

        web.ignoring().antMatchers("/*/")
                .antMatchers("/eureka/**")
                .antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
