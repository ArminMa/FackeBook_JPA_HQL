package org.kth.HI1034.repository.post;


import org.kth.HI1034.model.entity.post.PostUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface PostUserRepository extends JpaRepository<PostUser, Long>, JpaSpecificationExecutor<PostUser> {

}