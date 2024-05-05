package com.vamsi.krishna.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;


import javax.servlet.Servlet;
import java.io.IOException;
import java.util.Iterator;

@Component(
        service = Servlet.class,
        property = {
                Constants.SERVICE_DESCRIPTION + "=Component Details Servlet",
                ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES + "=/apps/VamsiKrishna/components/componentslist",
                ServletResolverConstants.SLING_SERVLET_METHODS + "=GET"
        }
)
public class FetchComponentsServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        // Set the response type to HTML
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        // Get a ResourceResolver from the request
        ResourceResolver resourceResolver = request.getResourceResolver();

        // Define the path to search for component definitions
        String componentsPath = "/apps/VamsiKrishna/components";
        Resource componentsRoot = resourceResolver.getResource(componentsPath);

        // Start building the HTML response
        StringBuilder htmlResponse = new StringBuilder();
        htmlResponse.append("<html><head><title>Component Details</title></head><body>");
        htmlResponse.append("<h1>Available Components:</h1>");
        htmlResponse.append("<ul>");

        // Iterate through the components and collect relevant details
        if (componentsRoot != null) {
            Iterator<Resource> components = componentsRoot.listChildren();
            while (components.hasNext()) {
                Resource component = components.next();
                String name = component.getName();
                String title = component.getValueMap().get("jcr:title", "Unknown Title");
                String componentGroup = component.getValueMap().get("componentGroup", "Unknown Group");
                String slingResourceType = component.getValueMap().get("sling:resourceType", "N/A");
                String slingResourceSuperType = component.getValueMap().get("sling:resourceSuperType", "N/A");

                htmlResponse.append("<li>");
                htmlResponse.append("<h1>").append(name).append("</h1>");
                htmlResponse.append("<b>jcr:title:</b> ").append(title).append("<br>");
                htmlResponse.append("<b>componentGroup:</b> ").append(componentGroup).append("<br>");
                htmlResponse.append("<b>sling:resourceType:</b> ").append(slingResourceType).append("<br>");
                htmlResponse.append("<b>sling:resourceSuperType:</b> ").append(slingResourceSuperType);
                htmlResponse.append("</li><br>");
            }
        } else {
            htmlResponse.append("<li>No components found in ").append(componentsPath).append("</li>");
        }

        htmlResponse.append("</ul>");
        htmlResponse.append("</body></html>");

        // Write the HTML to the response
        response.getWriter().write(htmlResponse.toString());
    }
}