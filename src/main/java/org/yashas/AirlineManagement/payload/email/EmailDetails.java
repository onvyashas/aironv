package org.yashas.AirlineManagement.payload.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDetails {   
    private String recipient;
    private String messageBody;
    private String subject;
}