package com.example.security_live_coding;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SecurityLiveCodingApplication {

	@Autowired
	JwtService jwtService;

	public static void main(String[] args) {
		SpringApplication.run(SecurityLiveCodingApplication.class, args);
	}

	@GetMapping("/login")
	public ResponseEntity<?> login(@RequestParam String username, @RequestParam String role) {
		// Retourner un token JWT
		// Je vais venir vérifier si les credentials
		// (identifiants et le mdp) correspondent à ce que j'ai en base pour l'utilisateur
		String token = this.jwtService.generateToken(username, role);
		return ResponseEntity.ok(token);
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

	// @Bean
	// public CommandLineRunner runner() {
	// 	return args -> {
	// 		String token = this.jwtService.generateToken();
	// 		String subject = this.jwtService.getSubject(token);
	// 		Date date = this.jwtService.getExpirationDate(token);

	// 		System.out.println("@@@@@@@@@@ date: " + date);
	// 	};
	// }

}

// https://github.com/jwtk/jjwt

// Enigma
// Les allemand un code secret pour encoder leur message

// allemand
// Le code c'est juste d'inverser le 1ere et la dernière lettre pour chaque mot