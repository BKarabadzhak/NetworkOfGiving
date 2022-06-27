package org.finaltask.networkofgiving.models;

import javax.persistence.Embeddable;
import java.io.Serializable;
//https://stackoverflow.com/questions/23837561/jpa-2-0-many-to-many-with-extra-column
//https://stackoverflow.com/questions/61610701/jpa-2-0-many-to-many-with-extra-column-update-collection

@Embeddable
public class CustomerCharityDonatesId implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long customerId;
    private Long charityId;
}
