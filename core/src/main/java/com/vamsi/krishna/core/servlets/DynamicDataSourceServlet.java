package com.vamsi.krishna.core.servlets;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.cq.dam.commons.util.DamUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.iterators.TransformIterator;
import org.apache.jackrabbit.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_RESOURCE_TYPES;

@Component(
        service = Servlet.class,
        property = {
                Constants.SERVICE_ID + "=" + "Dynamic DataSource Servlet",
                SLING_SERVLET_RESOURCE_TYPES + "=" + "/bin/dropdownValues"
        }
)
public class DynamicDataSourceServlet extends SlingSafeMethodsServlet {

    private static final long serialVersionUID = 4235730140092283425L;
    private static final String TAG = DynamicDataSourceServlet.class.getSimpleName();
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicDataSourceServlet.class);

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        try {
            ResourceResolver resourceResolver = request.getResourceResolver();
            Resource currentResource = request.getResource();
            String dropdownSelector = Objects.requireNonNull(currentResource.getChild("datasource"))
                    .getValueMap()
                    .get("dropdownSelector", String.class);
            Resource jsonResource = getJsonResource(resourceResolver, Objects.requireNonNull(dropdownSelector));
            Asset asset = DamUtil.resolveToAsset(jsonResource);
            Rendition originalAsset = Objects.requireNonNull(asset).getOriginal();
            InputStream content = Objects.requireNonNull(originalAsset).adaptTo(InputStream.class);
            StringBuilder jsonContent = new StringBuilder();
            BufferedReader jsonReader = new BufferedReader(
                    new InputStreamReader(Objects.requireNonNull(content), StandardCharsets.UTF_8));
            String line;
            while ((line = jsonReader.readLine()) != null) {
                jsonContent.append(line);
            }
            JsonArray jsonArray = JsonParser.parseString(jsonContent.toString()).getAsJsonArray();
            Map<String, String> data = new TreeMap<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                data.put(jsonObject.get("text").getAsString(),
                        jsonObject.get("value").getAsString());
            }
            DataSource ds = new SimpleDataSource(new TransformIterator<>(data.keySet().iterator(), (Transformer) o -> {
                String dropValue = (String) o;
                ValueMap vm = new ValueMapDecorator(new HashMap<>());
                vm.put("text", dropValue);
                vm.put("value", data.get(dropValue));
                return new ValueMapResource(resourceResolver, new ResourceMetadata(), JcrConstants.NT_UNSTRUCTURED, vm);
            }));
            request.setAttribute(DataSource.class.getName(), ds);
        } catch (IOException e) {
            LOGGER.error("{}: exception occurred: {}", TAG, e.getMessage());
        }
    }

    private Resource getJsonResource(ResourceResolver resourceResolver, String dropdownSelector) {
        Resource jsonResource;
        switch (dropdownSelector) {
            case "countryList":
                jsonResource = resourceResolver.getResource("/content/dam/VamsiKrishna/country.json");
                break;
            case "colorList":
                jsonResource = resourceResolver.getResource("/content/dam/VamsiKrishna/color.json");
                break;
            case "fontList":
                jsonResource = resourceResolver.getResource("/content/dam/VamsiKrishna/font.json");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dropdownSelector);
        }
        return jsonResource;
    }
}
