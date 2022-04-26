package tr.com.ga.common.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tr.com.ga.common.config.swagger.ProfileEnums;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private Environment environment;
	// Adding the roles
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("mynormaluser")
				.password(encodePWD().encode("mynormaluserpass"))
				.roles("POSTS","COMMENTS");
	}
	// Configuring the api
	// according to the roles.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		var authorizeRequests =
			http
      		.csrf().disable()
			.authorizeRequests();

		if (ProfileEnums.DEV.profileExists(environment) ) {
			authorizeRequests.antMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll();
		}

		authorizeRequests
			.antMatchers("/health").permitAll()
			.antMatchers("/auth/login").permitAll()
			.antMatchers("/delete").hasRole("VALIDATED_USER")
			.anyRequest().authenticated()
			.and()
			.logout()
			.deleteCookies("JSESSIONID", "SESSION")
				.and().httpBasic();


	}
	@Bean
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}

}
