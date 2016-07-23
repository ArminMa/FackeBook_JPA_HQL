package org.kth.HI1034.model.domain.repository;

import org.kth.HI1034.model.domain.jwt.UserServerKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Sys on 2016-07-16.
 */
public interface UserKeyRepository 	extends
		JpaRepository<UserServerKey, Long>,
		JpaSpecificationExecutor<UserServerKey> {




}
