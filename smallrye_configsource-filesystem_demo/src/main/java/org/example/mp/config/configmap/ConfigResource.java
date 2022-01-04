package org.example.mp.config.configmap;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * See below spec for instructions on how to use MicroProfile Config 2.0
 * https://download.eclipse.org/microprofile/microprofile-config-2.0/microprofile-config-spec-2.0.html
 */
@ApplicationScoped
@Path("/configs")
public class ConfigResource {

    @Inject
    Config config;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ConfigValue> getAll() {
        List<ConfigValue> configs = new ArrayList<>();
        for (String key : config.getPropertyNames()) {
            ConfigValue val = config.getConfigValue(key);
            // Dump only FileSystemConfigSource[dir=/dir], not SysPropConfigSource and EnvConfigSource
            if (val.getSourceName().contains("FileSystemConfigSource")) {
                configs.add(val);
            }
        }
        return configs;
    }

    @GET
    @Path("/{key}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getProperty(@PathParam("key") String key) {
        return config.getOptionalValue(key, String.class).orElseThrow(() -> new NotFoundException());
    }
}
