package org.kth.HI1034.controller;

import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;
import org.kth.HI1034.AppPublicKeys;
import org.kth.HI1034.controller.util.MediaTypes;
import org.kth.HI1034.service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/getAppPublicKey")
public class AppKeyController {

//	@Value("${Jose4J.keyUserServer.public.Server.Key}")
//	public String publicKey;

	@Autowired
	private KeyService keyService;


	@RequestMapping( produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<?> getPublicAppKey(HttpServletRequest request, HttpServletResponse response) throws JoseException, InvalidJwtException {

		AppPublicKeys appPublicKeys = keyService.getAppPublicKeys();

		return ResponseEntity.ok()
				.contentType(MediaTypes.JsonUtf8)
				.body(keyService.getAppPublicKeys().toString());

	}

//	@RequestMapping(value = "/crypto", method = RequestMethod.GET)
//	public @ResponseBody
//	ResponseEntity<?> getCryptoData(
//			HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//
//
//
///*		JWTRsaSignature.ProduceJWTVerifyAndEncrypt("");*/
//
//		String encypted = " A_Test ";
//
//		FaceuserPojo faceuserPojo = new FaceuserPojo();
//		faceuserPojo.setEmail("armin@gmail.com");
//		faceuserPojo.setFirstName("Armin");
//
////		encypted = CipherUtils.encryptWithPublicKey(faceuserPojo.toString(), apiKeyFactory.getPublicKey());
//
//		encypted = CipherUtils.encryptWithSymmetricKey(faceuserPojo.toString(), apiKeyFactory.getSymmetricKey());
//
//		System.out.println("\n\n\n\n" +
//				"----------------------------------- apiKeyController_57 -------------------------------------\n\n" +
//				"\n\n" +
//				encypted +
//				"\n\n" +
//				"------------------------------------------------------------------------\n\n\n\n\n");
//
//		return ResponseEntity.ok()
//				.contentType(MediaType.TEXT_PLAIN)
//				.body(encypted);
//
//	}


//	@RequestMapping(value = "/cryptoJwt", method = RequestMethod.GET)
//	public @ResponseBody
//	ResponseEntity<?> getCryptoJwt(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		String encypted = " A_Test ";
//
//		FaceuserPojo faceuserPojo = new FaceuserPojo();
//		faceuserPojo.setEmail("armin@gmail.com");
//		faceuserPojo.setFirstName("Armin");
//
////		encypted = CipherUtils.encryptWithPublicKey(faceuserPojo.toString(), apiKeyFactory.getPublicKey());
//
//		encypted = CipherUtils.encryptWithSymmetricKey(faceuserPojo.toString(), apiKeyFactory.getSymmetricKey());
//
//		System.out.println("\n\n\n\n" +
//				"----------------------------------- apiKeyController_86 -------------------------------------\n\n" +
//				"\n\n" +
//				encypted +
//				"\n\n" +
//				"------------------------------------------------------------------------\n\n\n\n\n");
//
//		return ResponseEntity.ok()
//				.contentType(MediaType.TEXT_PLAIN)
//				.body(encypted);
//
//	}

}
