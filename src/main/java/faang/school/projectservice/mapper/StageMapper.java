package faang.school.projectservice.mapper;

import faang.school.projectservice.dto.stage.StageDto;
import faang.school.projectservice.model.Task;
import faang.school.projectservice.model.TeamMember;
import faang.school.projectservice.model.stage.Stage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Stream;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StageMapper {
    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "executors", target = "executorIds", qualifiedByName = "mapExecutor")
    StageDto toDto(Stage stage);

    List<StageDto> toDtoList(List<Stage> stages);

    @Mapping(source = "projectId", target = "project.id")
    @Mapping(target = "executors", ignore = true)
    @Mapping(target = "stageRoles", ignore = true)
    Stage toEntity(StageDto stageDto);

    @Named("mapExecutor")
    default List<Long> mapExecutor(List<TeamMember> teamMembers) {
        return teamMembers.stream().map(TeamMember::getId).toList();
    }
}
