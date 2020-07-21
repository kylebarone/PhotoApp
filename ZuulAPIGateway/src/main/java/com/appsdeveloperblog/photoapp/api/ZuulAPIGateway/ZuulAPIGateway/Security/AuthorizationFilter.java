package com.appsdeveloperblog.photoapp.api.ZuulAPIGateway.ZuulAPIGateway.Security;


import io.jsonwebtoken.Jwts;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


public class AuthorizationFilter extends BasicAuthenticationFilter {

    Environment environment;

    public AuthorizationFilter(AuthenticationManager authenticationManager, Environment environment) {
        super(authenticationManager);
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String authorizationToken = request.getHeader(environment.getProperty("authorization.token.header.name"));

        if (authorizationToken == null || !authorizationToken.startsWith(environment.getProperty("authorization.token.header.prefix"))) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(environment.getProperty("authorization.token.header.name"));

        if (authorizationHeader == null) {
            return null;
        }

        String token = authorizationHeader.replace(environment.getProperty("authorization.token.header.prefix"), "");

        //token can contain more info than just a userI
        String userId = Jwts.parser()
                .setSigningKey(environment.getProperty("token.secret"))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        if (userId == null) {
            return null;
        }
        return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
    }
}
