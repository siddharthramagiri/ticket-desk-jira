package com.example.demo.repository;

import com.example.demo.entity.Project;
import com.example.demo.entity.ProjectUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, Long> {

    @Query("""
        SELECT pu.project
        FROM ProjectUser pu
        WHERE pu.user.id = :userId
    """)
    List<Project> findProjectsByUserId(@Param("userId") Long userId);

}
