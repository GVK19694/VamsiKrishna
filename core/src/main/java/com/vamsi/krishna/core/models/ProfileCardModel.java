package com.vamsi.krishna.core.models;

import com.vamsi.krishna.core.services.ProfileCardService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@Model(
        adaptables = Resource.class,
        adapters = ProfileCardModel.class,
        resourceType = "VamsiKrishna/components/profilecard",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class ProfileCardModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileCardModel.class);
    private static final String TAG = ProfileCardModel.class.getName();
    @OSGiService
    ProfileCardService profileCardService;
    private String facebook;
    private String twitter;
    private String linkedin;
    private String instagram;
    private String github;
    public String getFacebook() {
        return facebook;
    }
    public String getTwitter() {
        return twitter;
    }
    public String getLinkedin() {
        return linkedin;
    }
    public String getInstagram() {
        return instagram;
    }
    public String getGithub() {
        return github;
    }
    @PostConstruct
    public void init() {
        LOGGER.info(TAG + "init");
        facebook = profileCardService.getFacebook();
        twitter = profileCardService.getTwitter();
        linkedin = profileCardService.getLinkedin();
        instagram = profileCardService.getInstagram();
        github = profileCardService.getGithub();
    }
}
