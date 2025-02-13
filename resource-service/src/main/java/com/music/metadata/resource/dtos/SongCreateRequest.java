package com.music.metadata.resource.dtos;

import jakarta.validation.constraints.*;

public record SongCreateRequest(

        @NotBlank(message = "Id is required")
        Long id,

        @NotBlank(message = "Name is required")
        @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
        String name,

        @NotBlank(message = "Artist is required")
        @Size(min = 1, max = 100, message = "Artist must be between 1 and 100 characters")
        String artist,

        @NotBlank(message = "Album is required")
        @Size(min = 1, max = 100, message = "Album must be between 1 and 100 characters")
        String album,

        @NotBlank(message = "Duration is required")
        @Pattern(regexp = "^(0[0-9]|[1-9][0-9]):([0-5][0-9])$", message = "Invalid duration format. Must be mm:ss")
        String duration,

        @NotNull(message = "Year is required")
        @Min(value = 1900, message = "Year must be between 1900 and 2099")
        @Max(value = 2099, message = "Year must be between 1900 and 2099")
        String year
    ) {
}
