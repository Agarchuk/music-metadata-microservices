package com.music.metadata.resource.mappers;

import com.music.metadata.resource.dtos.ResourceUploadResponse;
import com.music.metadata.resource.dtos.ResourcesDeleteResponse;
import org.mapstruct.Mapper;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ResourceMapper {

    ResourceUploadResponse mapToResourceUploadResponse(Long id);

    default ResourcesDeleteResponse mapToResourcesDeleteResponse(List<Long> ids) {
        return new ResourcesDeleteResponse(ids);
    }

    default List<Long> mapCsvToIdList(String ids) {
        return Arrays.stream(ids.split(","))
                .map(String::trim)
                .map(Long::parseLong)
                .toList();
    }
}
