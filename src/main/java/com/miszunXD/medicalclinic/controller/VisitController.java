package com.miszunXD.medicalclinic.controller;

import com.miszunXD.medicalclinic.dto.CreateVisitCommand;
import com.miszunXD.medicalclinic.dto.VisitDto;
import com.miszunXD.medicalclinic.model.Visit;
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

    @GetMapping("/{patientEmail}")
    public List<VisitDto> getPatientVisits(@PathVariable String patientEmail) {
        log.info("REQUEST GET /visits/{}", patientEmail);
        List<VisitDto> response = visitService.getPatientVisits(patientEmail);
        log.info("RESPONSE GET /visits/{} | 200 | count={}", patientEmail, response.size());
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

    @PostMapping("/{visitId}/patients/{patientEmail}")
    public VisitDto registerPatient(@PathVariable Long visitId, @PathVariable String patientEmail) {
        log.info("REQUEST POST /visits/{}/patients/{}", visitId, patientEmail);
        VisitDto response = visitService.registerPatient(visitId, patientEmail);
        log.info("RESPONSE POST /visits/{}/patients/{} | 200", visitId, patientEmail);
        return response;
    }
}
