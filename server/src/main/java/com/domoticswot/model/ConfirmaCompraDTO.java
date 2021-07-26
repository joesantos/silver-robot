package com.domoticswot.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder

public class ConfirmaCompraDTO {

    String usuario;
    String compraTipoProduto;
    String idProduto;
    String tipoProduto;
}