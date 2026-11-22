package com.miszunXD.medicalclinic.controller;

import com.miszunXD.medicalclinic.dto.CreateVisitCommand;
import com.miszunXD.medicalclinic.dto.VisitDto;
import com.miszunXD.medicalclinic.service.VisitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/visits")
public class VisitController {

    private final VisitService visitService;

    @GetMapping("/patients/{patientId}")
    public List<VisitDto> getPatientVisits(@PathVariable Long patientId) {
        log.info("REQUEST GET /visits/patients/{}", patientId);
        List<VisitDto> response = visitService.getPatientVisits(patientId);
        log.info("RESPONSE GET /visits/patients/{} | 200 | count={}", patientId, response.size());
        return response;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VisitDto createVisit(@RequestBody CreateVisitCommand command) {
        log.info("REQUEST POST /visits");
        VisitDto response = visitService.createVisit(command);
        log.info("RESPONSE POST /visits | 201");
        return response;
    }

    @PostMapping("/{visitId}/patients/{patientId}")
    public VisitDto registerPatient(@PathVariable Long visitId, @PathVariable Long patientId) {
        log.info("REQUEST POST /visits/{}/patients/{}", visitId, patientId);
        VisitDto response = visitService.registerPatient(visitId, patientId);
        log.info("RESPONSE POST /visits/{}/patients/{} | 200", visitId, patientId);
        return response;
    }
}
