package config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Parking Spot Auction API")
                        .version("1.0.0")
                        .description("""
                                Сервис управления лотами аукциона парковочных мест.
                                
                                **Основные функции:**
                                - Получение списка парковочных мест в аукционе
                                - Добавление доступного места в аукцион
                                - Удаление места из аукциона
                                
                                **Бизнес-правила:**
                                - В аукцион попадают только свободные (AVAILABLE) места
                                - Место не может быть добавлено в один аукцион дважды
                                - Одно место может одновременно участвовать в разных аукционах
                                """)
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}
