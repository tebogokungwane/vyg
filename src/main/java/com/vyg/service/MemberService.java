package com.vyg.service;

import com.vyg.entity.Members;
import com.vyg.enumerator.Nation;
import com.vyg.enumerator.Role;
import com.vyg.model.MemberRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface MemberService {

   Members createMember(MemberRequest memberRequest) throws Exception;

   Page<Members> getAllMembers(Pageable pageable);

   boolean deleteMember(long id);

   Members updateMember(long id, MemberRequest memberRequest);

   List<Members> getMembersUnderMentor(Long mentorId);

   List<Members> getAllMentors();


   Members login(String email, String password);

   List<Members> getMentorsByProvinceAndRegion(Role role, String provinceName, String regionName);

   Members createMentee(MemberRequest request);

   List<Members>  getMentorsByAddress(long addressId);

   List<Members> getAllMembersByAddress(long addressId);



   List<Members> findByAddressId(Long addressId);

   Page<Members> getAllMembersByAddress(Long addressId, Pageable pageable);

   Page<Members> getAllSavedMembersByAddress(Long addressId,  Pageable pageable);


}