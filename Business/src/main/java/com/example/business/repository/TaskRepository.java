package com.example.business.repository;

import com.example.business.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByUserId(Integer userId);
    List<Task> findByProjectId(Integer projectId);
}
