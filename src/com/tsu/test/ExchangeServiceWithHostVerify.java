package com.tsu.test;

import microsoft.exchange.webservices.data.EWSConstants;
import microsoft.exchange.webservices.data.core.EwsSSLProtocolSocketFactory;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.security.GeneralSecurityException;

public class ExchangeServiceWithHostVerify extends ExchangeService {

    //不对任何域名做验证
    private final static HostnameVerifier hostnameVerifierWithOutVerfy = new HostnameVerifier(){
        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    };

    public ExchangeServiceWithHostVerify() {
        super();
    }
    public ExchangeServiceWithHostVerify(ExchangeVersion requestedServerVersion) {
        super(requestedServerVersion);
    }

    protected Registry<ConnectionSocketFactory> createConnectionSocketFactoryRegistry() {
        try {
            return RegistryBuilder.<ConnectionSocketFactory>create().register(EWSConstants.HTTP_SCHEME, new PlainConnectionSocketFactory()).register(EWSConstants.HTTPS_SCHEME, EwsSSLProtocolSocketFactory.build(null,hostnameVerifierWithOutVerfy)).build();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(
                    "Could not initialize ConnectionSocketFactory instances for HttpClientConnectionManager", e
            );
        }
    }


}
