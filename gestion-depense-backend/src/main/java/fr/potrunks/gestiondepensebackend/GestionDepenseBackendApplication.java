package fr.potrunks.gestiondepensebackend;

import fr.potrunks.gestiondepensebackend.entity.PeriodSpentEntity;
import fr.potrunks.gestiondepensebackend.entity.SalaryEntity;
import fr.potrunks.gestiondepensebackend.entity.SpentCategoryEntity;
import fr.potrunks.gestiondepensebackend.entity.UserEntity;
import fr.potrunks.gestiondepensebackend.repository.PeriodSpentIRepository;
import fr.potrunks.gestiondepensebackend.repository.SalaryIRepository;
import fr.potrunks.gestiondepensebackend.repository.SpentCategoryIRepository;
import fr.potrunks.gestiondepensebackend.repository.UserIRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class GestionDepenseBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionDepenseBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserIRepository userIRepository, SpentCategoryIRepository spentCategoryIRepository, PeriodSpentIRepository periodSpentIRepository, SalaryIRepository salaryIRepository) {
		return args -> {
			// Add user
			UserEntity userEntity = new UserEntity();
			userEntity.setFirstNameUser("Alexis");
			userEntity.setLastNameUser("ARRIAL");
			userEntity.setMailUser("potrunks@hotmail.com");
			userEntity.setPasswordUser("��P��3���i���e\u0002���*�\u001A\u0019�+��ғ2\u0018�");
			userEntity.setSaltUser("uqk");
			userEntity.setAdministrator(true);
			userEntity = userIRepository.save(userEntity);
			UserEntity userEntity2 = new UserEntity();
			userEntity2.setFirstNameUser("Valerie");
			userEntity2.setLastNameUser("PAUCHET");
			userEntity2.setMailUser("valchan@hotmail.com");
			userEntity2.setPasswordUser("��P��3���i���e\u0002���*�\u001A\u0019�+��ғ2\u0018�");
			userEntity2.setSaltUser("uqk");
			userEntity2.setAdministrator(false);
			userEntity2 = userIRepository.save(userEntity2);

			// Add spent category
			List<String> categoryList = new ArrayList<>();
			categoryList.add("Fast-Food");
			categoryList.add("Gasoline");
			categoryList.add("Grocery");
			categoryList.add("Energy");
			categoryList.add("Multimedia");
			categoryList.add("Restaurant");
			categoryList.add("Tax");
			categoryList.add("Other");
			categoryList.add("Deposit");
			for (String category : categoryList
				 ) {
				SpentCategoryEntity spentCategoryEntity = new SpentCategoryEntity();
				spentCategoryEntity.setNameSpentCategory(category);
				spentCategoryIRepository.save(spentCategoryEntity);
			}

			// Add new period spent
			List<UserEntity> userEntityList = new ArrayList<>();
			userEntityList.add(userEntity);
			userEntityList.add(userEntity2);
			PeriodSpentEntity periodSpentEntity = new PeriodSpentEntity();
			periodSpentEntity.setStartDatePeriodSpent(LocalDate.now());
			periodSpentEntity.setUserEntityList(userEntityList);
			periodSpentIRepository.save(periodSpentEntity);
			SalaryEntity salaryEntity = new SalaryEntity();
			salaryEntity.setUserEntity(userEntity);
			salaryEntity.setValueSalary(7000f);
			salaryEntity.setPeriodSpentEntity(periodSpentEntity);
			salaryEntity.setDateSalary(LocalDate.now());
			salaryIRepository.save(salaryEntity);
			SalaryEntity salaryEntity2 = new SalaryEntity();
			salaryEntity2.setUserEntity(userEntity2);
			salaryEntity2.setValueSalary(3000f);
			salaryEntity2.setPeriodSpentEntity(periodSpentEntity);
			salaryEntity2.setDateSalary(LocalDate.now());
			salaryIRepository.save(salaryEntity2);
		};
	}

}
