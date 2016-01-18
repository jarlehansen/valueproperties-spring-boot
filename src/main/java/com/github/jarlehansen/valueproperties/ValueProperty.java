package com.github.jarlehansen.valueproperties;

public class ValueProperty {
    private String key;
    private String defaultValue;

    public ValueProperty(String annotationValue) {
        populateProperties(annotationValue);
    }

    public void populateProperties(String annotationValue) {
        annotationValue = annotationValue.replace("${", "");
        annotationValue = annotationValue.replace("}", "");
        if (annotationValue.contains(":")) {
            int index = annotationValue.indexOf(":");
            key = annotationValue.substring(index);
            defaultValue = annotationValue.substring(index + 1, annotationValue.length());
        } else {
            key = annotationValue;
        }
    }

    public String getKey() {
        return key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
