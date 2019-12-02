package com.example.demo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("azure.key-vault")
public class KeyVaultProperties {

  private String endpoint;

  private String tenantId;

  private String clientId;

  private String clientSecret;

  private String keyName;

}
