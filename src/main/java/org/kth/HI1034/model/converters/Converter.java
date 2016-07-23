package org.kth.HI1034.model.converters;


import com.google.gson.Gson;
import org.kth.HI1034.model.domain.entity.user.FaceUser;
import org.kth.HI1034.model.domain.jwt.UserServerKey;
import org.kth.HI1034.model.domain.jwt.UserServerKeyPojo;
import org.kth.HI1034.model.pojo.FaceuserPojo;

import java.util.Date;

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
		faceUser.setAccountLocked( ObjectNotNull(faceuserPojo.getAccountLocked() ) ?  faceuserPojo.getAccountLocked() : null);
		faceUser.setAccountExpired( ObjectNotNull(faceuserPojo.getAccountExpired() ) ? faceuserPojo.getAccountExpired() : null);
		faceUser.setAccountCreated( ObjectNotNull(faceuserPojo.getAccountCreated() ) ? faceuserPojo.getAccountCreated() : null );



		if(faceuserPojo.getAuthorities() != null && faceuserPojo.getAuthorities().isEmpty() == false){
			faceUser.setAuthorities(faceuserPojo.getAuthorities());
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
		faceuserPojo.setAccountCreated( ObjectNotNull(faceUser.getAccountCreated() ) ? faceUser.getAccountCreated() : new Date());

		if(faceUser.getAuthorities() != null && !faceUser.getAuthorities().isEmpty()) faceuserPojo.setAuthorities(faceuserPojo.getAuthorities());

		return faceuserPojo;

	}

	private static boolean ObjectNotNull(Object o){
		return (o == null ? false : true);
	}


	public static UserServerKeyPojo convert(UserServerKey userServerKey){
		return gson.fromJson(userServerKey.toString(), UserServerKeyPojo.class );
	}

	public static UserServerKey convert(UserServerKeyPojo userServerKeyPojo){
		return gson.fromJson(userServerKeyPojo.toString(), UserServerKey.class );
	}
}
