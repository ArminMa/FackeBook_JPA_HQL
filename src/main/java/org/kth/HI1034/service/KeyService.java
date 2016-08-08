package org.kth.HI1034.service;

import org.kth.HI1034.AppPublicKeys;
import org.kth.HI1034.model.domain.keyUserServer.UserServerKey;
import org.kth.HI1034.model.domain.keyUserServer.UserServerKeyPojo;


public interface KeyService {

    AppPublicKeys getAppPublicKeys();

    UserServerKeyPojo registerUserServerKey(UserServerKeyPojo userServerKeyPojo);

    UserServerKeyPojo findUserServerKey(String email);

    UserServerKey userExists(UserServerKeyPojo userServerKeyPojo);

    UserServerKeyPojo save(UserServerKeyPojo userServerKeyPojo);

    UserServerKeyPojo update(UserServerKeyPojo userServerKeyPojo);
}
