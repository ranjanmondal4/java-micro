package com.micro.zuul.configuration;

import com.micro.zuul.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
import java.util.List;

@Slf4j
public class AuthenticationFilter extends GenericFilterBean {

    private MongoTemplate mongoTemplate;

    public AuthenticationFilter(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getHeader("Authorization");
//        log.info("Token {}", token);

        if(!StringUtils.isEmpty(token)){
            Query query = new Query();
            query.addCriteria(Criteria.where("token").is(token));
            List<User> users = mongoTemplate.find(query, User.class);
            if(!users.isEmpty()){
                String savedToken = users.get(0).getToken();
                if(token.equals(savedToken)){
                    UserAuthentication authentication = new UserAuthentication(users.get(0));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
