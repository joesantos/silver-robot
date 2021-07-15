package com.domoticswot.util;

public class SparqlQueryString {
    public static String getPrefixes() {
        return "PREFIX : <http://www.semanticweb.org/joeds/ontologies/2020/3/swotDomoticProject#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX xml: <http://www.w3.org/XML/1998/namespace>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX ssn: <http://www.w3.org/ns/ssn/>\n" +
                "PREFIX sosa: <http://www.w3.org/ns/sosa/>\n";
    }

    public static String getPreAndPostConditionService(String servicePath) {
        return getPrefixes() + "\n SELECT ?hasPreCondition ?hasPostCondition ?affects WHERE{\n" +
                "?s rdf:type :Servico;\n" +
                ":hasPath '" + servicePath + "';\n" +
                ":hasPreCondition ?hasPreCondition;\n" +
                ":hasPostCondition ?hasPostCondition;\n" +
                ":affects ?affects .\n" +
                "}\n";
    }

    public static String getPreAndPostConditionServiceBySensor(String sensorUri) {
        return getPrefixes() + "\n SELECT ?hasPreCondition ?hasPostCondition ?affects WHERE{\n" +
                "?s rdf:type :Servico;\n" +
                ":reactsTo <" + sensorUri + ">;\n" +
                ":hasPreCondition ?hasPreCondition;\n" +
                ":hasPostCondition ?hasPostCondition;\n" +
                ":affects ?affects .\n" +
                "}\n";
    }

    public static String getActuatorByUri(String actuatorUri) {
        return getPrefixes() + "\n SELECT ?isHostedBy ?madeActuation ?forProperty WHERE{\n" +
                "?s rdf:type :LightSwitch;\n" +
                "sosa:isHostedBy ?isHostedBy;\n" +
                "sosa:madeActuation ?madeActuation;\n" +
                "ssn:forProperty ?forProperty .\n" +
                "FILTER (?s = "+actuatorUri+" )  \n" +
                "}\n";
    }

    public static String getActuationByUri(String actuationUri) {
        return getPrefixes() + "\n SELECT ?hasSimpleResult ?actsOnProperty WHERE{\n" +
                "?s rdf:type sosa:Actuation;\n" +
                "sosa:hasSimpleResult ?hasSimpleResult;\n" +
                "sosa:actsOnProperty ?actsOnProperty;\n" +
                " FILTER (?s = <"+actuationUri+"> )  \n" +
                "}\n";
    }

    public static String getFeaturesOfInterest() {
        return getPrefixes() + "SELECT ?uri ?hasProperty ?type WHERE{\n" +
                "  ?uri a ?subClass;\n" +
                "  ssn:hasProperty ?hasProperty ;\n" +
                "  rdf:type ?type .\n" +
                "  ?subClass rdfs:subClassOf sosa:FeatureOfInterest\n" +
                "  FILTER(?type != owl:NamedIndividual)\n" +
                "}";
    }

    public static String getActuationQuery() {
        return getPrefixes() + "SELECT ?s ?p ?o ?resultTime ?hasSimpleResult ?affectedProperty ?foi WHERE {\n" +
                "    ?s a sosa:Actuation ;\n" +
                "    sosa:resultTime ?resultTime ;\n" +
                "    sosa:hasSimpleResult ?hasSimpleResult ;\n" +
                "    sosa:actsOnProperty ?affectedProperty ;\n" +
                "    sosa:hasFeatureOfInterest ?foi\n" +
                "} ORDER BY ASC(?resultTime)";
    }

