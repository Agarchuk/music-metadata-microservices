package com.music.metadata.resource.services;

import com.music.metadata.resource.clients.SongsClient;
import com.music.metadata.resource.dtos.ResourceUploadResponse;
import com.music.metadata.resource.dtos.ResourcesDeleteResponse;
import com.music.metadata.resource.exceptions.ResourceNotFoundException;
import com.music.metadata.resource.mappers.ResourceMapper;
import com.music.metadata.resource.models.Resource;
import com.music.metadata.resource.repositories.ResourceRepository;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@AllArgsConstructor
public class ResourceServer {

    private static final String ALLOWED_CONTENT_TYPE = "audio/mpeg";

    private final Tika tika = new Tika();
    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;
    private final SongsClient mp3MetadataService;

    public byte[] getResource(@Positive Long id) {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found with id: " + id));
        return resource.getData();
    }

    public ResourceUploadResponse uploadResource(byte[] file) throws Exception {
        validateFileType(file);

        Resource resource = new Resource();
        resource.setData(file);
        resource.setContentType(ALLOWED_CONTENT_TYPE);
        resource.setSize((long) file.length);

        Resource savedResource = resourceRepository.save(resource);

        mp3MetadataService.extractAndSaveMetadata(savedResource.getData(), savedResource.getId());

        return resourceMapper.mapToResourceUploadResponse(savedResource.getId());
    }

    @Transactional
    public ResourcesDeleteResponse deleteResources(String ids) {
        validateCsvLength(ids);
        List<Long> idList = resourceMapper.mapCsvToIdList(ids);
        
        List<Long> existingIds = idList.stream()
                .filter(resourceRepository::existsById)
                .toList();
        
        resourceRepository.deleteAllById(existingIds);

        mp3MetadataService.deleteSongMetadata(ids);
        return resourceMapper.mapToResourcesDeleteResponse(existingIds);
    }

    private void validateFileType(byte[] file) {
        String detectedType = tika.detect(file);
        if (!ALLOWED_CONTENT_TYPE.equals(detectedType)) {
            throw new IllegalArgumentException("Invalid file type. Only MP3 files are allowed");
        }
    }

    private void validateCsvLength(String ids) {
        if (ids.length() > 200) {
            throw new IllegalArgumentException("CSV string length must be less than 200 characters.");
        }
    }
}
