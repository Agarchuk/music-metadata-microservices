package com.music.metadata.song.dtos;

import jakarta.validation.constraints.*;

public record SongCreateRequest(

        @NotNull(message = "Resource ID is required")
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
        @Pattern(regexp = "^(19|20)\\d{2}$", message = "Year must be in a YYYY format")
        String year
    ) {
}
