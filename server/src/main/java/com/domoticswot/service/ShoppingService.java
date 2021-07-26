package com.domoticswot.service;

import com.domoticswot.model.Loja;
import com.domoticswot.model.Produto;
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



    public static void startEmbeddedFuseki(){
        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination("http://localhost:3332/ds");
        try(RDFConnectionFuseki conn = (RDFConnectionFuseki) builder.build()) {
            File file = ResourceUtils.getFile("classpath:Ontologia2EP-v3.ttl");
            conn.put(file.toString());

//            try{
//                UpdateRequest updateDevice = UpdateFactory.create(SparqlQueryString.insertDevices());
//                conn.update(updateDevice);
//            }catch(Exception e){
//                System.out.println("Erro no insert");
//            }

        }catch (Exception e) {
            System.out.println("Não foi possível instanciar conexão");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static List<Produto> getProdutos() {
        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination("http://localhost:3332/ds");

        Query query = QueryFactory.create(SparqlQueryString.getAvailableProducts());

        try(RDFConnectionFuseki conn = (RDFConnectionFuseki) builder.build()) {
            List<Produto> produtos = new ArrayList<>();

            QueryExecution qe = conn.query(query);
            ResultSet rsService = qe.execSelect();

            do {
                QuerySolution qs = rsService.next();
                org.apache.jena.rdf.model.Resource uri = qs.getResource("s");
                org.apache.jena.rdf.model.Resource eVendidoEm = qs.getResource("éVendido");
                org.apache.jena.rdf.model.Literal valorProduto = qs.getLiteral("valorProduto");
                org.apache.jena.rdf.model.Literal nomeProduto = qs.getLiteral("nomeProduto");

                produtos.add(Produto.builder().nome(nomeProduto.getString()).eVendidoEm(eVendidoEm.getURI()).valorProduto(valorProduto.getDouble()).uri(uri.getURI()).build());

            } while (rsService.hasNext());

            qe.close();
            conn.close();

            return produtos;
        } catch (Exception e) {
            System.out.println("Não foi possível instanciar conexão");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static List<Produto> getProdutosPorLoja(String lojaId) {
        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination("http://localhost:3332/ds");

        Query query = QueryFactory.create(SparqlQueryString.getProdutosPorLoja(lojaId));

        try(RDFConnectionFuseki conn = (RDFConnectionFuseki) builder.build()) {
            List<Produto> produtos = new ArrayList<>();

            QueryExecution qe = conn.query(query);
            ResultSet rsService = qe.execSelect();

            do {
                QuerySolution qs = rsService.next();
                org.apache.jena.rdf.model.Resource uri = qs.getResource("s");
                org.apache.jena.rdf.model.Resource eVendidoEm = qs.getResource("éVendido");
                org.apache.jena.rdf.model.Literal valorProduto = qs.getLiteral("valorProduto");
                org.apache.jena.rdf.model.Literal nomeProduto = qs.getLiteral("nomeProduto");
                org.apache.jena.rdf.model.Resource tipoProduto = qs.getResource("tipoProduto");
                org.apache.jena.rdf.model.Resource eComprado = qs.getResource("eComprado");

                if(eComprado != null){
                    System.out.println(eComprado);
                    produtos.add(Produto.builder()
                            .nome(nomeProduto.getString())
                            .eVendidoEm(eVendidoEm.getURI())
                            .valorProduto(valorProduto.getDouble())
                            .uri(uri.getURI())
                            .eComprado(eComprado.getURI())
                            .tipoProduto(tipoProduto.getURI()).build());
                }else{
                    System.out.println("SemÉComprado");
                    produtos.add(Produto.builder()
                            .nome(nomeProduto.getString())
                            .eVendidoEm(eVendidoEm.getURI())
                            .valorProduto(valorProduto.getDouble())
                            .uri(uri.getURI())
                            .tipoProduto(tipoProduto.getURI()).build());
                }



            } while (rsService.hasNext());

            qe.close();
            conn.close();

            return produtos;
        } catch (Exception e) {
            System.out.println("Não foi possível instanciar conexão");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static List<Loja> getLojas() {
        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination("http://localhost:3332/ds");

        Query query = QueryFactory.create(SparqlQueryString.getStoreNames());

        try(RDFConnectionFuseki conn = (RDFConnectionFuseki) builder.build()) {
            List<Loja> lojas = new ArrayList<>();

            QueryExecution qe = conn.query(query);
            ResultSet rsService = qe.execSelect();

            do {
                QuerySolution qs = rsService.next();
                org.apache.jena.rdf.model.Literal nome = qs.getLiteral("nomeLoja");
                org.apache.jena.rdf.model.Resource uri = qs.getResource("s");
                org.apache.jena.rdf.model.Literal atividade = qs.getLiteral("atividade");

                lojas.add(Loja.builder().nome(nome.getString()).uri(uri.getURI()).atividade(atividade.getString()).build());

            } while (rsService.hasNext());

            qe.close();
            conn.close();

            return lojas;
        } catch (Exception e) {
            System.out.println("Não foi possível instanciar conexão");
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    public static void createProduto(String uriProduto,
                                           String tipoProduto,
                                           String eVendidoEmTipo,
                                           String loja,
                                           String vendeTipo,
                                           String nomeProduto,
                                           String valorProduto,
                                           String tipoLoja,
                                           String nomeLoja) {

        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination("http://localhost:3332/ds");

        System.out.println("uriProduto: " +uriProduto+ "; " + "tipoProduto: " +tipoProduto+ "; " + "eVendidoEmTipo: " +eVendidoEmTipo+ "; " + "loja: " +loja+ "; " + "vendeTipo: " +vendeTipo+ "; " + "nomeProduto: " +nomeProduto+ "; " + "valorProduto: " +valorProduto+ "; " + "tipoLoja: " +tipoLoja+ "; " + "nomeLoja: " +nomeLoja+ "; ");

        UpdateRequest createProductQuery = UpdateFactory.create(SparqlQueryString.createNewProduct(
                uriProduto,
                tipoProduto,
                eVendidoEmTipo,
                loja,
                vendeTipo,
                nomeProduto,
                valorProduto,
                tipoLoja,
                nomeLoja)
        );

        try(RDFConnectionFuseki conn = (RDFConnectionFuseki) builder.build()) {
            conn.update(createProductQuery);
        } catch (Exception e) {
            System.out.println("Não foi possível instanciar conexão");
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    public static void confirmaCompra(    String usuario,
            String compraTipoProduto,
            String idProduto,
            String tipoProduto) {

        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination("http://localhost:3332/ds");

        UpdateRequest createProductQuery = UpdateFactory.create(SparqlQueryString.confirmaCompra(usuario, compraTipoProduto, idProduto, tipoProduto));

        try(RDFConnectionFuseki conn = (RDFConnectionFuseki) builder.build()) {
            conn.update(createProductQuery);
        } catch (Exception e) {
            System.out.println("Não foi possível instanciar conexão");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}

