package com.vyg.service;

import com.vyg.dto.ProjectDTO;
import com.vyg.entity.Members;
import com.vyg.entity.Project;
import com.vyg.exception.ResourceNotFoundException;
import com.vyg.repository.MemberRepository;
import com.vyg.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberRepository memberRepository;

    // GET all projects
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream().map(p -> {
            ProjectDTO dto = new ProjectDTO();
            dto.setId(p.getId());
            dto.setName(p.getName());
            dto.setImageName(p.getImageName());
            dto.setMemberCount(p.getMembers() != null ? p.getMembers().size() : 0);
            return dto;
        }).collect(Collectors.toList());
    }

    // GET members of a project
    public List<Members> getMembersByProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return project.getMembers();
    }

    // GET project image
    public byte[] getProjectImage(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return project.getImageData();
    }

    public String getProjectImageType(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return project.getImageType();
    }

    // POST create project
    public Project createProject(String name, MultipartFile image) throws IOException {
        Project project = new Project();
        project.setName(name);
        if (image != null && !image.isEmpty()) {
            project.setImageName(image.getOriginalFilename());
            project.setImageType(image.getContentType());
            project.setImageData(image.getBytes());
        }
        return projectRepository.save(project);
    }

    // PUT update project
    public Project updateProject(Long id, String name, MultipartFile image) throws IOException {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        project.setName(name);
        if (image != null && !image.isEmpty()) {
            project.setImageName(image.getOriginalFilename());
            project.setImageType(image.getContentType());
            project.setImageData(image.getBytes());
        }
        return projectRepository.save(project);
    }

    // DELETE project
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    // PUT assign member to project
    public void assignMember(Long projectId, Long memberId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        Members member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        project.getMembers().add(member);
        projectRepository.save(project);
    }

    // POST add member to project (with duplicate check)
    public String addMember(Long projectId, Long memberId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        Members member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        if (!project.getMembers().contains(member)) {
            project.getMembers().add(member);
            projectRepository.save(project);
        }
        return "Member added successfully";
    }

    // GET unassigned members by address
    public List<Members> getUnassignedMembersByAddress(Long addressId) {
        List<Members> allMembers = memberRepository.findByAddressId(addressId);
        List<Long> assignedIds = projectRepository.findAllAssignedMemberIds();
        return allMembers.stream()
                .filter(m -> !assignedIds.contains(m.getId()))
                .collect(Collectors.toList());
    }

    // PUT move member between projects
    public void moveMember(Long memberId, Long fromProjectId, Long toProjectId) {
        Project fromProject = projectRepository.findById(fromProjectId)
                .orElseThrow(() -> new RuntimeException("Source project not found"));
        Project toProject = projectRepository.findById(toProjectId)
                .orElseThrow(() -> new RuntimeException("Target project not found"));
        Members member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        fromProject.getMembers().remove(member);
        toProject.getMembers().add(member);

        projectRepository.save(fromProject);
        projectRepository.save(toProject);
    }
}