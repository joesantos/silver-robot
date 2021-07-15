package com.domoticswot.service;

import com.domoticswot.model.Loja;
import com.domoticswot.util.SparqlQueryString;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.jena.query.*;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShoppingService {

    @Autowired
    ObjectMapper mapper;

    public static void testEmbeddedFuseki(){
        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination("http://localhost:3332/ds");
        try(RDFConnectionFuseki conn = (RDFConnectionFuseki) builder.build()) {
            File file = ResourceUtils.getFile("classpath:Ontology.ttl");
            conn.put(file.toString());

            try{
                UpdateRequest updateDevice = UpdateFactory.create(SparqlQueryString.insertDevices());
                conn.update(updateDevice);
            }catch(Exception e){
                System.out.println("Erro no insert");
            }

        }catch (Exception e) {
            System.out.println("Não foi possível instanciar conexão");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static List<Loja> ListarLoja() {
        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination("http://localhost:3332/ds");

        Query getFeaturesOfInterests = QueryFactory.create(SparqlQueryString.getFeaturesOfInterest());
        Query getActuation = QueryFactory.create(SparqlQueryString.getActuationQuery());

        try(RDFConnectionFuseki conn = (RDFConnectionFuseki) builder.build()) {
            List<Loja> lojas = new ArrayList<>();

            QueryExecution qe = conn.query(getFeaturesOfInterests);
            ResultSet rsService = qe.execSelect();

            do {
                QuerySolution qs = rsService.next();
                org.apache.jena.rdf.model.Resource nome = qs.getResource("hasProperty");
                org.apache.jena.rdf.model.Resource uri = qs.getResource("uri");
                org.apache.jena.rdf.model.Resource atividade = qs.getResource("type");

                lojas.add(Loja.builder().nome(nome.getURI()).uri(uri.getURI()).atividade(atividade.getURI()).build());

            } while (rsService.hasNext());

            qe.close();

//            Query getProperties = QueryFactory.create();
//            QueryExecution qeProperty = conn.query()

            return lojas;
        } catch (Exception e) {
            System.out.println("Não foi possível instanciar conexão");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void executeUpdate(String hasSimpleValue, String propertyUri, RDFConnectionFuseki conn){
        int value = Integer.parseInt(hasSimpleValue);
        try {
            UpdateRequest updateDevice = UpdateFactory.create(SparqlQueryString.getPrefixes() +
                    "DELETE { ?s :hasLiteral false } INSERT { ?s :hasLiteral true }" +
                    "WHERE { ?s a owl:NamedIndividual FILTER(?s = <"+propertyUri+">)}"
            );

            try {
                conn.update(updateDevice);
                System.out.println("\n Atualizando o Valor da property");

            } catch (Exception e) {
                System.out.println("Não foi possível atualizar o estado do dispositivo" + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

