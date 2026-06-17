package com.sanossalvos.pet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, properties = {
		"spring.datasource.url=jdbc:h2:mem:testdb;MODE=PostgreSQL",
		"spring.datasource.driver-class-name=org.h2.Driver",
		"spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
		"spring.kafka.bootstrap-servers=localhost:9092"
})
@ActiveProfiles("test")
class PetApplicationTests {

	@Test
	void contextLoads() {
		// Verifica que el contexto levante bien
	}

	@Test
	void mainMethodTest() {
		// Esto ejecuta el main real usando argumentos vacíos para cubrir las líneas de PetApplication
		PetApplication.main(new String[] {"--spring.profiles.active=test"});
	}
}