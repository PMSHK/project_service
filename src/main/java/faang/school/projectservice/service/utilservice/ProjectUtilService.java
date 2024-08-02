package faang.school.projectservice.service.utilservice;

import faang.school.projectservice.exception.ConflictException;
import faang.school.projectservice.exception.ErrorMessage;
import faang.school.projectservice.exception.NotFoundException;
import faang.school.projectservice.jpa.ProjectJpaRepository;
import faang.school.projectservice.model.Project;
import faang.school.projectservice.model.ProjectStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectUtilService {

    private final ProjectJpaRepository projectJpaRepository;

    @Transactional(readOnly = true)
    public Project getById(long id) {
        return projectJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Project id=%d not found", id))
                );
    }

    public Project save(Project project) {
        return projectJpaRepository.save(project);
    }

    public List<Project> getAllByIdsStrictly(Collection<Long> ids) {
        List<Project> projects = projectJpaRepository.findAllById(ids);
        if (ids.size() != projects.size()) {
            throw new NotFoundException(ErrorMessage.SOME_OF_PROJECTS_NOT_EXIST);
        }
        return projects;
    }

    public List<Project> findAllDistinctByTeamMemberIds(Collection<Long> teamMemberIds) {
        return projectJpaRepository.findAllDistinctByTeamMemberIds(teamMemberIds);
    }

    public void checkProjectsNotClosed(Collection<Project> projects) {
        if (!verifyProjectsNotIncludeStatuses(projects, ProjectStatus.COMPLETED, ProjectStatus.CANCELLED)) {
            throw new ConflictException(ErrorMessage.PROJECT_STATUS_INVALID);
        }
    }

    public void checkProjectsFitTeamMembers(Collection<Long> projectIds, Collection<Long> teamMemberIds) {
        // Проверяем относятся ли проекты к переданным пользователям, если существует проект, к
        // которому не относится ни один из пользователей - бросаем исключение
        // Лишний проект / недостающие мемберы

        Set<Long> projectIdsFromMembers =
                findAllDistinctByTeamMemberIds(teamMemberIds).stream()
                        .map(Project::getId)
                        .collect(Collectors.toSet());
        boolean isValid = projectIdsFromMembers.containsAll(projectIds);
        if (!isValid) {
            throw new ConflictException(ErrorMessage.PROJECTS_UNFIT_MEMBERS);
        }
    }

    private boolean verifyProjectsNotIncludeStatuses(Collection<Project> projects, ProjectStatus... invalidStatuses) {
        Set<ProjectStatus> invalidStatusesSet = Set.of(invalidStatuses);
        return projects.stream().noneMatch(project -> invalidStatusesSet.contains(project.getStatus()));
    }
}
