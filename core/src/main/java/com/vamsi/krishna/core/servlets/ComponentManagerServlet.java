package com.vamsi.krishna.core.servlets;

import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.ServletResolverConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Component;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;

import org.apache.sling.api.resource.ModifiableValueMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.Gson;

@Component(service = Servlet.class,
        property = {
                ServletResolverConstants.SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET + "," + HttpConstants.METHOD_POST + "," + HttpConstants.METHOD_PUT + "," + HttpConstants.METHOD_DELETE,
                ServletResolverConstants.SLING_SERVLET_PATHS + "=" + "/bin/VamsiKrishna/componentManager"
        }
)
public class ComponentManagerServlet extends SlingAllMethodsServlet {

    private Gson gson = new Gson();

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String componentName = request.getParameter("name");
        response.setContentType("application/json");
        ResourceResolver resolver = request.getResourceResolver();
        Resource resource = resolver.getResource("/apps/VamsiKrishna/components" + (componentName != null ? "/" + componentName : ""));

        JsonObject jsonResponse = new JsonObject();
        JsonArray components = new JsonArray();

        if (resource != null) {
            if (componentName != null) {
                // Fetch single component
                jsonResponse.addProperty("path", resource.getPath());
                jsonResponse.add("properties", gson.toJsonTree(resource.getValueMap()));
            } else {
                // Fetch all components
                for (Resource component : resource.getChildren()) {
                    JsonObject compJson = new JsonObject();
                    compJson.addProperty("name", component.getName());
                    compJson.addProperty("path", component.getPath());
                    compJson.add("properties", gson.toJsonTree(component.getValueMap()));
                    components.add(compJson);
                }
                jsonResponse.add("components", components);
            }
        } else {
            jsonResponse.addProperty("message", "Component not found.");
        }
        response.getWriter().write(gson.toJson(jsonResponse));
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String componentName = request.getParameter("name");
        JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);
        response.setContentType("application/json");
        ResourceResolver resolver = request.getResourceResolver();

        try {
            Resource resource = resolver.create(resolver.getResource("/apps/VamsiKrishna/components"), componentName, gson.fromJson(jsonObject, Map.class));
            resolver.commit();
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("message", "Created component at: " + resource.getPath());
            response.getWriter().write(gson.toJson(jsonResponse));
        } catch (Exception e) {
            response.getWriter().write("Error creating component: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        String componentName = request.getParameter("name");
        JsonObject jsonObject = gson.fromJson(request.getReader(), JsonObject.class);
        response.setContentType("application/json");
        ResourceResolver resolver = request.getResourceResolver();

        try {
            Resource resource = resolver.getResource("/apps/VamsiKrishna/components/" + componentName);
            if (resource != null) {
                ModifiableValueMap valueMap = resource.adaptTo(ModifiableValueMap.class);
                jsonObject.entrySet().forEach(entry -> valueMap.put(entry.getKey(), gson.fromJson(entry.getValue(), Object.class)));
                resolver.commit();
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("message", "Updated component at: " + resource.getPath());
                response.getWriter().write(gson.toJson(jsonResponse));
            } else {
                response.getWriter().write("Component not found for updating.");
            }
        } catch (Exception e) {
            response.getWriter().write("Error updating component: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        String componentName = request.getParameter("name");
        response.setContentType("application/json");
        ResourceResolver resolver = request.getResourceResolver();

        try {
            Resource resource = resolver.getResource("/apps/VamsiKrishna/components/" + componentName);
            if (resource != null) {
                resolver.delete(resource);
                resolver.commit();
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("message", "Deleted component.");
                response.getWriter().write(gson.toJson(jsonResponse));
            } else {
                response.getWriter().write("Component not found for deletion.");
            }
        } catch (Exception e) {
            response.getWriter().write("Error deleting component: " + e.getMessage());
        }
    }
}