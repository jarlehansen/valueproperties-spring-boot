package com.github.jarlehansen.valueproperties;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class PropertiesLocatorController {
    private final Set<ValueProperty> valueProperties;

    public PropertiesLocatorController(Set<ValueProperty> valueProperties) {
        this.valueProperties = valueProperties;
    }

    @RequestMapping(value = "/valueProps", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<ValueProperty> getValueProperties() {
        return valueProperties;
    }

}
