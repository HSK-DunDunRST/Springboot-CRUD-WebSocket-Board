package kr.co.ipdisk.dundunhsk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.apache.catalina.connector.Connector;
// import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
// import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
// import org.springframework.context.annotation.Bean;
// import org.springframework.web.bind.annotation.RestController;

/* Security 해제시 로그인 사이트 안보이게 하기. */
//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
// @RestController
public class BoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

    // @Bean
    // ServletWebServerFactory servletContainer() {
    //     TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
    //     tomcat.addAdditionalTomcatConnectors(createSslConnector());
    //     return tomcat;
    // }

    // private Connector createSslConnector() {
    //     Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    //     connector.setScheme("https");
    //     connector.setSecure(true);
    //     connector.setPort(5001);
    //     return connector;
    // }
}
