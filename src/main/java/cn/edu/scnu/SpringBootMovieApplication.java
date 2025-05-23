package cn.edu.scnu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.edu.scnu.mapper")
public class SpringBootMovieApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMovieApplication.class, args);
    }

}
