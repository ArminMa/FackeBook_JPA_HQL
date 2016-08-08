package org.kth.HI1034.util.enums;


import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@JsonInclude(JsonInclude.Include.NON_NULL)
public enum Role {

//    ROLE_USER, ROLE_ADMIN, ROLE_SUPER_ADMIN , ROLE_ANONYMOUS;

	ROLE_USER, ROLE_ADMIN, ROLE_SUPER_ADMIN , ROLE_ANONYMOUS;

	public static final String USER = "USER";

	public static final String ADMIN = "ADMIN";

	public static final String SUPER_ADMIN = "SUPER_ADMIN";

	public static final String ANONYMOUS = "ANONYMOUS";
}
