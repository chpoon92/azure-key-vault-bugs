Issue:

In azure-security-keyvault-keys v4.0.0, use com.azure.security.keyvault.keys.cryptography.KeyEncryptionKeyClientBuilder.buildKeyEncryptionKey().getKeyId(String keyId) leads to NullPointerException

Setup required:
1. Azure key vault url
2. Service principal that can access key vault
   * tenant-id
   * client id
   * client secret
3. Key name of an existing key in key vault

Properties:
```
azure:
  key-vault:
    endpoint:
    tenant-id:
    client-id:
    client-secret:
    key-name:
```