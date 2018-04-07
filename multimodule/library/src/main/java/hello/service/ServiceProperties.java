package hello.service;

import java.util.logging.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("service")
public class ServiceProperties {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ServiceProperties.class);
    /**
     * A message for the service.
     */
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
    	logger.debug("myservice configured with: {}", message);
        this.message = message;
    }
}