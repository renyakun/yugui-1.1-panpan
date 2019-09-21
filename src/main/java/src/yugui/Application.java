package src.yugui;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.spring.annotation.MapperScan;

@RestController
@SpringBootApplication
@ComponentScan(basePackages = {"src"})
@MapperScan("src.yugui.mapper")
public class Application {
    private static final Logger logger = Logger.getLogger(Application.class);

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        logger.info("main start...");
        SpringApplication.run(Application.class, args);
    }

}


