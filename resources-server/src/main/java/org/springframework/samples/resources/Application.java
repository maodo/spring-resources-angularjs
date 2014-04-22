package org.springframework.samples.resources;

import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.samples.resources.helpers.ResourceUrlHelper;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.FingerprintResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.PublicResourceUrlProvider;
import org.springframework.web.servlet.resource.ResourceResolver;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(new Object[]{Application.class}, args);
	}

}

@Configuration
class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("index");
		registry.addViewController("/hello").setViewName("index");
		registry.addViewController("/hello/**").setViewName("index");
	}

	@Bean
	public HandlebarsViewResolver viewResolver(PublicResourceUrlProvider translator) {
		HandlebarsViewResolver viewResolver = new HandlebarsViewResolver();
		viewResolver.setPrefix("classpath:/templates/");
		viewResolver.registerHelper("src", new ResourceUrlHelper(translator));
		return viewResolver;
	}
}

@Configuration
class ClientConfig extends WebMvcConfigurerAdapter {

	@Value("${APP_HOME:}") String appPath;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

		List<ResourceResolver> resolvers = new ArrayList<ResourceResolver>();
		resolvers.add(new FingerprintResourceResolver());
		resolvers.add(new PathResourceResolver());

		if(!appPath.isEmpty()) {
			registry.addResourceHandler("/**")
					.addResourceLocations("file:///" + this.appPath + "/resources-client/src/")
					.setResourceResolvers(resolvers);
		} else {
			registry.addResourceHandler("/**")
					.addResourceLocations("classpath:/static/")
					.setResourceResolvers(resolvers);
		}

	}
}