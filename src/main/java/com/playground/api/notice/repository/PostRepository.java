package com.playground.api.notice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.playground.api.notice.entity.PostEntity;
import com.playground.api.notice.entity.PostEntityPK;

public interface PostRepository extends JpaRepository< PostEntity, PostEntityPK >{
	List<PostEntity> findByNoticeEntity(PostEntityPK req);
	
	List<PostEntity> deleteByNttSn(Integer nttNo);
}
