package org.kth.HI1034.repository.post;


import org.kth.HI1034.model.entity.user.UserDetached;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface PostUserRepository extends JpaRepository<UserDetached, String>, JpaSpecificationExecutor<UserDetached> {

}