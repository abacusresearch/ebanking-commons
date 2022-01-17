package ch.deeppay.spring.logging;

import java.util.Collections;
import java.util.HashSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.zalando.logbook.BodyFilter;
import org.zalando.logbook.json.JsonBodyFilters;

public class LogConfigurationTest {


  @Test
  public void testReplaceFormUrlEncodedProperty(){
    BodyFilter bodyFilter = LogConfiguration.replaceFormUrlEncodedProperty(new HashSet<>(Collections.singletonList("refreshToken")), "<secrect>");
    String result = bodyFilter.filter("application/x-www-form-urlencoded","bankId=6300&clientId=abacusresearch&clientSecret=17&grantType=REFRESHTOKEN&class=class+swiss.deepcloud.deeppay.ebanking.oauth.schema.OAuthRefreshTokenRequest&refreshToken=mkedNvetTvvTPPPGn3DCFMK4hYr");
    Assertions.assertEquals("bankId=6300&clientId=abacusresearch&clientSecret=17&grantType=REFRESHTOKEN&class=class+swiss.deepcloud.deeppay.ebanking.oauth.schema.OAuthRefreshTokenRequest&refreshToken=<secrect>",result);
  }


  @Test
  public void testReplaceFormUrlEncodedPropertyWithInvalidContentType(){
    BodyFilter bodyFilter = LogConfiguration.replaceFormUrlEncodedProperty(new HashSet<>(Collections.singletonList("refreshToken")), "<secrect>");
    String result = bodyFilter.filter("invalid","bankId=6300&clientId=abacusresearch&clientSecret=17&grantType=REFRESHTOKEN&class=class+swiss.deepcloud.deeppay.ebanking.oauth.schema.OAuthRefreshTokenRequest&refreshToken=mkedNvetTvvTPPPGn3DCFMK4hYr");
    Assertions.assertEquals("bankId=6300&clientId=abacusresearch&clientSecret=17&grantType=REFRESHTOKEN&class=class+swiss.deepcloud.deeppay.ebanking.oauth.schema.OAuthRefreshTokenRequest&refreshToken=mkedNvetTvvTPPPGn3DCFMK4hYr",result);
  }


  @Test
  public void testJsonBodyFilters(){
    BodyFilter bodyFilter =   JsonBodyFilters.replaceJsonStringProperty(new HashSet<>(Collections.singletonList("contractId")), "<secret>");
    String result = bodyFilter.filter("application/json","{\n" +
                                                "\"bankId\": \"0\",\n" +
                                                "\"contractId\": \"OptCodeLogin1\",\n" +
                                                "\"password\": \"1234\",\n" +
                                                "\"language\": \"de\"\n" +
                                                "}");
    Assertions.assertEquals("{\n" +
                            "\"bankId\": \"0\",\n" +
                            "\"contractId\": \"<secret>\",\n" +
                            "\"password\": \"1234\",\n" +
                            "\"language\": \"de\"\n" +
                            "}",result);
  }

}