    public static String insertDevices() {
        return getPrefixes() + "INSERT DATA{\n" +
                "    :sala-tv-1 a owl:NamedIndividual, :Room, sosa:Platform ;\n" +
                "    sosa:hosts :light-sensor-sala-tv-1 ;\n" +
                "    sosa:hosts :lampada-sala-tv-1-switch ;\n" +
                "    ssn:implements :switch-on-lampada-sala-tv-1 ;\n" +
                "    ssn:implements :switch-off-lampada-sala-tv-1 .\n" +
                "};\n" +
                "\n" +
                "INSERT DATA{\n" +
                "    :switch-on-lampada-sala-tv-1 a owl:NamedIndividual, sosa:Actuation ;\n" +
                "    sosa:hasSimpleResult \"on\" ;\n" +
                "    sosa:actsOnProperty :set-intensity-lampada-sala-tv-1 ;\n" +
                "    sosa:madeByActuator :lampada-sala-tv-1-switch ;\n" +
                "    sosa:resultTime \"2020-05-23T10:20:13+05:30\"^^xsd:dateTime ;\n" +
                "    sosa:hasFeatureOfInterest :lampada-sala-tv-1 .\n" +
                "};\n" +
                "\n" +
                "INSERT DATA{\n" +
                "    :switch-off-lampada-sala-tv-1 a owl:NamedIndividual, sosa:Actuation ;\n" +
                "    sosa:hasSimpleResult \"off\" ;\n" +
                "    sosa:actsOnProperty :set-intensity-lampada-sala-tv-1 ;\n" +
                "    sosa:madeByActuator :lampada-sala-tv-1-switch ;\n" +
                "    sosa:resultTime \"2020-05-23T10:20:13+05:30\"^^xsd:dateTime ;\n" +
                "    sosa:hasFeatureOfInterest :lampada-sala-tv-1 .\n" +
                "};\n" +
                "\n" +
                "INSERT DATA{\n" +
                "    :light-sensor-sala-tv-1 a owl:NamedIndividual, :LightSensor ;\n" +
                "    sosa:observes :set-intensity-lampada-sala-tv-1 ;\n" +
                "    sosa:madeObservation :observation-intensity-lampada-sala-tv-1 ;\n" +
                "    sosa:isHostedBy :sala-tv-1 .\n" +
                "};\n" +
                "\n" +
                "INSERT DATA {\n" +
                "\t:set-intensity-lampada-sala-tv-1 a owl:NamedIndividual, :SetIntensity ;\n" +
                "    ssn:isPropertyOf :lampada-sala-tv-1 .\n" +
                "};\n" +
                "\n" +
                "\n" +
                "INSERT DATA {\n" +
                "\t:intensity-lampada-sala-tv-1 a owl:NamedIndividual, :Intensity ;\n" +
                "    ssn:isPropertyOf :lampada-sala-tv-1 .\n" +
                "};\n" +
                "\n" +
                "INSERT DATA{\n" +
                "    :observation-intensity-lampada-sala-tv-1 a owl:NamedIndividual, sosa:Observation;\n" +
                "    sosa:hasSimpleValue rdfs:Literal;\n" +
                "    sosa:resultTime xsd:dateTimeStamp;\n" +
                "    sosa:madeBySensor :light-sensor-sala-tv-1;\n" +
                "    sosa:hasFeatureOfInterest :lampada-sala-tv-1 .\n" +
                "};\n" +
                "\n" +
                "INSERT DATA{\n" +
                "    :lampada-sala-tv-1 a owl:NamedIndividual, :Lamp ;\n" +
                "    ssn:hasProperty :intensity-lampada-sala-tv-1 ;\n" +
                "    ssn:hasProperty :set-intensity-lampada-sala-tv-1 .\n" +
                "};\n" +
                "\n" +
                "INSERT DATA{\n" +
                "\t:lampada-sala-tv-1-switch a owl:NamedIndividual, :LightSwitch;\n" +
                "    sosa:isHostedBy :sala-tv-1;\n" +
                "    ssn:forProperty :set-intensity-lampada-sala-tv-1;\n" +
                "    sosa:madeActuation :switch-on-lampada-sala-tv-1 .\n" +
                "};\n" +
                "\n" +
                "INSERT DATA{\n" +
                "    :switch-off-lampada-sala-tv-1 a owl:NamedIndividual, sosa:Actuation ;\n" +
                "    sosa:hasSimpleResult \"off\" ;\n" +
                "    sosa:actsOnProperty :set-intensity-lampada-sala-tv-1 ;\n" +
                "    sosa:madeByActuator :lampada-sala-tv-1-switch ;\n" +
                "    sosa:resultTime \"2021-02-06T22:42:35-03:00\"^^xsd:dateTime ;\n" +
                "    sosa:hasFeatureOfInterest :lampada-sala-tv-1 .\n" +
                "};\n" +
                "\n" +
                "INSERT DATA{\n" +
                "    :switch-on-lampada-sala-tv-1 a owl:NamedIndividual, sosa:Actuation ;\n" +
                "    sosa:hasSimpleResult \"on\" ;\n" +
                "    sosa:actsOnProperty :set-intensity-lampada-sala-tv-1 ;\n" +
                "    sosa:madeByActuator :lampada-sala-tv-1-switch ;\n" +
                "    sosa:resultTime \"2021-02-06T22:46:35-03:00\"^^xsd:dateTime ;\n" +
                "    sosa:hasFeatureOfInterest :lampada-sala-tv-1 .\n" +
                "};\n";
    }

    public static String getActuation(String hasProperty) {
        return getPrefixes() + "SELECT ?s ?p ?o ?resultTime ?hasSimpleResult WHERE {\n" +
                "   ?s a sosa:Actuation;\n" +
                "   sosa:resultTime ?resultTime ;\n" +
                "   sosa:hasSimpleResult ?hasSimpleResult ;\n" +
                "   sosa:actsOnProperty <" + hasProperty + ">" +
                "}\n" +
                "ORDER BY DESC(?resultTime)";
    }

    public static String getFeaturesOfInterestWithActuators() {
        return getPrefixes() + "SELECT ?uri ?hasProperty ?type ?actedBy WHERE{\n" +
                "  ?uri a ?subClass;\n" +
                "  ssn:hasProperty ?hasProperty ;\n" +
                "  rdf:type ?type .\n" +
                "  ?subClass rdfs:subClassOf  sosa:FeatureOfInterest .\n" +
                "  FILTER(?type != owl:NamedIndividual)\n" +
                "}";
    }

    public static String getProcedureByUri(String procedureUri) {
        return getPrefixes() + "\n SELECT ?hasSimpleValue WHERE{\n" +
                "?s rdf:type owl:NamedIndividual;\n" +
                ":hasSimpleValue ?hasSimpleValue;\n" +
                " FILTER (?s = <"+procedureUri+"> )  \n" +
                "}\n";
    }


    public static String getServicesPathByClass(String deviceClass) {
        return getPrefixes() + "SELECT ?hasPath WHERE{\n" +
                "?s a owl:NamedIndividual, :Servico;\n" +
                ":affects :" + deviceClass + ";\n" +
                ":hasPath ?hasPath . \n" +
                "}";
    }

    public static String getAcs(){
        return getPrefixes() + "SELECT ?s ?hasTemperature ?hasName ?hasId ?hasLocal WHERE{\n" +
                "?s a owl:NamedIndividual, :ArCondicionado;\n" +
                ":hasTemperature ?hasTemperature;\n" +
                ":hasName ?hasName;\n" +
                ":hasId ?hasId;\n" +
                ":hasLocal ?hasLocal .\n" +
                "}";
    }


    public static String getClasses(){
        return getPrefixes() + "SELECT ?class WHERE{\n" +
                "\t?class a owl:Class;\n" +
                "}";
    }
}