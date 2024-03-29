package org.kth.HI1034.model.converters;


import com.google.gson.Gson;
import org.kth.HI1034.model.domain.authority.Authority;
import org.kth.HI1034.model.domain.authority.AuthorityPojo;
import org.kth.HI1034.model.domain.authority.UserAuthority;
import org.kth.HI1034.model.domain.keyUserServer.UserServerKey;
import org.kth.HI1034.model.domain.keyUserServer.UserServerKeyPojo;
import org.kth.HI1034.model.domain.post.FacePost;
import org.kth.HI1034.model.domain.post.FacePostPojo;
import org.kth.HI1034.model.domain.post.UserDetached;
import org.kth.HI1034.model.domain.user.FaceUser;
import org.kth.HI1034.model.domain.user.FaceuserPojo;
import org.kth.HI1034.util.GsonX;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Converter {

	public static Gson gson = new Gson();

	public Converter() {
	}

	public static FaceUser convert(FaceuserPojo faceuserPojo){
		if( faceuserPojo == null ) return null;

		FaceUser faceUser = new FaceUser();


		faceUser.setId( (faceuserPojo.getId() != null ? faceuserPojo.getId() : null) );
		faceUser.setEmail(ObjectNotNull(faceuserPojo.getEmail() ) ? faceuserPojo.getEmail() : null);
		faceUser.setPassword( ObjectNotNull(faceuserPojo.getPassword() ) ? faceuserPojo.getPassword() : null );
		faceUser.setUsername(ObjectNotNull(faceuserPojo.getUsername() ) ? faceuserPojo.getUsername() : null);
		faceUser.setFirstName(ObjectNotNull(faceuserPojo.getFirstName() ) ? faceuserPojo.getFirstName() : null);
		faceUser.setLastName( ObjectNotNull(faceuserPojo.getLastName() ) ? faceuserPojo.getLastName() : null );
		faceUser.setEnabled( ObjectNotNull(faceuserPojo.getEnabled() ) ?  faceuserPojo.getEnabled() : true);
		faceUser.setAccountLocked( ObjectNotNull(faceuserPojo.getAccountLocked() ) ?  faceuserPojo.getAccountLocked() : false);
		faceUser.setAccountExpired( ObjectNotNull(faceuserPojo.getAccountExpired() ) ? faceuserPojo.getAccountExpired() : false);
		faceUser.setAccountCreated( ObjectNotNull(faceuserPojo.getAccountCreated() ) ? faceuserPojo.getAccountCreated() : null );


		SortedSet<UserAuthority> userAuthorities;

		if(
				(faceuserPojo.getAuthorities() != null) &&
						(faceuserPojo.getAuthorities().isEmpty() == false) &&
						(faceUser.getId() != null))
		{

			userAuthorities = new TreeSet<>();

			for (AuthorityPojo AP: faceuserPojo.getAuthorities()){
				userAuthorities.add(new UserAuthority(faceUser, Converter.convert(AP) ));
			}

			faceUser.setAuthorities(userAuthorities);
		}

		return faceUser;

	}

	public static FaceuserPojo convert(FaceUser faceUser){
		if( faceUser == null ) return null;

		FaceuserPojo faceuserPojo = new FaceuserPojo();


		faceuserPojo.setId( (faceUser.getId() != null ? faceUser.getId() : null) );
		faceuserPojo.setEmail(ObjectNotNull(faceUser.getEmail() ) ? faceUser.getEmail() : null);
		faceuserPojo.setPassword( ObjectNotNull(faceUser.getPassword() ) ? faceUser.getPassword() : null );
		faceuserPojo.setUsername(ObjectNotNull(faceUser.getUsername() ) ? faceUser.getUsername() : null);
		faceuserPojo.setFirstName(ObjectNotNull(faceUser.getFirstName() ) ? faceUser.getFirstName() : null);
		faceuserPojo.setLastName( ObjectNotNull(faceUser.getLastName() ) ? faceUser.getLastName() : null );
		faceuserPojo.setEnabled( ObjectNotNull(faceUser.getEnabled() ) ?  faceUser.getEnabled() : true);
		faceuserPojo.setAccountLocked( ObjectNotNull(faceUser.getAccountLocked() ) ?  faceUser.getAccountLocked() : false);
		faceuserPojo.setAccountExpired( ObjectNotNull(faceUser.getAccountExpired() ) ? faceUser.getAccountExpired() : false);
		faceuserPojo.setAccountCreated( ObjectNotNull(faceUser.getAccountCreated() ) ? faceUser.getAccountCreated() : null);

		List<AuthorityPojo> authorityPojos;

		if(
				(faceUser.getAuthorities() != null) &&
						(faceUser.getAuthorities().isEmpty() == false) &&
						(faceUser.getId() != null))
		{

			authorityPojos = new ArrayList<>();

			for (UserAuthority UA: faceUser.getAuthorities()){
				if(UA.getAuthority() != null){
					authorityPojos.add(Converter.convert(UA.getAuthority()));
				}

			}

			faceuserPojo.setAuthorities(authorityPojos);
		}

		return faceuserPojo;

	}



	private static boolean ObjectNotNull(Object o){
		return (o == null ? false : true);
	}


	public static UserServerKeyPojo convert(UserServerKey userServerKey){
		if( userServerKey == null ) return null;
		return gson.fromJson(userServerKey.toString(), UserServerKeyPojo.class );
	}

	public static UserServerKey convert(UserServerKeyPojo userServerKeyPojo){
		if( userServerKeyPojo == null ) return null;
		return gson.fromJson(userServerKeyPojo.toString(), UserServerKey.class );
	}

	public static AuthorityPojo convert (Authority authority){
		if( authority == null ) return null;
		return GsonX.gson.fromJson(authority.toString(), AuthorityPojo.class );
	}

	public static Authority convert (AuthorityPojo authorityPojo){
		if( authorityPojo == null ) return null;
		return GsonX.gson.fromJson(authorityPojo.toString(), Authority.class );
	}

	@SuppressWarnings("unchecked")
	public static Iterable<?> convert (Iterable<?> genericList){

		if( genericList == null ) return null;

		if ( !genericList.iterator().hasNext() ) return null;

		// this can be modified
//		if (somthing instanceof List<?>) {
//
//		}
//       else if (x instanceof Collection<?>) {
//             }

		if(genericList.iterator().next() instanceof AuthorityPojo){
			List<Authority> authorities = new ArrayList<>();
			genericList.forEach( S -> authorities.add( convert( (AuthorityPojo) S) ) );
			return authorities;
		}


		if(genericList.iterator().next() instanceof Authority){
			List<AuthorityPojo> authorityPojos = new ArrayList<>();
			genericList.forEach( S -> authorityPojos.add( convert( (Authority) S) ) );

			return authorityPojos;
		}

		if(genericList.iterator().next() instanceof FaceuserPojo){
			List<FaceUser> faceUsersList = new ArrayList<>();
			genericList.forEach( S -> faceUsersList.add( convert( (FaceuserPojo) S) ) );
			return faceUsersList;
		}

		if(genericList.iterator().next() instanceof FaceUser){
			List<FaceuserPojo> faceuserPojos = new ArrayList<>();
			genericList.forEach( S -> faceuserPojos.add( convert( (FaceUser) S) ) );
			return faceuserPojos;
		}

		if(genericList.iterator().next() instanceof UserServerKey){
			List<UserServerKeyPojo> userServerKeyPojos = new ArrayList<>();
			genericList.forEach( S -> userServerKeyPojos.add( convert( (UserServerKey) S) ) );
			return userServerKeyPojos;
		}

		if(genericList.iterator().next() instanceof UserServerKeyPojo){
			List<UserServerKey> userServerKeys = new ArrayList<>();
			genericList.forEach( S -> userServerKeys.add( convert( (UserServerKeyPojo) S) ) );
			return userServerKeys;
		}

		if(genericList.iterator().next() instanceof FacePost){
			List<FacePostPojo> facePostPojos = new ArrayList<>();

			for(FacePost FP : (List<FacePost>) genericList){

				FacePostPojo facePostPojo = GsonX.gson.fromJson(FP.toString(), FacePostPojo.class);

				FaceuserPojo authorFaceuserPojo = new FaceuserPojo();
				authorFaceuserPojo.setEmail(FP.getAuthor().getEmail());
				authorFaceuserPojo.setUsername(FP.getAuthor().getUsername());

				facePostPojo.setFaceUserAuthor(authorFaceuserPojo);
				facePostPojos.add(facePostPojo);
			}
			return facePostPojos;
		}

		if(genericList.iterator().next() instanceof FacePostPojo){
			List<FacePost> facePosts = new ArrayList<>();

			for(FacePostPojo FP : (List<FacePostPojo>) genericList){

				FacePost facePost = GsonX.gson.fromJson(FP.toString(), FacePost.class);

				UserDetached userDetached = new UserDetached();
				userDetached.setEmail(FP.getFaceUserAuthor().getEmail());
				userDetached.setUsername(FP.getFaceUserAuthor().getUsername());

				facePost.setAuthor(userDetached);
				facePosts.add(facePost);
			}
			return facePosts;
		}

		return null;

	}

	public static FacePostPojo convert(FacePost facePost) {

		FacePostPojo facePostPojo = GsonX.gson.fromJson(facePost.toString(), FacePostPojo.class);

		FaceuserPojo authorFaceuserPojo = new FaceuserPojo();
		authorFaceuserPojo.setEmail(facePost.getAuthor().getEmail());
		authorFaceuserPojo.setEmail(facePost.getAuthor().getUsername());

		facePostPojo.setFaceUserAuthor(authorFaceuserPojo);

		return facePostPojo;
	}

	public static FacePost convert(FacePostPojo facePostPojo) {

		FacePost facePost = GsonX.gson.fromJson(facePostPojo.toString(), FacePost.class);

		UserDetached userDetached = new UserDetached();
		userDetached.setEmail(facePostPojo.getFaceUserAuthor().getEmail());
		userDetached.setEmail(facePostPojo.getFaceUserAuthor().getUsername());

		facePost.setAuthor(userDetached);

		return facePost;
	}


}
