package faang.school.projectservice.controller;

import faang.school.projectservice.config.context.UserContext;
import faang.school.projectservice.dto.ResourceResponseDto;
import faang.school.projectservice.service.ResourceService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/projects/{projectId}/resources")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ResourceController {

    private final ResourceService resourceService;
    private final UserContext userContext;

    @PostMapping
    public ResourceResponseDto uploadNew(@PathVariable @Positive Long projectId,
                                         @RequestParam MultipartFile file) {

        log.info("Received request to ResourceController.uploadNew -- projectId={}, fileName={}, content-type={}",
                projectId, file.getOriginalFilename(), file.getContentType());
        return resourceService.uploadNew(userContext.getUserId(), projectId, file);
    }
}
