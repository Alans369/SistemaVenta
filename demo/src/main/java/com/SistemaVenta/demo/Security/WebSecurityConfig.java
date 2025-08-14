package com.SistemaVenta.demo.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;





@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


	

    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
        .csrf(csrf -> csrf.disable())
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests((requests) -> requests
				// Recursos estáticos
				.requestMatchers("/css/*", "/js/*", "/images/*").permitAll()
				.requestMatchers("/static/**").permitAll()
				.requestMatchers("/webjars/**").permitAll()
				// Rutas públicas específicas
				.requestMatchers("/register", "/access-denied", "/logout", "/login", "/save","layout","/login1").permitAll()
				.requestMatchers("/").permitAll()
				// Rutas protegidas por roles
				.requestMatchers("/admin/**").hasAnyAuthority("ROLE_VENDEDOR")
				.requestMatchers("/user/**").hasAnyAuthority("ROLE_CLIENTE")
				// Cualquier otra ruta requiere autenticación
				.anyRequest().authenticated()
			).
            addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(ex -> ex
            .accessDeniedHandler((request, response, accessDeniedException) -> {
                                response.sendRedirect("/access-denied");
            })
        )   .logout(logout -> logout.disable()); // 🔥 Deshabilita el logout automático
			

		return http.build();
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /** 
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }*/

    @Bean
	public AuthenticationManager authenticationManager(
			UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(authenticationProvider);
	}

	 



	

    

}
