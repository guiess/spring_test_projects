package spring.hello_test.adapter.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;


@Configuration
@EnableWebSecurity
@ConditionalOnProperty(value="spring.hello_test.auth")
public class WebSecurityConfigAdapter  extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests().anyRequest().fullyAuthenticated().and().formLogin();
        System.out.println("FEPO WebSecurityConfigAdapter");
    }

    //ben/bob
    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception{
        builder.ldapAuthentication().userDnPatterns("uid={0},ou=people")
                .groupSearchBase("ou=groups")
                .contextSource().url("ldap://localhost:8389/dc=springframework,dc=org")
                .and()
                .passwordCompare().passwordEncoder(new LdapShaPasswordEncoder())
                .passwordAttribute("userPassword");
    }


}
