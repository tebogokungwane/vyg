package com.vyg.controller;

import com.vyg.entity.Members;
import com.vyg.enumerator.Nation;
import com.vyg.enumerator.Role;
import com.vyg.model.MemberRequest;
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
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<Members> registerMember(@RequestBody MemberRequest memberRequest) {
        try {
            Members member = memberService.createMember(memberRequest);
            return ResponseEntity.ok(member);
        } catch (Exception e) {
            log.error("Error creating member");
            log.info("Exception "+ e);

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

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


    @GetMapping("/mentors/{province}/{region}")
    public ResponseEntity<List<Members>> getMentorsByProvinceAndRegion(@PathVariable Role role, @PathVariable String provinceName,  @PathVariable String regionName ) {
        return ResponseEntity.ok(memberService.getMentorsByProvinceAndRegion(role, provinceName, regionName));
    }

//    @GetMapping("/members/nation/{nation}")
//    public ResponseEntity<List<Members>> getMembersByNation(@PathVariable Nation nation) {
//        return ResponseEntity.ok(memberService.getMembersByNation(nation));
//    }


//    @GetMapping("/mentor/{mentorId}/mentees")
//    public ResponseEntity<Optional<Members>> getMentorAndMentees(@PathVariable Long mentorId) {
//        return ResponseEntity.ok(memberService.getMentorAndMentees(mentorId));
//    }


}