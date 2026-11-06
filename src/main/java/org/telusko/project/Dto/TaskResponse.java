package org.telusko.project.Dto;

import org.telusko.project.Model.task;

import java.sql.Timestamp;

public record TaskResponse(
        Long id,
        String description,
        task.Status status,
        Timestamp createdAt,
        Timestamp updatedAt
) {
    public static TaskResponse from(task task) {
        return new TaskResponse(
                task.getId(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}
