package org.led.database.mysql.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import javax.sql.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@ComponentScan("org.led.database.mysql")
public class CommonDataConfig {
    @Bean
    Properties dataSourceProperties(Environment env, ApplicationContext ctx) throws IOException {
        Properties dataSourceProperties = new Properties();
        dataSourceProperties.load(
            ctx.getResource(env.getProperty("org.led.database.mysql.config")).getInputStream());
        return dataSourceProperties;
    }

    @Bean
    @Profile("default")
    DataSource dataSource(Environment env, @Qualifier("dataSourceProperties") Properties props) {
        setPropsFromEnv("hikari", props, env);
        return new HikariDataSource(new HikariConfig(props));
    }

    @Bean
    SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);

        try {
            sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                    .getResources("classpath*:mapper/*-mapper.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sessionFactory;
    }

    private void setPropsFromEnv(String prefix, Properties props, Environment env) {
        Collections.list(props.keys()).stream().forEach(key -> {
            String name = key.toString();
            String envName = prefix + '_' + name;
            String envAltName = prefix + '_' + name.replace('.', '_');

            if (env.containsProperty(envName)) {
                props.setProperty(name, env.getProperty(envName));
            } else if (env.containsProperty(envAltName)) {
                props.setProperty(name, env.getProperty(envAltName));
            }
        });
    }
}
