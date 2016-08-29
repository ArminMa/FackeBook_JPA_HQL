package org.kth.HI1034.service.impl;


import org.kth.HI1034.controller.util.MediaTypes;
import org.kth.HI1034.model.domain.faceMail.FaceMail;
import org.kth.HI1034.model.domain.faceMail.FaceMailRepository;
import org.kth.HI1034.model.domain.faceMail.userReceivedMail.UserReceivedMail;
import org.kth.HI1034.model.domain.faceMail.userReceivedMail.UserReceivedMailPojo;
import org.kth.HI1034.model.domain.faceMail.userReceivedMail.UserReceivedMailRepository;
import org.kth.HI1034.model.domain.user.FaceUser;
import org.kth.HI1034.model.domain.user.FaceUserRepository;
import org.kth.HI1034.service.FaceMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@Service
public class FaceMailServiceImpl implements FaceMailService{


    @Autowired
    private FaceUserRepository userRepo;


    @Autowired
    private FaceMailRepository mailRepo;


    @Autowired
    private UserReceivedMailRepository userReceivedMailRepo;


    @Override
    public ResponseEntity<?> sendMail(UserReceivedMailPojo userReceivedMailPojo, HttpServletRequest request){

        FaceUser faceSender = userRepo.findByEmail(userReceivedMailPojo.getAuthor().getEmail());

        FaceUser reciver = userRepo.findByEmail(userReceivedMailPojo.getReceivingUser().getEmail());


        FaceMail faceFaceMailA = mailRepo.save(new FaceMail(
                userReceivedMailPojo.getReceivedMail().getHeader(),
                userReceivedMailPojo.getReceivedMail().getMailText(),
                new Date() ));
        mailRepo.flush();


        UserReceivedMail userReceivedMail = new UserReceivedMail(faceFaceMailA, reciver, faceSender);
        userReceivedMail = userReceivedMailRepo.save(userReceivedMail);
        userReceivedMailRepo.flush();

        if(userReceivedMail!= null)  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @Override
    public ResponseEntity<?> getReceivedMailToUser(String email) {

        List<UserReceivedMail> userReceivedMail = userReceivedMailRepo.findReceivedMailToUser(email);

        if(userReceivedMail == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok()
                .contentType(MediaTypes.JsonUtf8)
                .body(userReceivedMail.toString());
    }
}
