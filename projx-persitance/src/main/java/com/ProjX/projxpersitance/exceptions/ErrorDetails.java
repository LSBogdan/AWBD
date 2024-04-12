package com.ProjX.projxpersitance.exceptions;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDetails {
    private ZonedDateTime errorDate;
    private String detail;
    private String errorKey;
}
