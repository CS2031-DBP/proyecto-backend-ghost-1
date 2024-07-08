package com.example.proyecto_dbp.Task.dto;

import com.example.proyecto_dbp.Activity.dto.ActivityDTO;
import com.example.proyecto_dbp.Course.domain.Course;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
public class TaskDTO extends ActivityDTO {

    @NotNull
    @Size(min = 1, max = 255)
    private String priority;

    @NotNull
    private Boolean completed;

}
