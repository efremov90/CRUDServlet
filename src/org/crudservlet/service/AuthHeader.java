package org.crudservlet.service;

import java.io.IOException;
import java.util.Base64;
import java.util.StringTokenizer;
import java.util.logging.Logger;

public class AuthHeader {

    private String Account;
    private String Password;

    private static Logger logger = Logger.getLogger(AuthHeader.class.getName());

    private AuthHeader() {

    }

    public AuthHeader(String authHeader) {

        logger.info("start");

        if (authHeader == null) {
            new AuthHeader();
        } else {
            final String valueBasic = authHeader.replaceFirst("Basic ", "");
            String accountAndPassword = null;
            try {
                byte[] decodedBytes = Base64.getDecoder().decode(
                        valueBasic);
                accountAndPassword = new String(decodedBytes, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            final StringTokenizer tokenizer = new StringTokenizer(
                    accountAndPassword, ":");
            try {
                if (tokenizer.countTokens() == 2) {
                    Account = tokenizer.nextToken();
                    Password = tokenizer.nextToken();
                    logger.info(this.toString());
                    logger.info("success");
                } else {
                    throw new IllegalArgumentException("Ошибка: Неверный заголовок");
                }
            } catch (IllegalArgumentException e) {
                logger.throwing(AuthHeader.class.getName(),
                        AuthHeader.class.getEnclosingMethod().getName(),
                        e);
                e.printStackTrace();
            }
        }
    }

    public String getAccount() {
        return Account;
    }

    public String getPassword() {
        return Password;
    }

    @Override
    public String toString() {
        return "AuthHeader{" +
                "Account='" + Account + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
