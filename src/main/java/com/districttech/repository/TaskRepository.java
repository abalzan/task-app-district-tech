package com.districttech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.districttech.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

}
