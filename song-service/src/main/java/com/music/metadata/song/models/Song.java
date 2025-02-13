package com.music.metadata.song.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "metadata")
public class Song {

    @Column(name = "name")
    private String name;

    @Column(name = "artist")
    private String artist;

    @Column(name = "album")
    private String album;

    @Column(name = "duration")
    private String duration;

    @Column(name = "year")
    private String year;

    @Id
    @Column(name = "id", unique = true)
    private Long id;
}
