package org.finaltask.networkofgiving.dtos.request;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CharityRequest {
    @NotBlank
    @Size(min = 6, max = 100)
    private String title;

    @NotBlank
    @Size(max = 1000)
    private String description;

    private Double  donationRequired;

    private Double donationCurrent;

    private Integer participantsRequired;

    private Integer participantsCurrent;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDonationRequired() {
        return donationRequired;
    }

    public void setDonationRequired(Double donationRequired) {
        this.donationRequired = donationRequired;
    }

    public Double getDonationCurrent() {
        return donationCurrent;
    }

    public void setDonationCurrent(Double donationCurrent) {
        this.donationCurrent = donationCurrent;
    }

    public Integer getParticipantsRequired() {
        return participantsRequired;
    }

    public void setParticipantsRequired(Integer participantsRequired) {
        this.participantsRequired = participantsRequired;
    }

    public Integer getParticipantsCurrent() {
        return participantsCurrent;
    }

    public void setParticipantsCurrent(Integer participantsCurrent) {
        this.participantsCurrent = participantsCurrent;
    }

}
