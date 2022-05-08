package fr.potrunks.gestiondepensebackend;

import fr.potrunks.gestiondepensebackend.entity.SpentCategoryEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.repository.SpentCategoryIRepository;
import fr.potrunks.gestiondepensebackend.repository.UserIRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class GestionDepenseBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionDepenseBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserIRepository userIRepository, SpentCategoryIRepository spentCategoryIRepository) {
		return args -> {
			// Add user
			UserEntity userEntity = new UserEntity();
			userEntity.setFirstNameUser("Alexis");
			userEntity.setLastNameUser("ARRIAL");
			userEntity.setMailUser("potrunks@hotmail.com");
			userEntity.setPasswordUser("��P��3���i���e\u0002���*�\u001A\u0019�+��ғ2\u0018�");
			userEntity.setSaltUser("uqk");
			userEntity.setAdministrator(true);
			userIRepository.save(userEntity);

			// Add spent category
			List<String> categoryList = new ArrayList<>();
			categoryList.add("Restaurant");
			categoryList.add("Fast-Food");
			categoryList.add("Gasoline");
			categoryList.add("Grocery");
			categoryList.add("Energy");
			categoryList.add("Multimedia");
			categoryList.add("Restaurant");
			categoryList.add("Tax");
			categoryList.add("Other");
			for (String category : categoryList
				 ) {
				SpentCategoryEntity spentCategoryEntity = new SpentCategoryEntity();
				spentCategoryEntity.setNameSpentCategory(category);
				spentCategoryIRepository.save(spentCategoryEntity);
			}
		};
	}

}
