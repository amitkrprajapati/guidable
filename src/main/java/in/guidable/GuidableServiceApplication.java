package in.guidable;

import in.guidable.exceptions.GlobalExceptions;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(GlobalExceptions.class)
public class GuidableServiceApplication {

  @Bean
  ModelMapper modelMapper() {
    return new ModelMapper();
  }

  public static void main(String[] args) {
    SpringApplication.run(GuidableServiceApplication.class, args);
  }
}
