package org.finaltask.networkofgiving.dtos.request;

import org.springframework.web.multipart.MultipartFile;

public class CharityToAdd {

    private String title;

    private String description;

    private Double donationRequired;

    private Double donationCurrent;

    private Integer participantsRequired;

    private Integer participantsCurrent;

    public CharityToAdd(String title, String description, Double donationRequired,
                        Double donationCurrent, Integer participantsRequired, Integer participantsCurrent) {
        this.title = title;
        this.description = description;
        this.donationRequired = donationRequired;
        this.donationCurrent = donationCurrent;
        this.participantsRequired = participantsRequired;
        this.participantsCurrent = participantsCurrent;
    }

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
