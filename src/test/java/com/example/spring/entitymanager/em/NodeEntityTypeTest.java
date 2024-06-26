package com.example.spring.entitymanager.em;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
//@DataJpaTest()
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = Replace.ANY)
@Import(value = {DBTestConfig.class, NodeEntityTypeTest.TestContextLoader.class})
public class NodeEntityTypeTest {



	@Test
	void test(){

	}

	@TestConfiguration
	public static class TestContextLoader {


	}
}