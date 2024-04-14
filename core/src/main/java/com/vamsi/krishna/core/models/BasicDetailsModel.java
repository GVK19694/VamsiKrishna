package com.vamsi.krishna.core.models;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.api.resource.Resource;

@Model(adaptables = Resource.class, defaultInjectionStrategy= DefaultInjectionStrategy.OPTIONAL,
    resourceType = {BasicDetailsModel.RESOURCE_TYPE}
)
public class BasicDetailsModel {
    protected static final String RESOURCE_TYPE = "VamsiKrishna/components/basicdetails";
    @ValueMapValue
    String name;
    @ValueMapValue
    String designation;
    @ValueMapValue
    String mobile;
    @ValueMapValue
    String email;
    @ValueMapValue
    String city;

    public String getName() {
        return name;
    }
    public String getDesignation() {
        return designation;
    }
    public String getMobile() {
        return mobile;
    }
    public String getEmail() {
        return email;
    }
    public String getCity() {
        return city;
    }
}
