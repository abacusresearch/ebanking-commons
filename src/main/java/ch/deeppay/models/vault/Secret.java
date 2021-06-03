package ch.deeppay.models.vault;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Secret {

  private String privateKey;
  private String certificate;
  private String bankCertificate;

}