package com.starLife.in.starLifeConfig;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.starLife.in.starLifeConfig.AdminLoginConfiguration.CustomAdminDetailsService;
import com.starLife.in.starLifeConfig.BankMitraConfiguration.CustomBankMitraDetailsService;
import com.starLife.in.starLifeConfig.CustomerConfiguration.CustomCustomerDetails;
import com.starLife.in.starLifeConfig.CustomerConfiguration.JwtValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class StarLifeBankConfig {


	@Autowired
	private CustomCustomerDetails getCustomerDetailService;
	

	@Autowired
	private CustomBankMitraDetailsService customBankMitraDetailsService;

	@Autowired
	private CustomAdminDetailsService customAdminDetailsService;


	@Autowired
	private CustomAuthenticationSuccessHandler successhandler;

	// create bean for password encryption

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		UrlBasedCorsConfigurationSource source = new
		UrlBasedCorsConfigurationSource();
		CorsConfiguration cfg = new CorsConfiguration();
		cfg.setAllowedOrigins(Arrays.asList(
		"http://localhost:9191",
		"http://localhost:5173"));
		cfg.setAllowCredentials(true);
		cfg.setAllowedHeaders(Collections.singletonList("*"));
		cfg.setAllowedMethods(Collections.singletonList("*"));
		cfg.setExposedHeaders(Arrays.asList("Authorization"));
		source.registerCorsConfiguration("/**", cfg);

		http
				.csrf(Customizer -> Customizer.disable())

				.addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
				.authorizeHttpRequests(request -> request
				.requestMatchers("/customer/**").hasRole("CUSTOMER")
				.requestMatchers("/bank_mitra/**").hasRole("BANKMITRA")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll()
				)
				

				.formLogin(login -> login
				.loginPage("/signin") // Default login page for Customers
				.loginProcessingUrl("/dologin")  // Common login processing URL
				.successHandler(successhandler)
				.permitAll()
				
				)
				.exceptionHandling(exception -> exception
				.authenticationEntryPoint((request, response, authException) -> {

						String requestURI = request.getRequestURI(); // Get requested URL
						
						if (requestURI.startsWith("/customer/")) {
								response.sendRedirect("/signin"); // Redirect to customer login
						} else if (requestURI.startsWith("/bank_mitra/")) {
								response.sendRedirect("/bankmitraLogin"); // Redirect to bank mitra login
						} else if (requestURI.startsWith("/admin/")) {
								response.sendRedirect("/adminLogin"); // Redirect to bank mitra login
						} else {
								response.sendRedirect("/signin"); // Default login page
						}
				})
		     )

				.oauth2Login(Customizer -> {
				Customizer.loginPage("/customer/signin")
				.successHandler(new AuthenticationSuccessHandler() {

				@Override
				public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws IOException, ServletException {
				response.sendRedirect("/newHome");

				}

				});

			});



	 	http.logout(logout -> logout

				.logoutUrl("/logout") // Custom logout URL
				.logoutSuccessUrl("/home/logoutsuccess") // Redirect URL after successful logout
				.invalidateHttpSession(true) // Invalidate the session on logout
				.clearAuthentication(true) // Clear authentication
				.deleteCookies("JSESSIONID") // Optionally delete cookies
				.permitAll()
				
				);

		return http.build();
	}



	  @Bean
    public AuthenticationManager authenticationManager() {

        DaoAuthenticationProvider customerProvider = new DaoAuthenticationProvider();
        customerProvider.setUserDetailsService(getCustomerDetailService);
        customerProvider.setPasswordEncoder(passwordEncoder());

        DaoAuthenticationProvider adminProvider = new DaoAuthenticationProvider();
        adminProvider.setUserDetailsService(customAdminDetailsService);
        adminProvider.setPasswordEncoder(passwordEncoder());

        DaoAuthenticationProvider bankMitraProvider = new DaoAuthenticationProvider();
        bankMitraProvider.setUserDetailsService(customBankMitraDetailsService);
        bankMitraProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(List.of(customerProvider, bankMitraProvider, adminProvider));
    }












}
