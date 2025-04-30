package com.core.carryOn.WorkStart.Controller;

import com.core.carryOn.WorkStart.Service.WorkStartService;
import com.core.carryOn.WorkStart.domain.WorkStart;
import com.core.carryOn.member.domain.Member;
import com.core.carryOn.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WorkStartController {
    private final MemberService memberService;
    private final WorkStartService workStartService;

    @GetMapping("/my-worktime")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<WorkStart> getMyWorkTime(){
        Member member = memberService.getMyMemberWithAuthority()
                .orElseThrow(() -> new NoSuchElementException("해당 멤버가 없습니다."));

        WorkStart workStart = workStartService.findByMemberId(member.getId())
                .orElseThrow(() -> new NoSuchElementException("출근시간 정보가 없습니다."));

        return ResponseEntity.ok(workStart);
    }

    @PostMapping("/setting-worktime")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<WorkStart> updateWorkTime(@RequestBody WorkStart workStart){
        Member member = memberService.getMyMemberWithAuthority()
                .orElseThrow(() -> new NoSuchElementException("해당 멤버가 없습니다."));

        Optional<WorkStart> byMemberId = workStartService.findByMemberId(member.getId());

        if(byMemberId.isPresent()){
            WorkStart existedWorktime = byMemberId.get();
            existedWorktime.setStartTime(workStart.getStartTime());
            workStartService.save(existedWorktime);
            return ResponseEntity.ok(existedWorktime);
        }else{
            WorkStart newWorkStart = new WorkStart();
            newWorkStart.setStartTime(workStart.getStartTime());
            newWorkStart.setMemberId(member.getId());
            workStartService.save(newWorkStart);
            return ResponseEntity.ok(newWorkStart);
        }


    }
}
