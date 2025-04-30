package com.core.carryOn.WorkStart.Service;

import com.core.carryOn.WorkStart.Repository.WorkstartRepository;
import com.core.carryOn.WorkStart.domain.WorkStart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkStartService {
    private final WorkstartRepository workstartRepository;

    public Optional<WorkStart> findByMemberId(String memberId) {
        return workstartRepository.findByMemberId(memberId);
    }

    public WorkStart save(WorkStart workstart) {
        return workstartRepository.save(workstart);
    }
}
