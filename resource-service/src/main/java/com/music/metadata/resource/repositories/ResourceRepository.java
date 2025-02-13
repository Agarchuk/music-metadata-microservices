package com.music.metadata.resource.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.music.metadata.resource.models.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

}
