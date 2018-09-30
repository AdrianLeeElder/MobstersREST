package com.adrian.mobsters.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

@Component
public class SSLCertificateImporter implements CommandLineRunner {

    private static final String PASSPHRASE = "changeit";
    private static final String CERT_ALIAS = "mobsters";

    @Value("classpath:mobsterscert.cer")
    private Resource mobstersSslCertificate;
    @Value("classpath:cacerts")
    private Resource trustStore;

    @Override
    public void run(String... args) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        System.setProperty("javax.net.ssl.trustStore", trustStore.getFile().getAbsolutePath());
        InputStream certIn = mobstersSslCertificate.getInputStream();

        final char sep = File.separatorChar;
        File dir = new File(System.getProperty("java.home") + sep + "lib" + sep + "security");

        InputStream localCertIn = trustStore.getInputStream();

        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(localCertIn, PASSPHRASE.toCharArray());
        if (keystore.containsAlias(CERT_ALIAS)) {
            certIn.close();
            localCertIn.close();
            return;
        }
        localCertIn.close();

        BufferedInputStream bis = new BufferedInputStream(certIn);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        while (bis.available() > 0) {
            Certificate cert = cf.generateCertificate(bis);
            keystore.setCertificateEntry(CERT_ALIAS, cert);
        }

        certIn.close();

        OutputStream out = new FileOutputStream(trustStore.getFile());
        keystore.store(out, PASSPHRASE.toCharArray());
        out.close();
    }
}
