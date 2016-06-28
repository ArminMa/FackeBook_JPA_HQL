//package org.kth.HI1034.model;
//
//import javax.persistence.Embeddable;
//import javax.persistence.FetchType;
//import javax.persistence.ManyToOne;
//
//@Embeddable
//public class FriendRequestPk implements java.io.Serializable {
//
//	private FaceUser requestFrom;
//	private FaceUser requestTo;
//
//
//	public FriendRequestPk() {
//	}
//
//	public FriendRequestPk(FaceUser requestFrom, FaceUser requestTo) {
//		this.requestFrom = requestFrom;
//		this.requestTo = requestTo;
//	}
//
//
//	@ManyToOne(fetch= FetchType.LAZY)
//	public FaceUser getRequestFrom() {
//		return requestFrom;
//	}
//	public void setRequestFrom(FaceUser requestFrom) {
//		this.requestFrom = requestFrom;
//	}
//
//
//	@ManyToOne(fetch= FetchType.LAZY)
//
//	public FaceUser getRequestTo() {
//		return requestTo;
//	}
//	public void setRequestTo(FaceUser requestTo) {
//		this.requestTo = requestTo;
//	}
//}
