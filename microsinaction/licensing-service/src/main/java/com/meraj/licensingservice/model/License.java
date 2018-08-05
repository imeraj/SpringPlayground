package com.meraj.licensingservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class License {
    private String id;
    private String productName;
    private String licenseType;
    private String organizationId;
}
