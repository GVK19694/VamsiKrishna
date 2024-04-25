package com.vamsi.krishna.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.ServletResolverConstants;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.osgi.service.component.annotations.Component;
import org.osgi.framework.Constants;

import java.io.IOException;
import java.util.Iterator;
import com.google.gson.JsonObject;

@Component(service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=Component Properties Servlet",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
                ServletResolverConstants.SLING_SERVLET_PATHS + "=/bin/componentsList"
        })
public class ComponentPropertiesServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws ServletException, IOException {
        ResourceResolver resolver = req.getResourceResolver();
        Resource resource = resolver.getResource("/apps/VamsiKrishna/components");
        JsonObject jsonObject = new JsonObject();

        if (resource != null) {
            iterateResources(resource, jsonObject);
        }

        resp.setContentType("application/json");
        resp.getWriter().write(jsonObject.toString());
    }
    private void iterateResources(Resource resource, JsonObject jsonResponse) {
        Iterator<Resource> children = resource.listChildren();
        while (children.hasNext()) {
            Resource child = children.next();
            ValueMap properties = child.getValueMap();
            JsonObject propertiesJson = new JsonObject();
            //Skip the unwanted jcr properties
            properties.forEach((key, value) -> {
                if (!key.equals("jcr:created") &&
                        !key.equals("jcr:lastModified") &&
                        !key.equals("jcr:createdBy") &&
                        !key.equals("jcr:lastModifiedBy") &&
                        !key.equals("jcr:uuid") &&
                        !key.equals("jcr:mimeType") &&
                        !key.equals("jcr:data")) {
                    propertiesJson.addProperty(key, String.valueOf(value));
                }
            });

            jsonResponse.add(child.getName(), propertiesJson);

            // Recursive call to include properties of nested nodes
            if (child.hasChildren()) {
                iterateResources(child, propertiesJson);
            }
        }
    }
}