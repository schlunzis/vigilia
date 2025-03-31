package org.schlunzis.vigilia.core.api;

import lombok.extern.slf4j.Slf4j;
import org.schlunzis.vigilia.core.io.MediaType;
import org.schlunzis.vigilia.core.io.SupportedFile;
import org.schlunzis.vigilia.core.model.SupportedMediaTypesDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Service
public class SystemService implements SystemApiDelegate {

    @Override
    public ResponseEntity<SupportedMediaTypesDTO> getMediaTypes() {

        Map<MediaType, List<SupportedFile>> groupedSupportedMediaFiles = Arrays.stream(SupportedFile.values()).collect(groupingBy(SupportedFile::getMediaType));

        for (MediaType mediaType : MediaType.values()) {
            groupedSupportedMediaFiles.computeIfAbsent(mediaType, _ -> List.of());
        }

        return ResponseEntity.ok(new SupportedMediaTypesDTO()
                .image(groupedSupportedMediaFiles.get(MediaType.IMAGE).stream().flatMap(sft -> Arrays.stream(sft.getFileExtensions())).toList())
                .document(groupedSupportedMediaFiles.get(MediaType.DOCUMENT).stream().flatMap(sft -> Arrays.stream(sft.getFileExtensions())).toList())
                .video(groupedSupportedMediaFiles.get(MediaType.VIDEO).stream().flatMap(sft -> Arrays.stream(sft.getFileExtensions())).toList())
                .audio(groupedSupportedMediaFiles.get(MediaType.AUDIO).stream().flatMap(sft -> Arrays.stream(sft.getFileExtensions())).toList())
        );
    }
}
