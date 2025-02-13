package com.music.metadata.resource.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.music.metadata.resource.dtos.ResourceUploadResponse;
import com.music.metadata.resource.dtos.ResourcesDeleteResponse;
import com.music.metadata.resource.services.ResourceServer;

@RestController
@RequestMapping("/resources")
public class ResourceController {

    private static final String ALLOWED_CONTENT_TYPE = "audio/mpeg";

    @Autowired
    private ResourceServer resourceServer;

    @GetMapping(value = "/{id}", produces = ALLOWED_CONTENT_TYPE)
    public ResponseEntity<byte[]> getResource(@PathVariable Long id) {
        return ResponseEntity.ok().body(resourceServer.getResource(id));
    }

    @PostMapping(consumes = ALLOWED_CONTENT_TYPE)
    public ResponseEntity<ResourceUploadResponse> uploadResource(
            @RequestBody byte[] file) throws Exception {
        return ResponseEntity.ok(resourceServer.uploadResource(file));
    }

    @DeleteMapping
    public ResponseEntity<ResourcesDeleteResponse> deleteResources(@RequestParam String id) {
        return ResponseEntity.ok(resourceServer.deleteResources(id));
    }
}
