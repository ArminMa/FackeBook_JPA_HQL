package org.kth.HI1034.service.impl;


import org.kth.HI1034.controller.util.MediaTypes;
import org.kth.HI1034.model.converters.Converter;
import org.kth.HI1034.model.domain.post.*;

import org.kth.HI1034.model.domain.user.FaceUserPojo;

import org.kth.HI1034.security.JWT.TokenPojo;
import org.kth.HI1034.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserDetachedRepository postUserRepo;

    private List<UserDetached> userDetachedList = new ArrayList<>();

    @Override
    public ResponseEntity<?> post(FacePostPojo faceUserPojo, HttpServletRequest request){


        FacePost facePost = Converter.convert(faceUserPojo);


        facePost.setAuthor( postUserRepo.findOneByEmail(faceUserPojo.getAuthor().getEmail()) );
        facePost.getReceivers().clear();

        facePost.setAuthor( postUserRepo.findOneByEmail(faceUserPojo.getAuthor().getEmail()) );
        facePost.getReceivers().add( postUserRepo.findOneByEmail(faceUserPojo.getReceivers().first().getEmail())  );




        facePost = postRepo.save( facePost );
        postRepo.flush();

        if(facePost != null){
            return ResponseEntity.status(HttpStatus.OK).build();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @SuppressWarnings("unchecked")
    @Override
    public ResponseEntity<?> getPostToUserByUserEmail(FaceUserPojo faceUserPojo, HttpServletRequest request) {

        List<FacePost> facePost = postRepo.findAllReceivedPostsToUserByUserEmail( faceUserPojo.getEmail() );
        List<FacePostPojo> facePostPojos = (List<FacePostPojo>) Converter.convert(facePost);

//        return ResponseEntity.ok()
//                .contentType(MediaTypes.JsonUtf8)
//                .header(tokenHeader, httpTokenHeader)
//                .header(userTokenHeader, httpUserTokenHeader)
//                .body(tokenPojo.toString());

        return ResponseEntity.ok()
                .contentType(MediaTypes.JsonUtf8)
                .body(facePostPojos.toString());
    }


}
