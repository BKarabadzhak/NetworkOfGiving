package org.finaltask.networkofgiving.dtos.request;

import javax.validation.constraints.NotBlank;

public class DonateRequest {
    @NotBlank
    private Long id;

    @NotBlank
    private Double amount;

    public DonateRequest(@NotBlank Long id, @NotBlank Double amount) {
        this.id = id;
        this.amount = amount;
    }

    public DonateRequest() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
