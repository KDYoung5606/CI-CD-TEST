package umc.study;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import umc.study.service.StoreService.StoreQueryService;

@SpringBootApplication
@EnableJpaAuditing
public class StudyApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(ApplicationContext context){
        return args -> {
            StoreQueryService storeQueryService = context.getBean(StoreQueryService.class);

            String name = "요아정";
            Float score = 4.0f;

            System.out.println("Executing findStoreByNameAndScore with parameters");
            System.out.println("Name : " + name);
            System.out.println("Score : " + score);
            storeQueryService.findStoreByNameAndScore(name, score).forEach(System.out::println);
        };
    }
}
