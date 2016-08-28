package se.acrend.slack.qbis.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 *
 */
@Configuration
public class ApplicationConfig {

  private Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

  @Value("DATABASE_URL")
  private String dbUrl;

  @Bean(name = "datasource")
  @Profile("local")
  public DataSource localDataSource() {

    // no need shutdown, EmbeddedDatabaseFactoryBean will take care of this

    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
    EmbeddedDatabase db = builder
        .setType(EmbeddedDatabaseType.H2)
        .build();
    return db;
  }

  @Bean(name = "datasource")
  @Profile("!local")
  public DataSource dataSource() {

    logger.info("Database url: " + dbUrl);

    // no need shutdown, EmbeddedDatabaseFactoryBean will take care of this

    BasicDataSource ds = new BasicDataSource();

    ds.setDriverClassName("org.postgresql.Driver");
    ds.setUrl(dbUrl);

    return ds;
  }


}
