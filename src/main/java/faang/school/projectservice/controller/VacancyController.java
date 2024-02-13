package faang.school.projectservice.controller;

import faang.school.projectservice.dto.client.VacancyDto;
import faang.school.projectservice.mapper.VacancyMapper;
import faang.school.projectservice.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;
    private final VacancyMapper vacancyMapper;

    @PostMapping("/vacancy")
    public VacancyDto createVacancy(@RequestBody VacancyDto vacancyDto,
                                    @RequestParam("createdBy") Long createdBy) {
        return vacancyService.createVacancy(vacancyDto, createdBy);
    }

    @PutMapping("/vacancy")
    public void updateVacancy(@RequestBody VacancyDto vacancyDto) {
        vacancyService.updateVacancy(vacancyDto);
    }

    @DeleteMapping("/vacancy/{id}")
    public void deleteVacancy(@PathVariable("id") Long id) {
        vacancyService.deleteVacancy(id);
    }

    @GetMapping("/vacancy")
    public List<VacancyDto> getVacanciesWithFilters(@RequestParam("name") String name,
                                                    @RequestParam("position") String position) {
        return vacancyService.getVacanciesWithFilters(name, position);
    }

    @GetMapping("/vacancy/{id}")
    public VacancyDto getVacancy(@PathVariable("id") Long id) {
        return vacancyMapper.toDto(vacancyService.getVacancy(id));
    }
}
