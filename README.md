# ebanking-commons

The ebanking-commons library contains central components and utility classes for microservices. The library does not contain any service specific code. 
It helps to create an uniform presence of all interfaces. Components that are automatically initialized by spring boot should be designed in such a way that 
they can be overridden by a micorservice if necessary.

## Relase a new Version
To relase a new library version a tag (https://github.com/abacusresearch/ebanking-commons/tags) has to be created with a new version number. After the tag is created the new version of the lib can be used by the microservice
