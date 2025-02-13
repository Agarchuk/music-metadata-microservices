package com.music.metadata.song.dtos;

public record SongDTO(
        String id,String name, String artist, String album, String duration, String year
    ) {
}
