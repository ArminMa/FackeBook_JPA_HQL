package org.kth.HI1034.service;

import org.kth.HI1034.model.domain.faceMail.userReceivedMail.UserReceivedMailPojo;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Sys on 2016-08-27.
 */
public interface FaceMailService {

    ResponseEntity<?> sendMail(UserReceivedMailPojo userReceivedMailPojo, HttpServletRequest request);

    ResponseEntity<?> getReceivedMailToUser(String email);
}
