//package org.kth.HI1034.model;
//
//
//import javax.persistence.CascadeType;
//import javax.persistence.Embeddable;
//import javax.persistence.FetchType;
//import javax.persistence.ManyToOne;
//import java.io.Serializable;
//
//
//@Embeddable
//public class FacePostPk implements Serializable{
//
//	public FaceUser requestFrom;
//	public FaceUser requestTo;
//
//
//	public FacePostPk() {
//	}
//
//	@ManyToOne(fetch= FetchType.LAZY , cascade = CascadeType.DETACH)
//	public FaceUser getRequestFrom() {
//		return requestFrom;
//	}
//	public void setRequestFrom(FaceUser requestFrom) {
//		this.requestFrom = requestFrom;
//	}
//
//
//	@ManyToOne(fetch= FetchType.LAZY , cascade = CascadeType.DETACH)
//	public FaceUser getRequestTo() {
//		return requestTo;
//	}
//	public void setRequestTo(FaceUser requestTo) {
//		this.requestTo = requestTo;
//	}
//
//}
