package com.appsdeveloperblog.photoapp.api.ZuulAPIGateway.ZuulAPIGateway.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.core.env.Environment;

@Configuration
@EnableWebSecurity
public class WebSecurity  extends WebSecurityConfigurerAdapter {

    private final Environment environment;

    @Autowired
    public WebSecurity(Environment environment) {
        this.environment = environment;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.headers().frameOptions().disable();

        //this restricts the endpoint acess
        //this is enabling endpoints that dont require authorization header
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, environment.getProperty("api.users-ws.login.path")).permitAll()
                .antMatchers(HttpMethod.POST, environment.getProperty("api.users-ws.registration.path")).permitAll()
                .antMatchers(HttpMethod.GET, environment.getProperty("api.users-ws.status.path")).permitAll()
                .antMatchers(environment.getProperty("api.users-ws.console.path")).permitAll()
                .antMatchers(environment.getProperty("api.zuul.actuator.url.path")).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new AuthorizationFilter(authenticationManager(), environment));

        //telling spring not to create a http session that caches cookies that might mess up how our shit is authorized
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
