package com.vyg.service;

import com.vyg.entity.*;
import com.vyg.enumerator.Gender;
import com.vyg.enumerator.Role;
import com.vyg.mapper.MemberMapper;
import com.vyg.dto.MemberRequest;
import com.vyg.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // ✅ Correct

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private AddressRepository addressRepository;


    public Members createMember(MemberRequest memberRequest) throws Exception {

        Address address = addressRepository.findById(memberRequest.getAddressId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid address ID"));

        Members mentor = null;
        if(memberRequest.getMentorId() !=0){
            mentor = memberRepository.findById(memberRequest.getMentorId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid mentor ID"));
        }

        Members saveMember = MemberMapper.toEntity(memberRequest, address, mentor);

        return memberRepository.save(saveMember);
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
        Optional<Members> memberById = memberRepository.findById(id);
        if(memberById.isEmpty()){
            throw new IllegalArgumentException("Member not found");
        }

        Members updateMemberById = memberById.get();

        if(memberRequest.getRole() != null){
            updateMemberById.setRole(Role.valueOf(memberRequest.getRole().name()));
        }

        updateMemberById.setName(memberRequest.getName());
        updateMemberById.setSurname(memberRequest.getSurname());
        updateMemberById.setGender(Gender.valueOf(memberRequest.getGender().name()));
        updateMemberById.setCellNumber(memberRequest.getCellNumber());
        updateMemberById.setNation(memberRequest.getNation());
        updateMemberById.setPassword(memberRequest.getPassword());
        updateMemberById.setActive(memberRequest.isActive());
        updateMemberById.setResidentialAddress(memberRequest.getResidentialAddress());

        memberRepository.save(updateMemberById);

        return updateMemberById;
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
        Members loggedIn = memberRepository.findByEmailAndPassword(email, password).orElse(null);

        log.info(String.valueOf(loggedIn));

        return loggedIn;
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

}
