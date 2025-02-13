package com.music.metadata.song.controllers;

import com.music.metadata.song.dtos.SongDTO;
import com.music.metadata.song.dtos.SongDeleteResponse;
import com.music.metadata.song.services.SongService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.music.metadata.song.dtos.SongCreateRequest;
import com.music.metadata.song.dtos.SongCreateResponse;

@RestController
@RequestMapping("/songs")
public class SongController {

    @Autowired
    private SongService metadataService;

    @PostMapping
    public ResponseEntity<SongCreateResponse> createSongMetadata(
            @Valid @RequestBody SongCreateRequest createSongMetadataRequest) {
        return ResponseEntity.ok(metadataService.createSongMetadata(createSongMetadataRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongDTO> getSongMetadata(@PathVariable Long id) {
        return ResponseEntity.ok(metadataService.getSongMetadata(id));
    }

    @DeleteMapping
    public ResponseEntity<SongDeleteResponse> deleteSongsMetadata(@RequestParam String id) {
        return ResponseEntity.ok(metadataService.deleteSongsMetadata(id));
    }
}
