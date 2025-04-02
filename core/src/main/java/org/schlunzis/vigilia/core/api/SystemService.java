package org.schlunzis.vigilia.core.api;

import io.swagger.v3.oas.models.OpenAPI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.vigilia.core.io.MediaType;
import org.schlunzis.vigilia.core.io.SupportedFile;
import org.schlunzis.vigilia.core.model.CoreVersionDTO;
import org.schlunzis.vigilia.core.model.SupportedMediaTypesDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemService implements SystemApiDelegate {

    private final OpenAPI openAPI;

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

    @Override
    public ResponseEntity<CoreVersionDTO> getVersion() {
        String serviceVersion = getClass().getPackage().getImplementationVersion();
        if (serviceVersion == null) {
            log.warn("No implementation version found. There might be a problem with the build process.");
            serviceVersion = "unknown";
        }
        String apiVersion = openAPI.getInfo().getVersion();

        CoreVersionDTO coreVersionDTO = new CoreVersionDTO()
                .serviceVersion(serviceVersion)
                .apiVersion(apiVersion);
        return ResponseEntity.ok(coreVersionDTO);
    }
}
