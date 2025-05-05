package com.vyg.controller;

import com.vyg.entity.Members;
import com.vyg.enumerator.Role;
import com.vyg.dto.MemberRequest;
import com.vyg.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<Members> registerMember(@RequestBody MemberRequest memberRequest) {
        try {
            log.info("Request body :" + memberRequest);
            Members member = memberService.createMember(memberRequest);
            return ResponseEntity.ok(member);
        } catch (Exception e) {
            log.error("Error creating member");
            log.info("Exception "+ e);

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @PostMapping("/login")
    public ResponseEntity<Members> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        Members member = memberService.login(email, password);
        if (member != null) {
            return ResponseEntity.ok(member);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }



    @PostMapping("/addMentee")
    public ResponseEntity<Members> addMentee(@RequestBody MemberRequest request) {
        return ResponseEntity.ok(memberService.createMentee(request));
    }


    @GetMapping("/members")
    public ResponseEntity<Page<Members>> getAllMembers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "9000") int size){
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(memberService.getAllMembers(pageable));
    }

    @DeleteMapping("deleteMember/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteMember(@PathVariable long id){
        boolean deleteMember = memberService.deleteMember(id);
        Map<String, Boolean> response = new HashMap<>();
        if(deleteMember){
            response.put("DELETED", true);
            return ResponseEntity.ok(response);
        }else{
            response.put("DELETED", false);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/updateMember/{id}")
    public ResponseEntity<MemberRequest> updateMember(@PathVariable long id, @RequestBody MemberRequest memberRequest){
        memberService.updateMember(id,memberRequest);
        return ResponseEntity.ok(memberRequest);
    }

    @GetMapping("/{mentorId}/mentees")
    public ResponseEntity<List<Members>> getMembersUnderMentor(@PathVariable Long mentorId) {
        return ResponseEntity.ok(memberService.getMembersUnderMentor(mentorId));
    }

    @GetMapping("/mentors")
    public ResponseEntity<List<Members>> getAllMentors() {
        return ResponseEntity.ok(memberService.getAllMentors());
    }





    @GetMapping("/mentor/address/{addressId}")
    public ResponseEntity<List<Members>> getMentorsByAddress(@PathVariable Long addressId){
        List<Members> membersList = memberService.getMentorsByAddress(addressId);
        return ResponseEntity.ok(membersList);
    }


    @GetMapping("/all-members/address/{addressId}")
    public ResponseEntity<List<Members>> getAllMembersByAddress(@PathVariable Long addressId){
        List<Members> membersList = memberService.getAllMembersByAddress(addressId);
        return ResponseEntity.ok(membersList);
    }


    @GetMapping("/all-saved-members/address/{addressId}")
    public ResponseEntity<Page<Members>> getAllSavedMembers(
            @PathVariable Long addressId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9000") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(memberService.getAllSavedMembersByAddress(addressId, pageable));
    }





//    @GetMapping("/all-members/address/{addressId}")
//    public ResponseEntity<List<Members>> getAllMembers(@PathVariable Long addressId) {
//        List<Members> mentors = memberService.getAllMembersByAddress()
//        return ResponseEntity.ok(mentors);
//    }

//
//    @GetMapping("/all-members/address/{addressId}")
//    public ResponseEntity<Page<Members>> getAllMembersByAddress(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "9000") int size){
//        Pageable pageable = PageRequest.of(page,size);
//        return ResponseEntity.ok(memberService.getAllMembersByAddress(pageable));
//    }

//    @GetMapping("/address/{addressId}")
//    public ResponseEntity<List<Members>> getMembersByAddress(@PathVariable Long addressId){
//        List<Members> members = memberService.findByAddressId(addressId);
//        return ResponseEntity.ok(members);
//    }

//    @GetMapping("/members/nation/{nation}")
//    public ResponseEntity<List<Members>> getMembersByNation(@PathVariable Nation nation) {
//        return ResponseEntity.ok(memberService.getMembersByNation(nation));
//    }


//    @GetMapping("/mentor/{mentorId}/mentees")
//    public ResponseEntity<Optional<Members>> getMentorAndMentees(@PathVariable Long mentorId) {
//        return ResponseEntity.ok(memberService.getMentorAndMentees(mentorId));
//    }


}