package ru.tinkoff.edu.java.testing;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.tinkoff.edu.java.testing.configuration.IntegrationTestsConfiguration;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Testcontainers
abstract class AbstractPostgresContaineraseTest
{
    static Network network;

    @Container
    static PostgreSQLContainer<?> db;
    static {
        network = Network.newNetwork();
        db = new PostgreSQLContainer<>("postgres:14")
            .withExposedPorts(8080,5432)
            .withUsername("postgres")
            .withPassword("postgres")
            .withDatabaseName("db")
            .withNetwork(network)
            .withNetworkAliases("db");
        db.start();
    }

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", db::getJdbcUrl);
        registry.add("spring.datasource.username", db::getUsername);
        registry.add("spring.datasource.password", db::getPassword);
    }
}

@SpringBootTest
@Import(IntegrationTestsConfiguration.class)
public class IntegrationEnvironment extends AbstractPostgresContaineraseTest {

    @BeforeClass
    public static void init() throws IOException {


//        String url = db.getJdbcUrl();
//        System.out.println(url);

        Path userDirectory = Paths.get("")
                .toAbsolutePath().getParent().resolve("migrations").resolve("migrations");

//        System.out.println(userDirectory);
        Connection connection;

        {
            try {
                connection = DriverManager.getConnection(db.getJdbcUrl(),db.getUsername(),db.getPassword());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        Database database;

        {
            try {
                database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
        }
        Liquibase liquibase = new liquibase.Liquibase("master.xml",new DirectoryResourceAccessor(userDirectory),database);
        try {
            liquibase.update(new Contexts(),new LabelExpression());
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
        }



    }
}
