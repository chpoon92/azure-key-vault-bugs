package com.example.demo;

import com.azure.core.cryptography.AsyncKeyEncryptionKeyResolver;
import com.azure.core.cryptography.KeyEncryptionKeyResolver;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.security.keyvault.keys.KeyClient;
import com.azure.security.keyvault.keys.KeyClientBuilder;
import com.azure.security.keyvault.keys.cryptography.KeyEncryptionKeyClientBuilder;
import com.azure.security.keyvault.keys.models.KeyVaultKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;

@Slf4j
@EnableConfigurationProperties(KeyVaultProperties.class)
@SpringBootApplication
public class DemoApplication {

	@Autowired
	private KeyVaultProperties keyVaultProperties;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@PostConstruct
	public void test() {
		log.info("test key");
		try {
			ClientSecretCredential tokenCredential = new ClientSecretCredentialBuilder().tenantId(keyVaultProperties.getTenantId()).clientId(
					keyVaultProperties.getClientId()).clientSecret(keyVaultProperties.getClientSecret()).build();

			KeyClient keyAsyncClient = new KeyClientBuilder().vaultUrl(keyVaultProperties.getEndpoint()).credential(tokenCredential)
					.buildClient();

			KeyVaultKey keyVaultKey = keyAsyncClient.getKey(keyVaultProperties.getKeyName());

			log.info("keyVaultKey.getId={}", keyVaultKey.getId());

			KeyEncryptionKeyClientBuilder keyEncryptionKeyResolver =
					new KeyEncryptionKeyClientBuilder().credential(tokenCredential);

			String keyId = keyEncryptionKeyResolver.buildKeyEncryptionKey(keyVaultKey.getId()).getKeyId();

			log.info("keyId={}", keyId);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

}
