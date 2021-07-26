package com.domoticswot.resource;

import com.domoticswot.model.*;
import com.domoticswot.service.ShoppingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ShoppingResource {

    @GetMapping("/lojas")
    public List<Loja> getLojasResource() {
        return ShoppingService.getLojas();
    }

    @GetMapping("/loja")
    public List<String> getLojaPorNome( @RequestParam String nome ) {
        return null;
    }

    @GetMapping("/produtos")
    public List<Produto> getProdutosResource() {
        return ShoppingService.getProdutos();
    }

    @GetMapping("/produtosPorLoja")
    public List<Produto> getProdutosPorLoja(@RequestParam String id) {
        return ShoppingService.getProdutosPorLoja(id);
    }

    @GetMapping("/produto")
    public List<String> getProdutoPorNome(
            @RequestParam String nome
    ) {
        return null;
    }

    @PostMapping("/adicionaProduto")
    public boolean postProduto(@RequestBody CreateProductDTO body){

        System.out.println(body.getLoja());

        ShoppingService.createProduto(body.getUriProduto(),
                body.getTipoProduto(),
                body.getEVendidoEmTipo(),
                body.getLoja(),
                body.getVendeTipo(),
                body.getNomeProduto(),
                body.getValorProduto(),
                body.getTipoLoja(),
                body.getNomeLoja());

        return true;
    }

    @PostMapping("/confirmaCompra")
    public boolean postCompra(@RequestBody ConfirmaCompraDTO body){
        ShoppingService.confirmaCompra(
                body.getUsuario(),
                body.getCompraTipoProduto(),
                body.getIdProduto(),
                body.getTipoProduto()
        );

        return true;
    }

//
//
//    @GetMapping("/direcao")
//    public List<String> getDirecaoPorDestino(
//            @RequestParam String nome
//    ) {
//        return null;
//    }
//
//    @PostMapping("/produto")
//    public String novoProduto(@RequestBody Produto payload) {
//        return null;
//    }
//
//    @PostMapping("/loja")
//    public String novaLoja(@RequestBody Loja payload) {
//        return null;
//    }
//
//    @PostMapping("/atividade")
//    public String saveAtividade(@RequestBody Atividade payload) {
//        return null;
//    }
}
