package eu.innowise.ingredientservice.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.ext.neo4j.database.Neo4jDatabase;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    @Value("${spring.neo4j.uri}")
    private String neo4jUri;

    @Value("${spring.neo4j.authentication.username}")
    private String neo4jUsername;

    @Value("${spring.neo4j.authentication.password}")
    private String neo4jPassword;

    @Value("${spring.liquibase.change-log}")
    private String changelogFile;

//    @Bean
//    public Liquibase neo4jLiquibase() throws Exception {
//
//        try (Driver driver = GraphDatabase.driver(neo4jUri, AuthTokens.basic(neo4jUsername, neo4jPassword))) {}
//
//        Database database = DatabaseFactory.getInstance().openDatabase(
//                neo4jUri,
//                neo4jUsername,
//                neo4jPassword,
//                null,
//                new ClassLoaderResourceAccessor()
//        );
//
//        try (Liquibase liquibase = new Liquibase(
//                changelogFile,
//                new ClassLoaderResourceAccessor(),
//                database)) {
//            liquibase.update("");
//        }
//
//        return null;
//    }
}
