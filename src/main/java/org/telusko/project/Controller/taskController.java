package org.telusko.project.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.telusko.project.Dto.TaskPatchRequest;
import org.telusko.project.Dto.TaskRequest;
import org.telusko.project.Dto.TaskResponse;
import org.telusko.project.Model.task;
import org.telusko.project.Service.TaskService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class taskController {

    @Autowired
    private final TaskService taskService;

    public taskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponse> getAllTasks(@RequestParam(required = false) String status) {
        task.Status taskStatus = status == null ? null : taskService.parseStatus(status);
        return taskService.findAllTasks(taskStatus).stream()
                .map(TaskResponse::from)
                .toList();
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        return TaskResponse.from(taskService.findTaskById(id));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> addTask(@Valid @RequestBody TaskRequest request) {
        task savedTask = taskService.createTask(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedTask.getId())
                .toUri();
        return ResponseEntity.created(location)
                .body(TaskResponse.from(savedTask));
    }

    @PutMapping("/{id}")
    public TaskResponse replaceTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request
    ) {
        return TaskResponse.from(taskService.replaceTask(id, request));
    }

    @PatchMapping("/{id}")
    public TaskResponse patchTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskPatchRequest request
    ) {
        return TaskResponse.from(taskService.patchTask(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
