package hello.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import hello.interceptors.LogInterceptor;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	 @Override
	   public void addInterceptors(InterceptorRegistry registry) {
	      // LogInterceptor apply to all URLs.
	      registry.addInterceptor(new LogInterceptor());
	 }
}