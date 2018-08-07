package com.meraj.licensingservice.repositories;

import com.meraj.licensingservice.models.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends CrudRepository<License, String> {
    public List<License> findByOrganizationId(String OgranizationId);
    public License findByOrganizationIdAndLicenseId(String organizationId, String licesnseId);
}
