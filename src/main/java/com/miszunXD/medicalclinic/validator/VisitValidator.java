package com.miszunXD.medicalclinic.validator;

import com.miszunXD.medicalclinic.exception.InvalidDateException;
import com.miszunXD.medicalclinic.exception.VisitAlreadyBookedException;
import com.miszunXD.medicalclinic.model.Visit;

import java.time.LocalDateTime;

public final class VisitValidator {

    private VisitValidator() {
    }

    public static void validateVisitTime(LocalDateTime startDate, LocalDateTime endDate) {
        validateEndIsAfterStart(startDate, endDate);
        validateVisitStartTimeIsNotInThePast(startDate);
        validateTimeFullQuarter(startDate);
        validateTimeFullQuarter(endDate);

    }

    private static void validateEndIsAfterStart(LocalDateTime startDate, LocalDateTime endDate) {
        if (!endDate.isAfter(startDate)) {
            throw new InvalidDateException("Visit end date: " + endDate + " has to be after start date " + startDate + " !");
        }
    }

    public static void validateVisitStartTimeIsNotInThePast(LocalDateTime startDate) {
        if (startDate.isBefore(LocalDateTime.now())) {
            throw new InvalidDateException("Visit cannot be booked in the past: " + startDate + " !");
        }
    }

    private static void validateTimeFullQuarter(LocalDateTime dateTime) {
        if (dateTime.getMinute() % 15 != 0 || dateTime.getSecond() != 0 || dateTime.getNano() != 0) {
            throw new InvalidDateException("Visit must be booked to full quarter of an hour: " + dateTime + " !");
        }
    }

    public static void validateVisitIsNotAlreadyBooked(Visit visit) {
        if (visit.isTaken()) {
            throw new VisitAlreadyBookedException("Visit with given id: " + visit.getId() + " is already booked!");
        }
    }
}
