package cl.huertohogar.ordenes_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrdenesBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdenesBackendApplication.class, args);
	}

}
