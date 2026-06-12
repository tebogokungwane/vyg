package com.vyg.service;

import com.vyg.dto.AssignRoleRequest;
import com.vyg.entity.*;
import com.vyg.exception.ResourceNotFoundException;
import com.vyg.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectRoleService {

    private final ProjectMemberRoleRepository roleRepository;
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;

    public ProjectRoleService(ProjectMemberRoleRepository roleRepository,
                              ProjectRepository projectRepository,
                              MemberRepository memberRepository,
                              AddressRepository addressRepository) {
        this.roleRepository = roleRepository;
        this.projectRepository = projectRepository;
        this.memberRepository = memberRepository;
        this.addressRepository = addressRepository;
    }

    public List<ProjectMemberRole> getRolesByProject(Long projectId) {
        return roleRepository.findByProjectId(projectId);
    }

    public ProjectMemberRole assignRole(Long projectId, AssignRoleRequest request) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        Members member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        Address address = request.getAddressId() != null
                ? addressRepository.findById(request.getAddressId()).orElse(null)
                : null;

        ProjectMemberRole role = ProjectMemberRole.builder()
                .project(project)
                .member(member)
                .role(request.getRole())
                .level(request.getLevel())
                .address(address)
                .build();
        return roleRepository.save(role);
    }

    public ProjectMemberRole updateRole(Long projectId, Long roleId, AssignRoleRequest request) {
        ProjectMemberRole role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        if (request.getMemberId() != null) {
            Members member = memberRepository.findById(request.getMemberId())
                    .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
            role.setMember(member);
        }
        if (request.getRole() != null) role.setRole(request.getRole());
        if (request.getLevel() != null) role.setLevel(request.getLevel());
        if (request.getAddressId() != null) {
            Address address = addressRepository.findById(request.getAddressId()).orElse(null);
            role.setAddress(address);
        }
        return roleRepository.save(role);
    }

    public void removeRole(Long roleId) {
        roleRepository.deleteById(roleId);
    }

    public List<ProjectMemberRole> getFullHierarchy() {
        return roleRepository.findAll();
    }

    public void transferBranch(Long memberId, Long newAddressId) {
        Members member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
        Address address = addressRepository.findById(newAddressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
        member.setAddress(address);
        memberRepository.save(member);
    }
}
