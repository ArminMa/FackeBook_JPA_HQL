package org.kth.HI1034.service;

import org.jose4j.jwk.RsaJsonWebKey;
import org.kth.HI1034.model.domain.jwt.UserServerKeyPojo;

import java.security.KeyPair;

/**
 * Created by Sys on 2016-07-21.
 */
public interface KeyService {

    RsaJsonWebKey getServerStaticRsaJsonWebKey();

    KeyPair getServerStaticKeyPair();

    UserServerKeyPojo registerUserServerKey(UserServerKeyPojo userServerKeyPojo);

    UserServerKeyPojo findUserServerKey(UserServerKeyPojo userServerKeyPojo);



}
