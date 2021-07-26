package com.domoticswot.util;

public class SparqlQueryString {

    public static String getPrefixes() {
        return "PREFIX : <http://www.semanticweb.org/flavi/ontologies/2021/5/untitled-ontology-13#>\n" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX xml: <http://www.w3.org/XML/1998/namespace>\n" +
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX ssn: <http://www.w3.org/ns/ssn/>\n" +
                "PREFIX sosa: <http://www.w3.org/ns/sosa/>\n";
    }

    public static String getStoreNames(){
        return getPrefixes() + "SELECT ?s ?nomeLoja ?atividade WHERE{\n" +
                "    ?s a ?subClass ;\n" +
                "    :NomeLoja ?nomeLoja ;\n" +
                "    :Atividade ?atividade .\n" +
                "    ?subClass rdfs:subClassOf :Loja.\n" +
                "}";
    }

    public static String getStoreActivity(){
        return getPrefixes() + "SELECT ?s ?Atividade\n" +
                "WHERE{\n" +
                "   ?s a ?subClass ;\n" +
                "   :Atividade ?Atividade .\n" +
                "?  subClass rdfs:subClassOf :Loja.\n";
    }

    public static String getStoreNamesOrderByName(){
        return getPrefixes() + "SELECT ?s WHERE{\n" +
                "   ?s a ?subClass .\n" +
                "   ?subClass rdfs:subClassOf :Loja\n" +
                "}  " +
                "order by ?s\n";
    }

    public static String getAvailableProducts(){
        return getPrefixes() + "SELECT ?s ?nomeLoja ?éVendido ?valorProduto ?nomeProduto ?eComprado ?tipoProduto WHERE{\n" +
                "    ?s a ?subClass ;\n" +
                "    :ValorProduto ?valorProduto ;\n" +
                "    :NomeProduto ?nomeProduto ;\n" +
                "    :éComprado ?eComprado ; \n" +
                "    :rdf:type ?tipoProduto ;\n" +
                "    :éVendidoEmRestaurante | :éVendidoEmLojaDeRoupa | :éVendidoPorLojaDeEsporte | :éVendidoEmFarmacia ?éVendido .\n" +
                "    ?éVendido :NomeLoja ?nomeLoja .\n" +
                "    ?subClass rdfs:subClassOf :Produto.\n" +
                "}";
    }

    public static String getProdutosPorLoja(String lojaId){
        return getPrefixes() + "SELECT ?s ?nomeLoja ?éVendido ?valorProduto ?nomeProduto ?eComprado ?tipoProduto WHERE{\n" +
                "    ?s a ?subClass ;\n" +
                "    :ValorProduto ?valorProduto ;\n" +
                "    :NomeProduto ?nomeProduto ;\n" +
                "    a ?tipoProduto ;\n" +
                "    :éVendidoEmRestaurante | :éVendidoEmLojaDeRoupa | :éVendidoPorLojaDeEsporte | :éVendidoEmFarmacia ?éVendido .\n" +
                "    OPTIONAL { ?s :éComprado ?eComprado .} \n "+
                "    ?éVendido :NomeLoja ?nomeLoja .\n" +
                "    ?subClass rdfs:subClassOf :Produto .\n" +
                "    FILTER(?éVendido = :"+lojaId+")\n" +
                "    FILTER(?tipoProduto != owl:NamedIndividual)\n" +
                "}";
    }

    public static String getUsers(){
        return getPrefixes() + "SELECT ?s WHERE{\n" +
                "   ?s a :Usuario;\n" +
                "}\n";
    }

    public static String createNewProduct(String uriProduto,
                                          String tipoProduto,
                                          String eVendidoEmTipo,
                                          String loja,
                                          String vendeTipo,
                                          String nomeProduto,
                                          String valorProduto,
                                          String tipoLoja,
                                          String nomeLoja){
        return getPrefixes() + "INSERT DATA\n" +
                "{ \n" +
                "  :"+uriProduto+" a owl:NamedIndividual ,\n" +
                "      :"+tipoProduto+" ;\n" +
                "    :"+eVendidoEmTipo+" :"+loja+" ;\n" +
                "    :NomeProduto \" "+nomeProduto+" \"^^rdfs:Literal ;\n" +
                "    :ValorProduto \" "+valorProduto+"\" .\n" +
                "};\n" +
                "\n" +
                "INSERT { ?loja :"+vendeTipo+" :"+uriProduto+" }\n" +
                "WHERE {\n" +
                "  ?loja a owl:NamedIndividual, :"+tipoLoja+" ;\n" +
                "  :NomeLoja \""+nomeLoja+"\"^^rdfs:Literal\n" +
                "}";
    }

    public static String confirmaCompra(String usuario, String compraTipoProduto, String idProduto, String tipoProduto) {
        return getPrefixes() + "INSERT DATA {\n" +
                ":"+usuario+" :"+compraTipoProduto+" :"+idProduto+" .\n" +
                "};" +
                "" +
                "INSERT { ?produto :éComprado :"+usuario+" }\n" +
                "WHERE {\n" +
                "    ?produto a owl:NamedIndividual, :"+tipoProduto+" .\n" +
                "FILTER(?produto = :"+idProduto+")                \n" +
                "}";
    }

    public static String getStoreTypes(){
        return getPrefixes() + "SELECT ?s\n" +
                "WHERE {\n" +
                "    ?s rdfs:subClassOf :Loja  ;\n" +
                "}\n";
    }

    public static String getSpecificStore(String nomeLoja){
        return getPrefixes() + "SELECT ?s ?Atividade\n" +
                "WHERE{\n" +
                "   ?s a ?subClass ;\n" +
                "   :Atividade ?Atividade .\n" +
                "   ?subClass rdfs:subClassOf :Loja.\n" +
                "   FILTER(?nomeLoja = "+nomeLoja+"^^rdfs:Literal)\n" +
                "}\n";
    }

    public static String getStoreAddress(){
        return getPrefixes() + "SELECT ?s ?endereco WHERE{\n" +
                "   ?s a ?subClass ;\n" +
                "   :Endereço ?endereco .\n" +
                "   ?subClass rdfs:subClassOf :Loja.\n" +
                "}\n";
    }
}
