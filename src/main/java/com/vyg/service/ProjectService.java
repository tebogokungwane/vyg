package com.vyg.service;

import com.vyg.dto.ProjectDTO;
import com.vyg.entity.Members;
import com.vyg.entity.Project;
import com.vyg.repository.MemberRepository;
import com.vyg.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberRepository memberRepository;

    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
    }

    public Project createProject(String name, MultipartFile image) throws IOException {
        Project project = new Project();
        project.setName(name);

        if (image != null && !image.isEmpty()) {
            project.setImageName(image.getOriginalFilename());
            project.setImageData(image.getBytes());
            project.setImageType(image.getContentType());
        }

        return projectRepository.save(project);
    }

    public Project updateProject(Long id, String name, MultipartFile image) throws IOException {
        Project project = getProjectById(id);
        project.setName(name);

        if (image != null && !image.isEmpty()) {
            project.setImageName(image.getOriginalFilename());
            project.setImageData(image.getBytes());
            project.setImageType(image.getContentType());
        }

        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    public List<Members> getMembersByProject(Long projectId) {
        Project project = getProjectById(projectId);
        return project.getMembers();
    }

    public void moveMember(Long memberId, Long fromProjectId, Long toProjectId) {
        Members member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));

        Project fromProject = getProjectById(fromProjectId);
        Project toProject = getProjectById(toProjectId);

        if (!fromProject.getMembers().remove(member)) {
            throw new RuntimeException("Member is not in the source project");
        }

        toProject.getMembers().add(member);

        projectRepository.save(fromProject);
        projectRepository.save(toProject);
    }

    private ProjectDTO toDTO(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .imageName(project.getImageName())
                .imageType(project.getImageType())
                .memberCount(project.getMembers() != null ? project.getMembers().size() : 0)
                .build();
    }
}
