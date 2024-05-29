package com.scm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.scm.services.SecurityCustomUserDetailsService;

@Configuration
public class SecurityConfig {

    @Autowired
    private OAuthenticationSuccesHandler oSuccesHandler;

    //create user and login using java code within memory service
    // @Bean
    // public UserDetailsService userDetailsService(){

    //    UserDetails user1 = User
    //    .withDefaultPasswordEncoder()
    //    .username("admin123")
    //    .password("admin123")
    //    .roles("ADMIN","NORMAL")
    //    .build();

    //   UserDetails user2= User
    //   .withDefaultPasswordEncoder()
    //    .username("user123")
    //    .password("user123")
    //    //.roles("")
    //    .build();
    //     var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1,user2);

    //     return inMemoryUserDetailsManager;

    // }

    //Configuration of Authentication provider for spring security 
     @Autowired
    private SecurityCustomUserDetailsService userDetailsService;
    @Bean
    public AuthenticationProvider authenticationProvider(){
       DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
       //creating userdetailsservice object
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        //creating encoder password object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        //cofiguration
        //url configure kiya hai kon sa public urls rahennge aur kon se private url rahenge
        httpSecurity.authorizeHttpRequests(authorize->{
            //authorize.requestMatchers("/home","/signup").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        //login form default
        //if we want to change any thing we need to come here:: related form login
        // httpSecurity.formLogin(Customizer.withDefaults());

        //we created custom login page not default login page by spring 
        httpSecurity.formLogin(formLogin->{
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
          //  formLogin.successForwardUrl("/user/dashboard");
          //  formLogin.failureForwardUrl("/login?error=true");
          //  formLogin.successForwardUrl("/user/profile");
            formLogin.defaultSuccessUrl("/user/profile");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
            
            // formLogin.failureHandler(new AuthenticationFailureHandler() {

            //     @Override
            //     public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            //             AuthenticationException exception) throws IOException, ServletException {
            //         // TODO Auto-generated method stub
            //         throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'");
            //     }
                
            // });

            // formLogin.successHandler(new AuthenticationSuccessHandler() {

            //     @Override
            //     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            //             Authentication authentication) throws IOException, ServletException {
            //         // TODO Auto-generated method stub
            //         throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
            //     }
                
            // });

        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        

        //oauth2 configuration
        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");

        
            oauth.successHandler(oSuccesHandler);
        });

        httpSecurity.logout(logoutForm->{

            logoutForm.logoutUrl("/do-logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        
      //  httpSecurity.oauth2Login(Customizer.withDefaults());

        

        return httpSecurity.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

}
