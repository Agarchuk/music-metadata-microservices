package com.music.metadata.song.mappers;

import com.music.metadata.song.dtos.SongCreateResponse;
import com.music.metadata.song.dtos.SongDTO;
import com.music.metadata.song.dtos.SongDeleteResponse;
import com.music.metadata.song.models.Song;
import org.mapstruct.Mapper;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SongMapper {

    SongCreateResponse mapToResourceUploadResponse(Long id);
    SongDTO mapToResourceUploadResponse(Song song);

    default SongDeleteResponse mapToResourcesDeleteResponse(List<Long> ids) {
        return new SongDeleteResponse(ids);
    }

    default List<Long> mapCsvToIdList(String ids) {
        return Arrays.stream(ids.split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .toList();
    }
}
