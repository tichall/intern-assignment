package sy.internproject.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    private final String prodUrl;

    public SwaggerConfig(@Value("${sy.prodUrl}") String prodUrl) {
        this.prodUrl = prodUrl;
    }

    @Bean
    public OpenAPI openAPI() {
        final Server devServer = new Server();
        devServer.setUrl("/");
        devServer.description("개발 환경 서버 URL");

        final Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.description("운영 환경 서버 URL");

        final Info info = new Info()
                .title("Swagger API")
                .version("v1.0.0")
                .description("한 달 인턴 직무별 과제 API");

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer));
    }

}