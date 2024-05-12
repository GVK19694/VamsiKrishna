package com.vamsi.krishna.core.services.impl;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.vamsi.krishna.core.services.ResourceResolverService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
@Component(
        service = ResourceResolverService.class,
        property = {
                Constants.SERVICE_ID + "= VamsiKrishna Resource Resolver Service",
                Constants.SERVICE_DESCRIPTION + "= This service is responsible for returning an instance of ResourceResolver"
        })
public class ResourceResolverServiceImpl implements ResourceResolverService {
    //Resource Resolver Factory sub-service
    public static final String SUB_SERVICE = "VamsiKrishnaSubService";
    private static final String TAG = ResourceResolverServiceImpl.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceResolverServiceImpl.class);
    /*Factory class which gives instance of ResourceResolver*/
    @Reference
    ResourceResolverFactory resourceResolverFactory;
    /*
        This defines the API which may be used to resolve Resource objects and
        work with such resources like creating, editing or updating them.
        The resource resolver is available to the request processing servlet
        through the SlingHttpServletRequest.getResourceResolver() method.
        A resource resolver can also be created through the ResourceResolverFactory service.
        The ResourceResolver is also an Adaptable to get adapters of other types.
        A JCR based resource resolver might support adapting to the JCR Session used by
        the resolver to access the JCR Repository.
    */
    private ResourceResolver resourceResolver;
    @Activate
    protected void activate() {
        try {
            /*
                A service user is a JCR user with no password set and a minimal set of privileges
                that are necessary to perform a specific task.
                Having no password set means that it will not be possible to log in with a service user.
            */
            Map<String, Object> serviceUserMap = new HashMap<>();
            // Putting sub-service name in the map
            serviceUserMap.put(ResourceResolverFactory.SUBSERVICE, SUB_SERVICE);
            // Get the instance of Service Resource Resolver
            resourceResolver = resourceResolverFactory.getServiceResourceResolver(serviceUserMap);
        } catch (LoginException e) {
            LOGGER.error("{}: Exception occurred while getting resource resolver: {}", TAG, e.getMessage());
        }
    }

    @Override
    public ResourceResolver getResourceResolver() {
        return resourceResolver;
    }
}