package net.mite.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Masud
 */
public class Getconnection {

    public String domainUrl = null;
    public String isHttps = null;

    public Getconnection(String domainName, String isHttps) {
        
        this.isHttps = isHttps;

        if (isHttps.equalsIgnoreCase("true")) {
            this.domainUrl = "https://" + domainName;
        } else {
            this.domainUrl = "http://" + domainName;
        }
        
        System.out.println("Url Address : "+domainUrl);
    }

    public String getDomainUrl() {
        return domainUrl;
    }

    public void getCon() {

        try {
            URL url = new URL(domainUrl);

            if (isHttps.equalsIgnoreCase("true")) {
                //** FOR SSL connection
                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
                SSLContext.setDefault(ctx);

                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();



                conn.setHostnameVerifier(new HostnameVerifier() {

                    public boolean verify(String arg0, SSLSession arg1) {
                        return true;
                    }
                });

                System.out.println(conn.getResponseCode());

                conn.disconnect();
            } else {

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                System.out.println(conn.getResponseCode());

                conn.disconnect();
            }


        } catch (Exception ex) {
        }
    }

    private static class DefaultTrustManager implements X509TrustManager {

       
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

    
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

       
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }
}
