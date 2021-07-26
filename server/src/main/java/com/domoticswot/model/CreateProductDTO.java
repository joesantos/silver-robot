package com.domoticswot.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder

public class CreateProductDTO {

    String uriProduto;
    String tipoProduto;
    String eVendidoEmTipo;
    String loja;
    String vendeTipo;
    String nomeProduto;
    String valorProduto;
    String tipoLoja;
    String nomeLoja;
}