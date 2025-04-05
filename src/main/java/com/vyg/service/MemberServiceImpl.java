package com.vyg.service;

import com.vyg.entity.*;
import com.vyg.enumerator.Gender;
import com.vyg.enumerator.Nation;
import com.vyg.enumerator.Role;
import com.vyg.model.MemberRequest;
import com.vyg.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private AddressRepository addressRepository;


    public MemberServiceImpl(MemberRepository memberRepository ,AddressRepository addressRepository) {
        this.memberRepository = memberRepository;
        this.addressRepository = addressRepository;
    }

    public Members createMember(MemberRequest memberRequest) throws Exception {

        Members member = new Members();

        Address address = addressRepository.findById(memberRequest.getAddressId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid address ID"));


        member.setName(memberRequest.getName());
        member.setSurname(memberRequest.getSurname());
        member.setGender(memberRequest.getGender());
        member.setCellNumber(memberRequest.getCellNumber());
        member.setEmail(memberRequest.getEmail());


        member.setAddress(address);

        member.setRole(memberRequest.getRole());



        if (memberRequest.getMentorId() != 0) {
            Members mentor = memberRepository.findById(memberRequest.getMentorId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid mentor ID"));
            member.setMentor(mentor); // ✅ Assign full object
        }

        member.setNation(memberRequest.getNation());
        member.setResidentialAddress(memberRequest.getResidentialAddress());
        member.setPassword("VYG@123");
        member.setActive(memberRequest.isActive());
        member.setDateCreated(LocalDateTime.now()); // ✅ date and time now
        member.setCapturedBy(memberRequest.getCreateBy()); // ✅ Replace later with logged-in user

        return memberRepository.save(member);
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

        updateMemberById.setName(memberRequest.getName());
        updateMemberById.setSurname(memberRequest.getSurname());
        updateMemberById.setGender(Gender.valueOf(memberRequest.getGender().name()));
        updateMemberById.setCellNumber(memberRequest.getCellNumber());
        updateMemberById.setRole(Role.valueOf(memberRequest.getRole().name()));
        updateMemberById.setNation(memberRequest.getNation());
        updateMemberById.setPassword(memberRequest.getPassword());
        updateMemberById.setActive(memberRequest.isActive());

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
    public Members login(String email, String password) {
        Members loggedIn = memberRepository.findByEmailAndPassword(email, password).orElse(null);

        log.info(String.valueOf(loggedIn));

        return loggedIn;
    }


    @Override
    public List<Members> getMentorsByProvinceAndRegion(Role role, String provinceName, String regionName) {
        return List.of();
    }

//    @Override
//    public Members assignMentor(Long memberId, Long mentorId) {
//
//        Optional<Members> memberOpt = memberRepository.findById(memberId);
//        Optional<Members> mentorOpt = memberRepository.findById(mentorId);
//
//        if (memberOpt.isEmpty() || mentorOpt.isEmpty()){
//            throw new IllegalArgumentException("Invalid member or Mentor ID");
//        }
//
//        Members member = memberOpt.get();
//        Members mentor = mentorOpt.get();
//
//        if (mentor.getRole() != Role.MENTOR) {
//            throw new IllegalArgumentException("Only MENTORS can have members under them.");
//        }
//        member.setMentor(mentor);
//
//        return memberRepository.save(member);
//    }

//    @Override
//    public List<Members> getMentorsByProvinceAndRegion(Role role, String provinceName, String regionName) {
//
//
//        return memberRepository.findByRoleAndProvince_NameAndRegion_Name(role, provinceName, regionName);
//    }

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

    @Override
    public List<Members> getMentorsByAddress(long addressId) {
        return memberRepository.findByRoleAndAddressId(Role.MENTOR, addressId);
    }

    @Override
    public List<Members> getAllMembersByAddress(long addressId) {
        return memberRepository.findByRoleAndAddressId(Role.MEMBER, addressId);
    }

//    @Override
//    public List<Members> getAllMembersByAddress(long addressId) {
//        return memberRepository.findByRoleAndAddressId(Role.MEMBER, addressId);
//    }

    @Override
    public List<Members> findByAddressId(Long addressId) {
        return memberRepository.findByAddressId(addressId);
    }


    //        return membersRepository.findAllByAddressId(addressId, pageable);
    @Override
    public Page<Members> getAllMembersByAddress(Long addressId, Pageable pageable) {
        return memberRepository.findAllByAddressId(addressId, pageable);

    }

    @Override
    public Page<Members> getAllSavedMembersByAddress(Long addressId, Pageable pageable) {
        return memberRepository.findAllByAddressId(addressId, pageable);
    }





//    @Override
//    public Page<Members> getAllMembersByAddress(Pageable pageable) {
//        if(pageable == null){
//            pageable = Pageable.unpaged();
//        }
//        return memberRepository.findAll(pageable);
//    }

//    @Override
//    public List<Members> getMentorsByAddress(Long addressId) {
//        return memberRepository.findByRoleAndAddressId(Role.MENTOR, addressId);
//    }

//    public Members moveMember(Long memberId, Nation newNation, Long newProvinceId, Long newRegionId, Long newMentorId) {
//        Members member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new EntityNotFoundException("Member not found with ID: " + memberId));
//
////        Province newProvince = provinceRepository.findById(newProvinceId)
////                .orElseThrow(() -> new EntityNotFoundException("Province not found with ID: " + newProvinceId));
////
////        Region newRegion = regionRepository.findById(newRegionId)
////                .orElseThrow(() -> new EntityNotFoundException("Region not found with ID: " + newRegionId));
//
//        member.setNation(newNation);
////        member.setProvince(newProvince);
////        member.setRegion(newRegion);
//
//        if (newMentorId != null) {
//            Members newMentor = memberRepository.findById(newMentorId)
//                    .orElseThrow(() -> new EntityNotFoundException("New Mentor not found with ID: " + newMentorId));
//
//            // Remove the member from the old mentor if they have one
//            if (member.getMentor() != null) {
//                Members oldMentor = member.getMentor();
//                oldMentor.getMentees().remove(member);
//                memberRepository.save(oldMentor);
//            }
//
//            // ✅ Correctly assign the new mentor
//            member.setMentor(newMentor);
//            newMentor.getMentees().add(member);
//
//            memberRepository.save(newMentor);
//        } else {
//            // If no new mentor is provided, remove the existing mentor
//            if (member.getMentor() != null) {
//                Members oldMentor = member.getMentor();
//                oldMentor.getMentees().remove(member);
//                memberRepository.save(oldMentor);
//            }
//            member.setMentor(null);
//        }
//
//        return memberRepository.save(member);
//    }
}
