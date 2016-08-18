package org.kth.HI1034.controller.util;

import org.springframework.http.MediaType;

import java.nio.charset.Charset;


public class MediaTypes {


	public static final MediaType JsonUtf8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
}
