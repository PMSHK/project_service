package faang.school.projectservice.mapper;

import faang.school.projectservice.dto.client.ResourceDto;
import faang.school.projectservice.model.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "Spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResourceMapper {
    @Mapping(source = "project.id", target = "projectId")
    ResourceDto resourceToDto(Resource resource);
}
