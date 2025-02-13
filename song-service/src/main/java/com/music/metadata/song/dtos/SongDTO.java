package com.music.metadata.song.dtos;

public record SongDTO(
        Long id, String name, String artist, String album, String duration, String year
    ) {
}
