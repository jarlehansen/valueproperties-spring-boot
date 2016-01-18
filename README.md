Get an overview of all the @Value annotation in the code.
It will display both the key and defaultValue.

Enable the feature by adding @EnableValueProperties to the same class that contains @SpringBootApplication.
It is also possible to add a list of profiles where this feature should be enabled - @EnableValueProperties(profiles = "test").

The list is available at: /valueProps