package com.vamsi.krishna.core.models;

import java.util.List;
import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
@Model(
        adaptables = {Resource.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public interface WorkExperienceListModel {
    @Inject
    List<CompanyDetails> getCompanyDetails();
    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    interface CompanyDetails {
        @Inject
        String getCompanyName();
        @Inject
        String getDesignation();
        @Inject
        String getStartDate();
        @Inject
        String getEndDate();
        @Inject
        List<Description> getDescription();
    }
    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    interface Description {
        @Inject
        String getDetails();
    }
}
