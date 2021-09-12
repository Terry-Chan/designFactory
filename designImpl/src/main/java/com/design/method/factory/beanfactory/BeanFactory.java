package com.design.method.factory.beanfactory;


import com.design.method.api.snowflake.SnowId;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class BeanFactory {

    @Bean
    public SnowId snowId() {
        SnowId snowId = new SnowId(2,5);
        return snowId;
    }

//    @Bean(name = "source_a")
//    @ConfigurationProperties(prefix = "spring.datasource.druid.source_a")
//    public DataSource source_a() {
//        return DruidDataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "source_b")
//    @ConfigurationProperties(prefix = "spring.datasource.druid.source_b")
//    public DataSource source_b() {
//        return DruidDataSourceBuilder.create().build();
//    }
//
//
//    /**
//     * 动态数据源配置
//     *
//     * @return
//     */
//    @Bean
//    @Primary
//    public DataSource multipleDataSource(@Qualifier("source_a") DataSource source_a,
//                                         @Qualifier("source_b") DataSource source_b) {
//        DynamicDataSource dynamicDataSource = new DynamicDataSource(source_a);
//        Map<Object, Object> targetDataSources = new HashMap<>();
//        targetDataSources.put("source_a", source_a);
//        targetDataSources.put("source_b", source_b);
//        dynamicDataSource.setTargetDataSources(targetDataSources);
//        return dynamicDataSource;
//    }
//
//
//    @Bean("sqlSessionFactory")
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
//        sqlSessionFactory.setDataSource(multipleDataSource(source_a(), source_b()));
//
//        MybatisConfiguration configuration = new MybatisConfiguration();
//        configuration.setJdbcTypeForNull(JdbcType.NULL);
//        configuration.setMapUnderscoreToCamelCase(true);
//        configuration.setCacheEnabled(false);
//        sqlSessionFactory.setConfiguration(configuration);
//        return sqlSessionFactory.getObject();
//    }

}
