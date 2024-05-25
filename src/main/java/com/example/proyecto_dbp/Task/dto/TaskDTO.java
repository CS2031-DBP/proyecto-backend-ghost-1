package com.example.proyecto_dbp.Task.dto;

import com.example.proyecto_dbp.Activity.dto.ActivityDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TaskDTO extends ActivityDTO {
    @NotNull
    @Size(min = 1, max = 255)
    private String priority;

    @NotNull
    private Boolean completed;
}
