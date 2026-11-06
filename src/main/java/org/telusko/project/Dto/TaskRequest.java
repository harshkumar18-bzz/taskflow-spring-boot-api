package org.telusko.project.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.telusko.project.Model.task;

public record TaskRequest(
        @NotBlank
        @Size(max = 100)
        String description,

        @NotNull
        task.Status status
) {
}
