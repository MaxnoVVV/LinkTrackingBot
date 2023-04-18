package ru.tinkoff.edu.java.testing;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Testcontainers
abstract class AbstractPostgresContaineraseTest
{

    @Container
    static final PostgreSQLContainer<?> postgreSQLContainer;

    static final Liquibase liquibase;

    private static final Connection connection;

    static {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres:14")
                .withReuse(true)
                .withDatabaseName("scrapper");
        postgreSQLContainer.start();
        try {
            connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(),postgreSQLContainer.getUsername(),postgreSQLContainer.getPassword());
            System.out.println("Success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            liquibase = new Liquibase(new File(".")
                    .toPath()
                    .getParent()
                    .getParent()
                    .getParent()
                    .getParent()
                    .getParent()
                    .getParent()
                    .getParent()
                    .getParent()
                    .getParent()
                    .resolve("migrations")
                    .resolve("migrations")
                    .resolve("master.xml").toString()
                    ,new ClassLoaderResourceAccessor()
                    ,DatabaseFactory
                    .getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection)));
                    System.out.println("Success");
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

        try {
            liquibase.update(new Contexts(),new LabelExpression());
            System.out.println("Success");
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
        }

    }


}

@Testcontainers
public class IntegrationEnvironment extends AbstractPostgresContaineraseTest {


@Test
    public void EmptyTest()
{
    System.out.println("empty");
}


}
