package com.sohwakmo.cucumbermarket.repository;


import com.sohwakmo.cucumbermarket.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer>{
    List<Post> findByTitleIgnoreCaseContainingOrContentIgnoreCaseContainingOrderByPostNoDesc(String title, String content);

    Post findByImageName01(String imageSrc);
    Post findByImageName02(String imageSrc);
}
