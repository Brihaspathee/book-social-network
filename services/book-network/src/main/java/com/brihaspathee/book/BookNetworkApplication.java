package com.brihaspathee.book;

import com.brihaspathee.book.auth.entity.Role;
import com.brihaspathee.book.auth.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@Slf4j
public class BookNetworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookNetworkApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository) {
		log.info("Running runner");
		return args -> {
			if(roleRepository.findByRoleName("USER").isEmpty()) {
				roleRepository.save(Role.builder()
								.roleName("USER")
						.build());
			}
		};
	}

}
