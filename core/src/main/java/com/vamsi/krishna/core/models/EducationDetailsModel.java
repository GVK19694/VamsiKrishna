package com.vamsi.krishna.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface EducationDetailsModel {
    @ChildResource
    List<Education> getEducationList();

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    interface Education {

        @ValueMapValue
        String getInstituteName();

        @ValueMapValue
        String getDegree();

        @ValueMapValue
        String getSpecialization();

        @ValueMapValue
        String getStartDate();

        @ValueMapValue
        String getEndDate();

        @ValueMapValue
        String getLocation();

    }
}
