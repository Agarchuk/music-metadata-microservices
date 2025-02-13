package com.music.metadata.resource.clients;

import com.music.metadata.resource.dtos.SongCreateRequest;
import lombok.AllArgsConstructor;
import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.ByteArrayInputStream;
import java.util.Map;

@AllArgsConstructor
@Component
public class SongsClient {

    private final WebClient songServiceClient;

    public void extractAndSaveMetadata(byte[] mp3Data, Long resourceId) throws Exception {
        Metadata metadata = parseMp3Metadata(mp3Data);
        SongCreateRequest songCreateRequest = createSongCreateRequest(resourceId, metadata);
        sendSongCreateRequest(songCreateRequest);
    }

    public void deleteSongMetadata(String requestParam) {
        songServiceClient.delete()
                .uri("/songs?id=" + requestParam)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

    private Metadata parseMp3Metadata(byte[] mp3Data) throws Exception {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext context = new ParseContext();
        Parser parser = new Mp3Parser();

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(mp3Data)) {
            parser.parse(inputStream, handler, metadata, context);
        }
        return metadata;
    }

    private SongCreateRequest createSongCreateRequest(Long resourceId, Metadata metadata) {
        return new SongCreateRequest(resourceId, metadata.get("dc:title"),
                metadata.get("xmpDM:artist"),
                metadata.get("xmpDM:album"),
                convertDurationStringToMMSS(metadata.get("xmpDM:duration")),
                metadata.get("xmpDM:releaseDate"));
    }

    private void sendSongCreateRequest(SongCreateRequest songCreateRequest) {
        songServiceClient.post()
                .uri("/songs")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(songCreateRequest)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    private String convertDurationStringToMMSS(String durationStr) {
        try {
            double durationInSeconds = Double.parseDouble(durationStr);
            int minutes = (int) (durationInSeconds / 60);
            int seconds = (int) (durationInSeconds % 60);
            return String.format("%02d:%02d", minutes, seconds);
        } catch (NumberFormatException e) {
            System.err.println("Error: incorrect format: " + durationStr);
            return "00:00";
        }
    }
}