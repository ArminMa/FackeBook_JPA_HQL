//package org.kth.HI1034.model;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//import java.io.Serializable;
//import java.util.Date;
//
//@Entity
//@Table(name = "authority_user")
//public class AuthorityFaceUser implements Serializable {
//
//
//	private Authority authority;
//	private FaceUser byUser;
//	private Date sentDate;
//
//	private Long id;
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//
//	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//	@JoinColumn(name = "authority_id")
//	public Authority getAuthority() {
//		return authority;
//	}
//	public void setAuthority(Authority authority) {
//		this.authority = authority;
//	}
//
//
//	@OneToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "user_id")
//	public FaceUser getByUser() {
//		return byUser;
//	}
//	public void setByUser(FaceUser byUser) {
//		this.byUser = byUser;
//	}
//}
