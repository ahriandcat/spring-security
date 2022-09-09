package com.example.demosecurity;

import com.example.demosecurity.entity.User;
import com.example.demosecurity.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootTest
class DemoSecurityApplicationTests {


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Test
	void save_user(){
		User user1 = User.builder()
				.name("John").email("john@gmail.com")
				.password(passwordEncoder.encode("111"))
				.role(List.of("USER","EDITOR"))
				.build();

		User user2 = User.builder()
				.name("Timmie").email("timmie@gmail.com")
				.password(passwordEncoder.encode("222"))
				.role(List.of("USER"))
				.build();

		User user3 = User.builder()
				.name("Alice").email("alice@gmail.com")
				.password(passwordEncoder.encode("333"))
				.role(List.of("USER","EDITOR","ADMIN"))
				.build();

		userRepository.saveAll(List.of(user1,user2,user3));
	}
}
