package org.telusko.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telusko.project.Model.task;

import java.util.List;

@Repository
public interface taskRepository extends JpaRepository<task, Long> {
   List<task> findByStatus(task.Status status);
}
