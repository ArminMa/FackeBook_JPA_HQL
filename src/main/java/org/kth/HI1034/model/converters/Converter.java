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
import org.kth.HI1034.model.domain.post.UserDetachedPojo;
import org.kth.HI1034.model.domain.user.FaceUser;
import org.kth.HI1034.model.domain.user.FaceUserPojo;
import org.kth.HI1034.util.GsonX;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Converter {

	public static Gson gson = new Gson();

	public Converter() {
	}

	public static FaceUser convert(FaceUserPojo faceUserPojo){
		if( faceUserPojo == null ) return null;

		FaceUser faceUser = new FaceUser();


		faceUser.setId( (faceUserPojo.getId() != null ? faceUserPojo.getId() : null) );
		faceUser.setEmail(ObjectNotNull(faceUserPojo.getEmail() ) ? faceUserPojo.getEmail() : null);
		faceUser.setPassword( ObjectNotNull(faceUserPojo.getPassword() ) ? faceUserPojo.getPassword() : null );
		faceUser.setUsername(ObjectNotNull(faceUserPojo.getUsername() ) ? faceUserPojo.getUsername() : null);
		faceUser.setFirstName(ObjectNotNull(faceUserPojo.getFirstName() ) ? faceUserPojo.getFirstName() : null);
		faceUser.setLastName( ObjectNotNull(faceUserPojo.getLastName() ) ? faceUserPojo.getLastName() : null );
		faceUser.setEnabled( ObjectNotNull(faceUserPojo.getEnabled() ) ?  faceUserPojo.getEnabled() : true);
		faceUser.setAccountLocked( ObjectNotNull(faceUserPojo.getAccountLocked() ) ?  faceUserPojo.getAccountLocked() : false);
		faceUser.setAccountExpired( ObjectNotNull(faceUserPojo.getAccountExpired() ) ? faceUserPojo.getAccountExpired() : false);
		faceUser.setAccountCreated( ObjectNotNull(faceUserPojo.getAccountCreated() ) ? faceUserPojo.getAccountCreated() : null );


		SortedSet<UserAuthority> userAuthorities;

		if(
				(faceUserPojo.getAuthorities() != null) &&
						(faceUserPojo.getAuthorities().isEmpty() == false) &&
						(faceUser.getId() != null))
		{

			userAuthorities = new TreeSet<>();

			for (AuthorityPojo AP: faceUserPojo.getAuthorities()){
				userAuthorities.add(new UserAuthority(faceUser, Converter.convert(AP) ));
			}

			faceUser.setAuthorities(userAuthorities);
		}

		return faceUser;

	}

	public static FaceUserPojo convert(FaceUser faceUser){
		if( faceUser == null ) return null;

		FaceUserPojo faceUserPojo = new FaceUserPojo();


		faceUserPojo.setId( (faceUser.getId() != null ? faceUser.getId() : null) );
		faceUserPojo.setEmail(ObjectNotNull(faceUser.getEmail() ) ? faceUser.getEmail() : null);
		faceUserPojo.setPassword( ObjectNotNull(faceUser.getPassword() ) ? faceUser.getPassword() : null );
		faceUserPojo.setUsername(ObjectNotNull(faceUser.getUsername() ) ? faceUser.getUsername() : null);
		faceUserPojo.setFirstName(ObjectNotNull(faceUser.getFirstName() ) ? faceUser.getFirstName() : null);
		faceUserPojo.setLastName( ObjectNotNull(faceUser.getLastName() ) ? faceUser.getLastName() : null );
		faceUserPojo.setEnabled( ObjectNotNull(faceUser.getEnabled() ) ?  faceUser.getEnabled() : true);
		faceUserPojo.setAccountLocked( ObjectNotNull(faceUser.getAccountLocked() ) ?  faceUser.getAccountLocked() : false);
		faceUserPojo.setAccountExpired( ObjectNotNull(faceUser.getAccountExpired() ) ? faceUser.getAccountExpired() : false);
		faceUserPojo.setAccountCreated( ObjectNotNull(faceUser.getAccountCreated() ) ? faceUser.getAccountCreated() : null);

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

			faceUserPojo.setAuthorities(authorityPojos);
		}

		return faceUserPojo;

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

		if(genericList.iterator().next() instanceof FaceUserPojo){
			List<FaceUser> faceUsersList = new ArrayList<>();
			genericList.forEach( S -> faceUsersList.add( convert( (FaceUserPojo) S) ) );
			return faceUsersList;
		}

		if(genericList.iterator().next() instanceof FaceUser){
			List<FaceUserPojo> faceUserPojos = new ArrayList<>();
			genericList.forEach( S -> faceUserPojos.add( convert( (FaceUser) S) ) );
			return faceUserPojos;
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
				facePostPojos.add(facePostPojo);
			}

			return facePostPojos;
		}

		if(genericList.iterator().next() instanceof FacePostPojo){
			List<FacePost> facePosts = new ArrayList<>();

			for(FacePostPojo FP : (List<FacePostPojo>) genericList){

				facePosts.add( GsonX.gson.fromJson(FP.toString(), FacePost.class) );
			}
			return facePosts;
		}

		return null;

	}


	public static FacePostPojo convert(FacePost facePost) {
		return GsonX.gson.fromJson(facePost.toString(), FacePostPojo.class);
	}


	public static FacePost convert(FacePostPojo facePostPojo) {
		return GsonX.gson.fromJson(facePostPojo.toString(), FacePost.class);
	}

    private static UserDetached convert(UserDetachedPojo userDetachedPojo) {
        return GsonX.gson.fromJson(userDetachedPojo.toString(), UserDetached.class);
    }
    private static UserDetachedPojo convert(UserDetached userDetached) {
        return GsonX.gson.fromJson(userDetached.toString(), UserDetachedPojo.class);
    }

}
