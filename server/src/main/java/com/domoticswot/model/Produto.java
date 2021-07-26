package com.domoticswot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = Produto.ProdutoBuilder.class)
public class Produto {

    @JsonPOJOBuilder()
    public static class ProdutoBuilder {

    }

    String uri;
    String nome;
    String eComprado;
    Double valorProduto;

    String eVendidoEm;
    String tipoProduto;

}