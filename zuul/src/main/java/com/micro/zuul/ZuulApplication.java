package com.micro.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrixDashboard
public class ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}

	// Do not delete for now
	// https://piotrminkowski.com/2017/04/14/microservices-api-documentation-with-swagger2/
//	@Bean
//	public Docket api() throws IOException, org.codehaus.plexus.util.xml.pull.XmlPullParserException {
//		MavenXpp3Reader reader = new MavenXpp3Reader();
//		Model model = reader.read(new FileReader("pom.xml"));
//		return new Docket(DocumentationType.SWAGGER_2)
//				.select()
////				.apis(RequestHandlerSelectors.basePackage("com.micro.user.controller"))
//				.paths(PathSelectors.any())
//				.build().apiInfo(new ApiInfo("Zuul Service Api Documentation", "Documentation automatically generated", model.getParent().getVersion(), null, new Contact("Piotr Mińkowski", "piotrminkowski.wordpress.com", "piotr.minkowski@gmail.com"), null, null));
//	}
//

}
