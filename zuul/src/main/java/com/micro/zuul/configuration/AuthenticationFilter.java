package com.micro.zuul.configuration;

import com.micro.zuul.domain.RedisUserRole;
import com.micro.zuul.repo.RedisUserRoleRepo;
import com.micro.zuul.repo.RedisUserRoleTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class AuthenticationFilter extends GenericFilterBean {

    private MongoTemplate mongoTemplate;
//    private RedisUserRoleTemplate redisUserRoleTemplate;
    private RedisUserRoleRepo redisRepo;

    private JwtTokenProvider jwtTokenProvider;

    public AuthenticationFilter(MongoTemplate mongoTemplate, RedisUserRoleRepo redisRepo, JwtTokenProvider jwtTokenProvider){
        this.mongoTemplate = mongoTemplate;
        this.redisRepo = redisRepo;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getHeader("Authorization");
//        log.info("In Authentication filter, Token {}", token);

        if(!StringUtils.isEmpty(token)){
            // for admin only - JWT
            if(token.contains("Bearer")){
                token = jwtTokenProvider.removeToken(token);
                if(jwtTokenProvider.validateToken(token)){
                    RedisUserRole userRole = jwtTokenProvider.getUser(token);
                    UserAuthentication authentication = new UserAuthentication(userRole);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            // for user only - custom token created by user service
            else {
                RedisUserRole userRole = redisRepo.findByToken(token);
//                log.info("User role {}", userRole);
                if(!Objects.isNull(userRole)){
                    UserAuthentication authentication = new UserAuthentication(userRole);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
