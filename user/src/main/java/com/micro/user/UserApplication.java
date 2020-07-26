package com.micro.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrixDashboard
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	// do not delete for now
//	@Bean
//	public Docket api() throws IOException, org.codehaus.plexus.util.xml.pull.XmlPullParserException {
//		MavenXpp3Reader reader = new MavenXpp3Reader();
//		Model model = reader.read(new FileReader("pom.xml"));
//		return new Docket(DocumentationType.SWAGGER_2)
//				.select()
////				.apis(RequestHandlerSelectors.basePackage("com.micro.user.controller"))
//				.paths(PathSelectors.any())
//				.build().apiInfo(new ApiInfo("User Service Api Documentation", "Documentation automatically generated", model.getParent().getVersion(), null, new Contact("Piotr Mińkowski", "piotrminkowski.wordpress.com", "piotr.minkowski@gmail.com"), null, null));
//	}


}
