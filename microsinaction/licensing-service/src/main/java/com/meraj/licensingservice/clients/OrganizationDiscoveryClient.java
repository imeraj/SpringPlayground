package com.meraj.licensingservice.clients;

import com.meraj.licensingservice.models.Organization;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class OrganizationDiscoveryClient {

    @Autowired
    private DiscoveryClient discoveryClient;

    public Organization getOrganization(String organizationId) {
        RestTemplate restTemplate = new RestTemplate();
        List<ServiceInstance> instances = discoveryClient.getInstances("organizationservice");

        if (instances.size()==0)
            return null;
        String serviceUri = String.format("%s/v1/organizations/%s",instances.get(0).getUri().toString(), organizationId);

        ResponseEntity<Organization> restExchange
                = restTemplate.exchange(serviceUri, HttpMethod.GET,null, Organization.class, organizationId);

        System.out.println("BODY: " + restExchange.getBody());

        return restExchange.getBody();
    }
}
