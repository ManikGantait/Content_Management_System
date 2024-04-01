package com.example.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.entity.BlogPost;

public interface BlogPostRepository extends JpaRepository<BlogPost, Integer>{

}
