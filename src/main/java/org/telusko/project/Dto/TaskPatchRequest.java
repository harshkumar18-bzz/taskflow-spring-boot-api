package org.telusko.project.Dto;

import jakarta.validation.constraints.Size;
import org.telusko.project.Model.task;

public record TaskPatchRequest(
        @Size(max = 200)
        String description,
        task.Status status
) {
}
