package fr.potrunks.gestiondepensebackend;

import fr.potrunks.gestiondepensebackend.entity.*;
import fr.potrunks.gestiondepensebackend.repository.*;
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
	CommandLineRunner run(UserIRepository userIRepository, SpentCategoryIRepository spentCategoryIRepository, PeriodSpentIRepository periodSpentIRepository, SalaryIRepository salaryIRepository, SpentIRepository spentIRepository) {
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

			/*
			UserEntity userEntity2 = new UserEntity();
			userEntity2.setFirstNameUser("Valerie");
			userEntity2.setLastNameUser("PAUCHET");
			userEntity2.setMailUser("valchan@hotmail.com");
			userEntity2.setPasswordUser("��P��3���i���e\u0002���*�\u001A\u0019�+��ғ2\u0018�");
			userEntity2.setSaltUser("uqk");
			userEntity2.setAdministrator(false);
			userEntity2 = userIRepository.save(userEntity2);
			*/

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

			/*
			// Add new period spent
			// 1st period spent
			List<UserEntity> userEntityList = new ArrayList<>();
			userEntityList.add(userEntity);
			userEntityList.add(userEntity2);

			PeriodSpentEntity periodSpentEntity = new PeriodSpentEntity();
			periodSpentEntity.setStartDatePeriodSpent(LocalDate.now().minusDays(60));
			periodSpentEntity.setEndDatePeriodSpent(LocalDate.now().minusDays(30));
			periodSpentEntity.setUserEntityList(userEntityList);
			periodSpentEntity = periodSpentIRepository.save(periodSpentEntity);

			SalaryEntity salaryEntity = new SalaryEntity();
			salaryEntity.setUserEntity(userEntity);
			salaryEntity.setValueSalary(7001f);
			salaryEntity.setPeriodSpentEntity(periodSpentEntity);
			salaryEntity.setDateSalary(LocalDate.now());
			salaryIRepository.save(salaryEntity);

			SalaryEntity salaryEntity2 = new SalaryEntity();
			salaryEntity2.setUserEntity(userEntity2);
			salaryEntity2.setValueSalary(3002f);
			salaryEntity2.setPeriodSpentEntity(periodSpentEntity);
			salaryEntity2.setDateSalary(LocalDate.now());
			salaryIRepository.save(salaryEntity2);

			// 2nd period spent
			PeriodSpentEntity periodSpentEntity2 = new PeriodSpentEntity();
			periodSpentEntity2.setStartDatePeriodSpent(LocalDate.now().minusDays(30));
			periodSpentEntity2.setUserEntityList(userEntityList);
			periodSpentEntity2 = periodSpentIRepository.save(periodSpentEntity2);

			SalaryEntity salaryEntity3 = new SalaryEntity();
			salaryEntity3.setUserEntity(userEntity);
			salaryEntity3.setValueSalary(7003f);
			salaryEntity3.setPeriodSpentEntity(periodSpentEntity2);
			salaryEntity3.setDateSalary(LocalDate.now());
			salaryIRepository.save(salaryEntity3);

			SalaryEntity salaryEntity4 = new SalaryEntity();
			salaryEntity4.setUserEntity(userEntity2);
			salaryEntity4.setValueSalary(3004f);
			salaryEntity4.setPeriodSpentEntity(periodSpentEntity2);
			salaryEntity4.setDateSalary(LocalDate.now());
			salaryIRepository.save(salaryEntity4);

			// Add spents
			// For 1st Period Spent
			List<SpentEntity> spentEntityList = new ArrayList<>();
			SpentEntity spentEntity = new SpentEntity();
			spentEntity.setValueSpent(100f);
			spentEntity.setSpentCategoryEntity(spentCategoryIRepository.getById(1L));
			spentEntity.setDateSpent(LocalDate.now().minusDays(25));
			spentEntity.setNameSpent("Mc DO");
			spentEntity.setUserEntity(userEntity);
			spentEntity.setCommentSpent("C pas bien");
			spentEntity.setPeriodSpentEntity(periodSpentEntity);
			spentEntityList.add(spentEntity);
			spentIRepository.saveAll(spentEntityList);

			// For 2nd Period Spent
			List<SpentEntity> spentEntityList2 = new ArrayList<>();
			SpentEntity spentEntity2 = new SpentEntity();
			spentEntity2.setValueSpent(350f);
			spentEntity2.setSpentCategoryEntity(spentCategoryIRepository.getById(5L));
			spentEntity2.setDateSpent(LocalDate.now());
			spentEntity2.setNameSpent("XBOX");
			spentEntity2.setUserEntity(userEntity);
			spentEntity2.setCommentSpent("C bien");
			spentEntity2.setPeriodSpentEntity(periodSpentEntity2);
			spentEntityList2.add(spentEntity2);
			spentIRepository.saveAll(spentEntityList2);
			*/
		};
	}

}
