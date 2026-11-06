package org.telusko.project.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telusko.project.Dto.TaskPatchRequest;
import org.telusko.project.Dto.TaskRequest;
import org.telusko.project.Model.task;
import org.telusko.project.Repository.taskRepository;
import org.telusko.project.Exception.TaskNotFoundException;

import java.util.List;
import java.util.Locale;

@Service
public class TaskService {
    @Autowired
    private final taskRepository taskRepository;

    public TaskService(taskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public task createTask(TaskRequest request) {
        task newTask = new task();
        newTask.setDescription(request.description().trim());
        newTask.setStatus(request.status());
        return taskRepository.save(newTask);
    }

    public List<task> findAllTasks(task.Status status) {
        return status == null
                ? taskRepository.findAll()
                : taskRepository.findByStatus(status);
    }

    public task findTaskById(Long id) {
        return findExistingTask(id);
    }

    @Transactional
    public task replaceTask(Long id, TaskRequest request) {
        task existingTask = findExistingTask(id);
        existingTask.setDescription(request.description().trim());
        existingTask.setStatus(request.status());
        return taskRepository.save(existingTask);
    }

    @Transactional
    public task patchTask(Long id, TaskPatchRequest request) {
        task existingTask = findExistingTask(id);

        if (request.description() == null && request.status() == null) {
            throw new IllegalArgumentException("At least one field must be provided");
        }
        if (request.description() != null) {
            if (request.description().trim().isEmpty()) {
                throw new IllegalArgumentException("Description must not be blank");
            }
            existingTask.setDescription(request.description().trim());
        }
        if (request.status() != null) {
            existingTask.setStatus(request.status());
        }

        return taskRepository.save(existingTask);
    }

    @Transactional
    public void deleteTask(Long id) {
        taskRepository.delete(findExistingTask(id));
    }

    private task findExistingTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public task.Status parseStatus(String status) {
        try {
            return task.Status.valueOf(status.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Invalid task status: " + status, exception);
        }
    }
}
