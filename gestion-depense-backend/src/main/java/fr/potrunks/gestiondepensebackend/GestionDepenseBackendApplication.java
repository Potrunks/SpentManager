package fr.potrunks.gestiondepensebackend;

import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.repository.UserIRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GestionDepenseBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionDepenseBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserIRepository userIRepository) {
		return args -> {
			UserEntity userEntity = new UserEntity();
			userEntity.setFirstNameUser("Alexis");
			userEntity.setLastNameUser("ARRIAL");
			userEntity.setMailUser("potrunks@hotmail.com");
			userEntity.setPasswordUser("��P��3���i���e\u0002���*�\u001A\u0019�+��ғ2\u0018�");
			userEntity.setSaltUser("uqk");
			userEntity.setAdministrator(true);
			userIRepository.save(userEntity);
		};
	}

}
