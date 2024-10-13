package net.cavitos.workshop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ApiSecurityConfiguration {
    
    @Value("${auth0.audience}")
    private String audience;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;

    @Value("${security.cors.origins}")
    private String[] origins;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {

        http.cors(withDefaults())
                .authorizeHttpRequests(requests -> requests
                        // Spring actuators
                        .requestMatchers(HttpMethod.GET, "/actuator/**")
                            .permitAll() // GET requests don't need auth

                        // UI routes
                        .requestMatchers(HttpMethod.GET, "/ui/**")
                            .permitAll()
                        .requestMatchers(HttpMethod.POST, "/ui/**")
                            .permitAll()
                        .requestMatchers(HttpMethod.PUT, "/ui/**")
                            .permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/ui/**")
                            .permitAll()

                        // Static files
                        .requestMatchers(HttpMethod.GET, "/css/**")
                            .permitAll()

                        // API routes
                        .anyRequest()
                        .authenticated()
                )
                .oauth2ResourceServer(server -> server
                        .jwt(jwtConfigurer ->
                                jwtConfigurer.decoder(jwtDecoder())
                        ));

                http.csrf((csrf) -> csrf.csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler()));

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(origins));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(List.of("authorization", "content-type"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    JwtDecoder jwtDecoder() {

        final var withAudience = new AudienceValidator(audience);
        final var withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
        final var validator = new DelegatingOAuth2TokenValidator<>(withAudience, withIssuer);

        final var jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromOidcIssuerLocation(issuer);
        jwtDecoder.setJwtValidator(validator);

        return jwtDecoder;
    }

}
