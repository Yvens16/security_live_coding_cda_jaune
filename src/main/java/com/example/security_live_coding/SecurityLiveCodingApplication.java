package com.example.security_live_coding;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SecurityLiveCodingApplication {

	@Autowired
	JwtService jwtService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(SecurityLiveCodingApplication.class, args);
	}

	@GetMapping("/login")
	public ResponseEntity<?> login(@RequestParam String username) {
		// Retourner un token JWT
		// Je vais venir vérifier si les credentials
		// (identifiants et le mdp) correspondent à ce que j'ai en base pour
		// l'utilisateur
		Optional<UserEntity> userOptional = this.userRepository.findByUsername(username);
		if (userOptional.isPresent()) {
			String realUserName = userOptional.get().getUsername();
			String role = userOptional.get().getRoles().get(0).getName();
			String token = this.jwtService.generateToken(realUserName, role);
			return ResponseEntity.ok(token);
		} else {
			throw new RuntimeException("User not found");
		}
	}

	@GetMapping("/admin")
	public ResponseEntity<?> admin() {
		// Vérifier si j'ai des droits admin
		return ResponseEntity.ok("Hello admin");
	}

	@GetMapping("/user")
	public ResponseEntity<?> user() {
		// Vérifier si j'ai des droits user
		return ResponseEntity.ok("Hello user");
	}

	@Bean
	public CommandLineRunner runner() {
		return args -> {
			RoleEntity adminRole = new RoleEntity("ADMIN");
			RoleEntity userRole = new RoleEntity("USER");

			roleRepository.save(adminRole);
			roleRepository.save(userRole);

			UserEntity adminUser = new UserEntity("admin");
			UserEntity userUser = new UserEntity("user1");

			adminUser.getRoles().add(adminRole);
			userUser.getRoles().add(userRole);

			userRepository.save(adminUser);
			userRepository.save(userUser);

		};
	}

}

// https://github.com/jwtk/jjwt

// Enigma
// Les allemand un code secret pour encoder leur message

// allemand
// Le code c'est juste d'inverser le 1ere et la dernière lettre pour chaque mot