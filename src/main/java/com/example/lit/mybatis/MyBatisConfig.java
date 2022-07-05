package com.example.lit.mybatis;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration //설정 관련 클래스
@MapperScan("com.example.lit.mapper") //전달한 패키지에서 @Mapper 스캔
@RequiredArgsConstructor
public class MyBatisConfig {
//    커넥션 풀 및 MyBatis에 필요한 요소를 메모리에 할당 및 관리, xml과 java 연동에 필요한 경로 관리
    private final ApplicationContext applicationContext;

    @Bean //@Configuration 또는 @Component가 작성된 클래스의 메소드에 사용할 수 있다.
          //메소드의 리턴 객체를 스프링 컨테이너에 등록시켜준다.
          //객체명은 메소드의 이름으로 자동 설정이 되고, 직접 설정 시 @Bean(name="객체명")으로 사용한다.

    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig(){ return new HikariConfig(); }

    @Bean
    public DataSource dataSource(){ return new HikariDataSource(hikariConfig()); }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean(); //세션 팩토리 설정 객체 생성
        sqlSessionFactoryBean.setDataSource(dataSource()); // 위에서 설정한 datasource 객체를 세션 팩토리 설정에 전달
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath*:/mapper/*.xml")); //SQL쿼리를 작성할 xml 경로 설정
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/config/config.xml")); //mapper에서 사용할 다양한 설정 파일
        try {
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
            sqlSessionFactory.getConfiguration().setMapUnderscoreToCamelCase(true);
            return sqlSessionFactory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}














