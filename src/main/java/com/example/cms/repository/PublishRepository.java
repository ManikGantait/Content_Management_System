package com.example.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.entity.BlogPost;
import com.example.cms.entity.Publish;
import com.example.cms.entity.Schedule;

public interface PublishRepository extends JpaRepository<Publish, Integer>{



}
