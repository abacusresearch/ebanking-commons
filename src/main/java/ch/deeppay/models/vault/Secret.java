package ch.deeppay.models.vault;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static org.apache.commons.lang.StringUtils.isNotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Secret {

  private String privateKey;
  private String certificate;
  private String bankCertificate;

  public boolean isValid(){
    return isNotBlank(getBankCertificate()) &&
    isNotBlank(getCertificate()) &&
    isNotBlank(getPrivateKey());
  }

}