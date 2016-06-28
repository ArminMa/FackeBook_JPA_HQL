package org.kth.HI1034.repository;

import org.kth.HI1034.model.UserFriedID;
import org.kth.HI1034.model.UserFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface UserFriendRepository extends
		JpaRepository<UserFriend, UserFriedID>,
		JpaSpecificationExecutor<UserFriend> {




}
