package com.lakshaygpt28.bookmyticket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {
    private int status;
    private String error;
    private String message;
}
