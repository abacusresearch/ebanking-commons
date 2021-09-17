package ch.deeppay.secrets;

import java.util.HashMap;
import java.util.Map;

import ch.deeppay.models.vault.Secret;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties
public class SecretProperties {

  private Map<String, Secret> secrets = new HashMap<>();

}
