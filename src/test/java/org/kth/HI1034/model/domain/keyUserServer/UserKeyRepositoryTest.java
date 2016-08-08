package org.kth.HI1034.model.domain.keyUserServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kth.HI1034.ApplicationWar;
import org.kth.HI1034.security.util.KeyUtil;
import org.kth.HI1034.security.util.SignatureKeyAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.crypto.SecretKey;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationWar.class)
public class UserKeyRepositoryTest {

	@Autowired
	private UserKeyRepository userServerKeyRepository;

	private UserServerKey userServerKey;







	@Before
	public void setUp() throws Exception {

		SecretKey secretShareKey = KeyUtil.SymmetricKey.generateSecretKey( 32, SignatureKeyAlgorithm.Algo.HS256);

		userServerKey = new UserServerKey("UserKeyRepositoryTest@gmail.com", KeyUtil.SymmetricKey.getKeyAsString(secretShareKey) );

		userServerKey = userServerKeyRepository.save(userServerKey);
		userServerKeyRepository.flush();
		assertThat(userServerKey).isNotNull();




	}


	@Test
	public void testKey(){

		System.out.println("\n\n----------------- UserKeyRepositoryTest.testKey-start ----------------------------\n\n");

		assertThat(userServerKey).isNotNull();
		assertThat(userServerKey.getEmail()).isNotNull();

		UserServerKey findUserServerKey = userServerKeyRepository.findByEmail(userServerKey.getEmail());

		assertThat(findUserServerKey).isNotNull();


		SecretKey secretTokenKey = KeyUtil.SymmetricKey.generateSecretKey( 32, SignatureKeyAlgorithm.Algo.HS256); //needed a (32*8) 256 bit SecretKey

		findUserServerKey.setTokenKey(KeyUtil.SymmetricKey.getKeyAsString(secretTokenKey));

		Integer linesUpdated = userServerKeyRepository.update(findUserServerKey.getEmail(), findUserServerKey.getSharedKey(),findUserServerKey.getTokenKey());
		userServerKeyRepository.flush();

		assertThat(linesUpdated).isNotNull();
		assertThat(linesUpdated).isEqualTo( 1 );

		userServerKey = userServerKeyRepository.findByEmail(findUserServerKey.getEmail());
		assertThat(userServerKey).isNotNull();

		System.out.println("\n\n----------------- UserKeyRepositoryTest.testKey-end ----------------------------\n\n");


	}

	@After
	public void tearDown() throws Exception {

		assertThat(userServerKey).isNotNull();

		userServerKeyRepository.delete(userServerKey.getId());

	}

}