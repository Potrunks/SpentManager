package fr.potrunks.gestiondepensebackend;

import fr.potrunks.gestiondepensebackend.entity.*;
import fr.potrunks.gestiondepensebackend.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
public class GestionDepenseBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionDepenseBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserIRepository userIRepository, SpentCategoryIRepository spentCategoryIRepository, PeriodSpentIRepository periodSpentIRepository, SalaryIRepository salaryIRepository, SpentIRepository spentIRepository) {
		return args -> {

			StartApplicationMessage("1.0.0");
			
			CreateAdministratorAccount(userIRepository);

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

			CreateSpentCategory(spentCategoryIRepository);

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

	/**
	 * Display a message at the start to the application
	 * @param version
	 */
	private void StartApplicationMessage(String version) {
		System.out.println("SPENT MANAGER v" + version);
	}

	/**
	 * Create the administrator account after verify if this account already exist
	 * @param userIRepository
	 */
	private void CreateAdministratorAccount(UserIRepository userIRepository) {
		log.warn("Start process administrator account creation...");
		if (!AdministratorAccountExist(userIRepository)) {
			SetAdministratorAccount(userIRepository);
		}
		log.warn("End process administrator account creation !!!");
	}

	/**
	 * Verify if Administrator account exist in database
	 * @param userIRepository
	 * @return Return a Boolean true if the account exist else return false
	 */
	private Boolean AdministratorAccountExist(UserIRepository userIRepository) {
		log.warn("Verification if administrator account already exist...");
		UserEntity userToVerifyIfAdmin = userIRepository.findByAdministratorTrue();
		if (userToVerifyIfAdmin == null) {
			log.warn("Administrator account don't exist !!!");
			return false;
		}
		log.warn("Administrator account already existed !!!");
		return true;
	}

	/**
	 * Setup the Administrator account
	 * @param userIRepository
	 */
	private void SetAdministratorAccount(UserIRepository userIRepository) {
		log.warn("Create the administrator account...");
		UserEntity userEntity = new UserEntity();
		userEntity.setFirstNameUser("Alexis");
		userEntity.setLastNameUser("ARRIAL");
		userEntity.setMailUser("potrunks@hotmail.com");
		userEntity.setPasswordUser("��P��3���i���e\u0002���*�\u001A\u0019�+��ғ2\u0018�");
		userEntity.setSaltUser("uqk");
		userEntity.setAdministrator(true);
		userIRepository.save(userEntity);
		log.warn("Administrator account created !!!");
	}

	/**
	 * Add spent category in the database if this don't already exist
	 * @param spentCategoryIRepository
	 */
	private void CreateSpentCategory(SpentCategoryIRepository spentCategoryIRepository) {
		log.warn("Start process spent category creation...");
		log.warn("Add list of spent category in the database...");
		for (String category : GetSpentCategoryList()
		) {
			if (!SpentCategoryExist(category, spentCategoryIRepository)) {
				log.warn("Add the spent category " + category + "...");
				SpentCategoryEntity spentCategoryEntity = new SpentCategoryEntity();
				spentCategoryEntity.setNameSpentCategory(category);
				spentCategoryIRepository.save(spentCategoryEntity);
				log.warn("The spent category " + category + " created !!!");
			}
		}
		log.warn("End process spent category creation !!!");
	}

	/**
	 * Get a spent category list. Can be modified
	 * @return Return a list of spent category
	 */
	private List<String> GetSpentCategoryList() {
		log.warn("Create list of spent category...");
		List<String> categoryList = new ArrayList<>();
		categoryList.add("Fast-Food");
		categoryList.add("Essence");
		categoryList.add("Courses");
		categoryList.add("Energie");
		categoryList.add("Multimedia");
		categoryList.add("Restaurant");
		categoryList.add("Impôt");
		categoryList.add("Autre");
		categoryList.add("Avance");
		return categoryList;
	}

	/**
	 * Verify if the spent category by name already exist in the database
	 * @param spentCategoryName Spent category name wanted
	 * @param spentCategoryIRepository
	 * @return Return a boolean true if the spent category already exist in database else return a false
	 */
	private Boolean SpentCategoryExist(String spentCategoryName, SpentCategoryIRepository spentCategoryIRepository) {
		log.warn("Start to verify if spent category " + spentCategoryName + " already exist in database...");
		if (spentCategoryIRepository.findByNameSpentCategory(spentCategoryName) == null) {
			log.warn("The spent category " + spentCategoryName + " don't exist !!!");
			return false;
		}
		log.warn("The spent category " + spentCategoryName + " already exist !!!");
		return true;
	}

}
