package org.kth.HI1034.service.impl;

import org.kth.HI1034.controller.util.MediaTypes;
import org.kth.HI1034.model.converters.Converter;
import org.kth.HI1034.model.domain.post.FacePost;
import org.kth.HI1034.model.domain.post.FacePostPojo;
import org.kth.HI1034.model.domain.post.PostRepository;
import org.kth.HI1034.model.domain.user.FaceUser;
import org.kth.HI1034.model.domain.user.FaceUserRepository;
import org.kth.HI1034.model.domain.user.FaceUserPojo;

import org.kth.HI1034.service.FaceUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FaceUserServiceImp implements FaceUserService {

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private FaceUserRepository userRepository;


	@Value("${token.header}")
	private String tokenHeader;

	@Override
	public List<FacePostPojo> getUserReceivdPosts(String email) {
		List<FacePost> facePosts = postRepo.findAllReceivedPostsToUserByUserEmail(email);

		@SuppressWarnings("unchecked")
		List<FacePostPojo> facePostPojos = (List<FacePostPojo>) Converter.convert(facePosts);

		return facePostPojos;
	}

	@Override
	public ResponseEntity getUserReceivdPosts(Long is) {
		return null;
	}

	@Override
	public ResponseEntity<?> getUserReceivdPosts(String userEmail, String authToken) {

		List<FacePost> facePosts = postRepo.findAllReceivedPostsToUserByUserEmail(userEmail);

		@SuppressWarnings("unchecked")
		List<FacePostPojo> facePostPojos = (List<FacePostPojo>) Converter.convert(facePosts);




		return ResponseEntity.ok()
				.contentType(MediaTypes.JsonUtf8)
				.header(tokenHeader, authToken)
				.body(facePostPojos.toString());

	}



  @Override
  public ResponseEntity<?> emailExist(FaceUserPojo faceUserPojo) {

    if(faceUserPojo == null || faceUserPojo.getEmail() == null ){
      ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    return (userRepository.findByEmail(faceUserPojo != null ? faceUserPojo.getEmail() : null) != null) ?
        ResponseEntity.status(HttpStatus.FOUND).build() :
        ResponseEntity.status(HttpStatus.NOT_FOUND).build();


  }

  @Override
  public ResponseEntity<?> getAllUsers() {

      List<FaceUser> faceUserList = userRepository.findAll();

      if(faceUserList == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();



      @SuppressWarnings("unchecked")
      List<FaceUserPojo>  faceUserPojo = ( List<FaceUserPojo>) Converter.convert(faceUserList);

    return ResponseEntity.status(HttpStatus.FOUND)
            .contentType(MediaTypes.JsonUtf8)
            .body(faceUserPojo.toString());
  }

  @Override
  public ResponseEntity<?> emailOrUsernameExist(String email, String userName){

	  FaceUser faceUser = userRepository.findOneUserByEmailOrUsername(email, userName);

	  if(faceUser != null && faceUser.getEmail().equals(email) && faceUser.getUsername().equals(userName)){
		  ResponseEntity.status(HttpStatus.FOUND).build();
	  }

	  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }


	@Override
	public ResponseEntity<?> findUserSimilarToThis(String searchValue){

		List<FaceUser> faceUserList = userRepository.findUserSimilarToThis(searchValue);

		if( faceUserList == null ){
			ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
        @SuppressWarnings("unchecked")
        List<FaceUserPojo> faceUserPojos = ( List<FaceUserPojo> ) Converter.convert(faceUserList);

        if(faceUserPojos != null){
            return ResponseEntity.ok()
                    .contentType(MediaTypes.JsonUtf8)
                    .body(faceUserPojos.toString());
        }



        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

    @Override
    public ResponseEntity<?> finUserByEmail(String email) {

        FaceUser faceUser = userRepository.findByEmail(email);

        if(faceUser == null) ResponseEntity.status(HttpStatus.NOT_FOUND).build();


        FaceUserPojo faceUserPojo = Converter.convert(faceUser);


        if(faceUserPojo != null){
            return ResponseEntity.ok()
                    .contentType(MediaTypes.JsonUtf8)
                    .body(faceUserPojo.toString());
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
