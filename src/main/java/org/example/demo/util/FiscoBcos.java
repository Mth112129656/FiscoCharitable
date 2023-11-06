package org.example.demo.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.config.ConfigOption;
import org.fisco.bcos.sdk.config.exceptions.ConfigException;
import org.fisco.bcos.sdk.config.model.ConfigProperty;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.transaction.manager.AssembleTransactionProcessor;
import org.fisco.bcos.sdk.transaction.manager.TransactionProcessorFactory;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.InputStream;

@Data
@Component
@Slf4j
public class FiscoBcos {

    BcosSDK bcosSDK;
    Client fiscoclient;
    CryptoKeyPair keyPair;
    AssembleTransactionProcessor transactionProcessor;

    public FiscoBcos() throws Exception {
        this.init();
    }

    public void init() throws Exception {
        ConfigProperty configProperty = loadProperty();
        ConfigOption configOption ;
        try {
            configOption = new ConfigOption(configProperty, CryptoType.ECDSA_TYPE);
        } catch (ConfigException e) {
            log.error("init error:" + e.toString());
            return ;
        }
        bcosSDK = new BcosSDK(configOption);
        fiscoclient = bcosSDK.getClient(Integer.valueOf(1));
        keyPair = this.getFiscoclient().getCryptoSuite().createKeyPair();
        transactionProcessor = TransactionProcessorFactory.createAssembleTransactionProcessor(fiscoclient, keyPair, "src/main/resources/abi/", "src/main/resources/bin/");
    }

    public ConfigProperty loadProperty() {
        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        Yaml yaml = new Yaml(representer);
        String configFile = "/fisco-config.yml";
        try (InputStream inputStream = this.getClass().getResourceAsStream(configFile)) {
            return yaml.loadAs(inputStream, ConfigProperty.class);
        } catch (Exception e) {
            log.error("load property: ", e);
        }
        return null;
    }

}