package com.music.metadata.song.services;

import com.music.metadata.song.dtos.SongCreateRequest;
import com.music.metadata.song.dtos.SongCreateResponse;
import com.music.metadata.song.dtos.SongDeleteResponse;
import com.music.metadata.song.exceptions.EntityAlreadyExistsException;
import com.music.metadata.song.exceptions.ResourceNotFoundException;
import com.music.metadata.song.mappers.SongMapper;
import com.music.metadata.song.models.Song;
import com.music.metadata.song.repositories.SongRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class SongService {

    private SongRepository songRepository;
    private SongMapper songManager;

    @Transactional
    public SongCreateResponse createSongMetadata(SongCreateRequest createSongMetadataRequest) {
        if (songRepository.existsById(createSongMetadataRequest.id())) {
            throw new EntityAlreadyExistsException("Song with ID " + createSongMetadataRequest.id() + " already exists.");
        }
        Song song =new Song();
        song.setId(createSongMetadataRequest.id());
        song.setAlbum(createSongMetadataRequest.album());
        song.setArtist(createSongMetadataRequest.artist());
        song.setName(createSongMetadataRequest.name());
        song.setDuration(createSongMetadataRequest.duration());
        song.setYear(createSongMetadataRequest.year());


        Song savedSong = songRepository.save(song);
        return new SongCreateResponse(savedSong.getId());
    }

    public Song getSongMetadata(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));
    }

    public SongDeleteResponse deleteSongsMetadata(String ids) {
        validateCsvLength(ids);
        validateCommaSeparatedIds(ids);
        List<Long> idList = songManager.mapCsvToIdList(ids);

        List<Long> existingIds = idList.stream()
                .filter(songRepository::existsById)
                .toList();

        songRepository.deleteAllById(existingIds);
        return songManager.mapToResourcesDeleteResponse(existingIds);
    }

    private void validateCommaSeparatedIds(String ids) {
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            if (!id.trim().matches("\\d+")) {
                throw new IllegalArgumentException("Invalid ID format in CSV: " + id);
            }
        }
    }

    private void validateCsvLength(String ids) {
        if (ids.length() > 200) {
            throw new IllegalArgumentException("CSV string length must be less than 200 characters.");
        }
    }
}
