package hello.app;

import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import hello.service.MyService;

@SpringBootApplication(scanBasePackages = "hello")
@RestController
public class DemoApplication {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DemoApplication.class);
	
    private final MyService myService;

    public DemoApplication(MyService myService) {
        this.myService = myService;
    }

    @GetMapping("/")
    public String home() {
    	logger.debug("GET / called");
        return myService.message();
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}