package com.vamsi.krishna.core.configurations;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(
        name = "VamsiKrishna Profile Card Social Media Configurations",
        description = "This configuration captures the social media links"
)
public @interface ProfileCardConfig {
    @AttributeDefinition(
            name = "Twitter",
            description = "Twitter profile link"
    )
    String twitter();

    @AttributeDefinition(
            name = "LinkedIn",
            description = "LinkedIn profile link"
    )
    String linkedin();

    @AttributeDefinition(
            name = "Facebook",
            description = "Facebook profile link"
    )
    String facebook();

    @AttributeDefinition(
            name = "Instagram",
            description = "Instagram profile link"
    )
    String instagram();

    @AttributeDefinition(
            name = "Github",
            description = "Github profile link"
    )
    String github();
}
