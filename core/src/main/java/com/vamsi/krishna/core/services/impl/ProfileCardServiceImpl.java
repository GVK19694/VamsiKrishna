package com.vamsi.krishna.core.services.impl;

import com.vamsi.krishna.core.services.ProfileCardService;
import com.vamsi.krishna.core.configurations.ProfileCardConfig;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
        service = ProfileCardService.class,
        immediate = true,
        property = {
                Constants.SERVICE_ID + "=VamsiKrishna ProfileCard Service",
                Constants.SERVICE_DESCRIPTION + "=This service reads values from ProfileCard Configuration"
        }
)
@Designate(
        ocd = ProfileCardConfig.class
)
public class ProfileCardServiceImpl implements ProfileCardService {
    private static final Logger log = LoggerFactory.getLogger(ProfileCardServiceImpl.class);

    private ProfileCardConfig configuration;
    @Activate
    protected void activate(ProfileCardConfig config) { this.configuration = config; }
    @Override
    public String getTwitter() {
        return configuration.twitter();
    }
    @Override
    public String getLinkedin() {
        return configuration.linkedin();
    }
    @Override
    public String getFacebook() {
        return configuration.facebook();
    }
    @Override
    public String getInstagram() {
        return configuration.instagram();
    }
    @Override
    public String getGithub() {
        return configuration.github();
    }
}
