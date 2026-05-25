package com.vyg.service;

import com.vyg.dto.MentorDTO;
import com.vyg.dto.UnassignedMenteeDTO;
import com.vyg.entity.*;
import com.vyg.enumerator.Role;
import com.vyg.mapper.MemberMapper;
import com.vyg.dto.MemberRequest;
import com.vyg.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final NationsRepository nationRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;


    public Members createMember(MemberRequest memberRequest) throws Exception {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Members loggedInUser = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Logged-in user not found"));
        memberRequest.setAddressId(loggedInUser.getAddress().getId());

        if (memberRequest.getAddressId() == null) {
            log.error("Failed to create member: addressId is null");
            throw new IllegalArgumentException("Address ID is mandatory for registration.");
        }

        Address address = addressRepository.findById(memberRequest.getAddressId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid address ID"));

        Members mentor = null;
        if (memberRequest.getMentorId() != null) {
            mentor = memberRepository.findById(memberRequest.getMentorId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid mentor ID"));
        }

        Nations nation = null;
        if (memberRequest.getNationId() != null) {
            nation = nationRepository.findById(memberRequest.getNationId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid nation ID"));
        }

        Members saveMember = MemberMapper.toEntity(memberRequest, address, mentor, nation, passwordEncoder);

        Members saved = memberRepository.save(saveMember);

        if (saved.getEmail() != null) {
            String rawPassword = memberRequest.getPassword() != null ? memberRequest.getPassword() : "VYG@123";
            emailService.sendWelcomeEmail(saved.getEmail(), saved.getName(), rawPassword);
        }

        return saved;
    }


    @Override
    public Page<Members> getAllMembers(Pageable pageable) {
        if(pageable == null){
            pageable = Pageable.unpaged();

        }
        return memberRepository.findAll(pageable);
    }

    @Override
    public boolean deleteMember(long id) {
        Members byId = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        memberRepository.delete(byId);
        return true;
    }

    @Override
    public Members updateMember(long id, MemberRequest memberRequest) {

        Members member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        if (memberRequest.getName() != null) {
            member.setName(memberRequest.getName());
        }

        if (memberRequest.getSurname() != null) {
            member.setSurname(memberRequest.getSurname());
        }

        if (memberRequest.getGender() != null) {
            member.setGender(memberRequest.getGender());
        }

        if (memberRequest.getRole() != null) {
            member.setRole(memberRequest.getRole());
        }

        if (memberRequest.getCellNumber() != null) {
            member.setCellNumber(memberRequest.getCellNumber());
        }

        if (memberRequest.getNationId() != null) {
            Nations nation = nationRepository.findById(memberRequest.getNationId())
                    .orElseThrow(() -> new IllegalArgumentException("Nation not found"));
            member.setNation(nation);
        }

        if (memberRequest.getResidentialAddress() != null) {
            member.setResidentialAddress(memberRequest.getResidentialAddress());
        }

        member.setActive(memberRequest.isActive());

        return memberRepository.save(member);
    }



    @Override
    public List<Members> getMembersUnderMentor(Long mentorId) {
        return memberRepository.findByMentorId(mentorId);
    }

    @Override
    public List<Members> getAllMentors() {
        return memberRepository.findByRole(Role.MENTOR);

    }

    @Override
    @Transactional(readOnly = true)
    public Members login(String email, String password) {
        Members member = memberRepository.findByEmail(email).orElse(null);
        if (member == null || !passwordEncoder.matches(password, member.getPassword())) {
            return null;
        }
        return member;
    }


    @Override
    public List<Members> getMentorsByProvinceAndRegion(Role role, String provinceName, String regionName) {
        return List.of();
    }

    @Override
    public Members createMentee(MemberRequest request) {
        // 🔎 Find the mentor
        Optional<Members> mentorOpt = memberRepository.findById(request.getMentorId());
        if (mentorOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid Mentor ID: " + request.getMentorId());
        }

        memberRepository.findById(request.getMentorId());

        Members mentor = mentorOpt.get(); // ✅ Get the mentor

        // ✅ Create a new mentee & match mentor's branch, province, and region
        Members mentee = Members.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .gender(request.getGender())
                .cellNumber(request.getCellNumber())
//                .province(mentor.getProvince())  // ✅ Inherit province from mentor
//                .region(mentor.getRegion())      // ✅ Inherit region from mentor
//                .branch(mentor.getBranch())      // ✅ Inherit branch from mentor
                .role(Role.MEMBER)               // ✅ Ensure role is MEMBER
                .nation(mentor.getNation())
                .password(request.getPassword())
                .isActive(request.isActive())
                .mentor(mentor)                  // ✅ Link mentee to mentor
                .build();

        // ✅ Save mentee
        Members savedMentee = memberRepository.save(mentee);

        // ✅ Add mentee to mentor's mentees list and save
        mentor.getMentees().add(savedMentee);
        memberRepository.save(mentor);

        return savedMentee;
    }

    @Transactional
    public List<Members> getMentorsByAddress(long addressId) {
        return memberRepository.findByRoleAndAddressId(Role.MENTOR, addressId);
    }


    @Override
    public List<Members> getAllMembersByAddress(long addressId) {
        return memberRepository.findByRoleAndAddressId(Role.MEMBER, addressId);
    }
    @Override
    public List<Members> findByAddressId(Long addressId) {
        return memberRepository.findByAddressId(addressId);
    }

    @Override
    public Page<Members> getAllMembersByAddress(Long addressId, Pageable pageable) {
        return memberRepository.findAllByAddressId(addressId, pageable);

    }

    @Override
    public Page<Members> getAllSavedMembersByAddress(Long addressId, Pageable pageable) {
        return memberRepository.findAllByAddressId(addressId, pageable);
    }

    public Members findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id " + id));
    }

    /* ================= UNASSIGNED MENTEES ================= */
    public List<UnassignedMenteeDTO> getUnassignedMentees(Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Invalid Address ID: " + addressId
                ));

        return memberRepository
                .findUnassignedMenteesByAddress(address.getId())
                .stream()
                .map(m -> new UnassignedMenteeDTO(
                        m.getId(),
                        m.getName(),
                        m.getSurname(),
                        m.getCellNumber(),
                        m.getNation().getNation()
                ))
                .collect(Collectors.toList());
    }

    /* ================= MENTORS WITH MENTEES ================= */
    public Page<MentorDTO> getMentorsWithMentees(
            Long addressId,
            int page,
            int size
    ) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Invalid Address ID: " + addressId
                ));

        PageRequest pageable = PageRequest.of(page, size);

        Page<Object[]> results =
                memberRepository.findMentorsWithMenteeCount(
                        address.getId(),
                        pageable
                );

        List<MentorDTO> dtos = results.getContent().stream()
                .map(row -> new MentorDTO(
                        ((Number) row[0]).longValue(),   // mentor id
                        (String) row[1],                 // name
                        (String) row[2],                 // surname
                        (String) row[3],                 // cell
                        (String) row[4],                 // nation
                        (String) row[5],                 // address
                        ((Number) row[6]).longValue()    // mentee count
                ))
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, results.getTotalElements());
    }


}
