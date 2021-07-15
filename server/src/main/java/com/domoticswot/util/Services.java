package com.domoticswot.util;

import org.apache.jena.query.*;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;

import java.util.ArrayList;
import java.util.List;

public class Services {
    public static List<String> getServicesPath(String deviceClass){
        //Note
        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination("http://localhost:3030/ds");
        //Rpi
        //RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination("http://192.168.1.56:3030/test");

        Query getServices = QueryFactory.create(SparqlQueryString.getServicesPathByClass(deviceClass));

        try {
            RDFConnectionFuseki conn = (RDFConnectionFuseki) builder.build();

            QueryExecution qe = conn.query(getServices);
            ResultSet rsService = qe.execSelect();

            List<String> paths = new ArrayList<>();

            do {
                String hasPath;
                QuerySolution qs = rsService.next();
                hasPath = qs.getLiteral("hasPath").getString();
                paths.add(hasPath);
            } while (rsService.hasNext());

            qe.close();
            conn.end();
            conn.close();

            return paths;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
