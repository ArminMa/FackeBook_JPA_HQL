package org.kth.HI1034.service;

import org.kth.HI1034.AppPublicKeys;
import org.kth.HI1034.model.domain.jwt.UserServerKeyPojo;


public interface KeyService {

    AppPublicKeys getAppPublicKeys();

    UserServerKeyPojo registerUserServerKey(UserServerKeyPojo userServerKeyPojo);

    UserServerKeyPojo findUserServerKey(UserServerKeyPojo userServerKeyPojo);

    boolean userExists(UserServerKeyPojo userServerKeyPojo);

    UserServerKeyPojo save(UserServerKeyPojo userServerKeyPojo);

}
