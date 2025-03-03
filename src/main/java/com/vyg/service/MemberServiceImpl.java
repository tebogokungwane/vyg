package com.vyg.service;

import com.vyg.entity.Branch;
import com.vyg.entity.Province;
import com.vyg.entity.Region;
import com.vyg.enumerator.Gender;
import com.vyg.entity.Members;
import com.vyg.enumerator.Role;
import com.vyg.model.MemberRequest;
import com.vyg.repository.BranchRepository;
import com.vyg.repository.MemberRepository;
import com.vyg.repository.ProvinceRepository;
import com.vyg.repository.RegionRepository;
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
    private final ProvinceRepository provinceRepository;
    @Autowired
    private final RegionRepository regionRepository;
    @Autowired
    private BranchRepository branchRepository;


    public MemberServiceImpl(MemberRepository memberRepository, ProvinceRepository provinceRepository, RegionRepository regionRepository) {
        this.memberRepository = memberRepository;
        this.provinceRepository = provinceRepository;
        this.regionRepository = regionRepository;
    }

    public Members createMember(MemberRequest memberRequest) throws Exception {
        Members member = new Members();

        member.setName(memberRequest.getName());
        member.setSurname(memberRequest.getSurname());
        member.setGender(memberRequest.getGender());
        member.setCellNumber(memberRequest.getCellNumber());

        // ✅ Fetch and assign Province
        Province province = provinceRepository.findById(memberRequest.getProvinceId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Province ID"));
        member.setProvince(province);

        // ✅ Fetch and assign Region
        Region region = regionRepository.findById(memberRequest.getRegionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Region ID"));
        member.setRegion(region);

        // ✅ Fetch and assign Branch
        Branch branch = branchRepository.findById(memberRequest.getBranchId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Branch ID"));
        member.setBranch(branch);

        member.setRole(memberRequest.getRole());
        member.setNation(memberRequest.getNation());
        member.setPassword(memberRequest.getPassword());
        member.setActive(memberRequest.isActive());

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
    public Members assignMentor(Long memberId, Long mentorId) {

        Optional<Members> memberOpt = memberRepository.findById(memberId);
        Optional<Members> mentorOpt = memberRepository.findById(mentorId);

        if (memberOpt.isEmpty() || mentorOpt.isEmpty()){
            throw new IllegalArgumentException("Invalid member or Mentor ID");
        }

        Members member = memberOpt.get();
        Members mentor = mentorOpt.get();

        if (mentor.getRole() != Role.MENTOR) {
            throw new IllegalArgumentException("Only MENTORS can have members under them.");
        }
        member.setMentor(mentor);

        return memberRepository.save(member);
    }

    @Override
    public List<Members> getMentorsByProvinceAndRegion(Role role, String provinceName, String regionName) {


        return memberRepository.findByRoleAndProvince_NameAndRegion_Name(role, provinceName, regionName);
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
                .province(mentor.getProvince())  // ✅ Inherit province from mentor
                .region(mentor.getRegion())      // ✅ Inherit region from mentor
                .branch(mentor.getBranch())      // ✅ Inherit branch from mentor
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


//    @Override
//    public Optional<Members> getMentorAndMentees(Long mentorId) {
//        return memberRepository.findAllMenteesByMentor(mentorId);
//    }
}
