package pl.mati;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.session.web.http.SessionRepositoryFilter;
import pl.mati.sessionrepository.JPASessionRepository;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SessionRepositoryFilter<ExpiringSession> sessionSessionRepositoryFilter;

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new CookieHttpSessionStrategy();
    }

    @Bean
    public SessionRepositoryFilter<ExpiringSession> sessionRepositoryFilter(
            SessionRepository<ExpiringSession> sessionRepository,
            HttpSessionStrategy httpSessionStrategy
    ) {
        SessionRepositoryFilter<ExpiringSession> sessionRepositoryFilter = new SessionRepositoryFilter<>(sessionRepository);
        sessionRepositoryFilter.setHttpSessionStrategy(httpSessionStrategy);
        return sessionRepositoryFilter;
    }

    @Bean
    public SessionRepository<ExpiringSession> sessionRepository() {
        return new JPASessionRepository();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("mati").password("qwerty").roles("user").and()
                .withUser("mati2").password("qwerty").roles("user");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .logout().permitAll().and()
                .formLogin().permitAll().and()
                .addFilterBefore(this.sessionSessionRepositoryFilter, ChannelProcessingFilter.class)
                .authorizeRequests()
                    .anyRequest().authenticated();
    }
}
