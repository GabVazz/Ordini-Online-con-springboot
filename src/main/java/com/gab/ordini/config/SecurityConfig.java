package com.gab.ordini.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.gab.ordini.repository.UtenteRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private UtenteRepository utenteRepository;

	public SecurityConfig(UtenteRepository utenteRepository) {
		this.utenteRepository = utenteRepository;
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		// Configura il provider con il servizio di dettagli utente personalizzato
		authProvider.setUserDetailsService(new UtentiDetailsService(this.utenteRepository));
		// Configura il provider con il codificatore di password
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				// Configura le autorizzazioni delle richieste
				.authorizeHttpRequests(
						auth -> auth.requestMatchers("/admin/**").hasRole("ADMIN").anyRequest().permitAll())

				.formLogin(form -> form.loginPage("/loginAdmin").permitAll())

				.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logoutAdmin"))
						.logoutSuccessUrl("/admin/"))
				// Configura l'autenticazione di base HTTP
				.httpBasic(withDefaults());

		// Costruisce e ritorna l'oggetto HttpSecurity configurato
		return http.build();
	}

}
