package com.domoticswot.resource;

import com.domoticswot.model.Atividade;
import com.domoticswot.model.Loja;
import com.domoticswot.model.Produto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ShoppingResource {

    @GetMapping("/lojas")
    public List<String> getLojas() {
        return null;
    }

    @GetMapping("/loja")
    public List<String> getLojaPorNome( @RequestParam String nome ) {
        return null;
    }

    @GetMapping("/produtos")
    public List<String> getProdutos() {
        return null;
    }

    @GetMapping("/produtosPorLoja")
    public List<String> getProdutosPorLoja(@RequestParam String nome) {
        return null;
    }

    @GetMapping("/produto")
    public List<String> getProdutoPorNome(
            @RequestParam String nome
    ) {
        return null;
    }

    @GetMapping("/direcao")
    public List<String> getDirecaoPorDestino(
            @RequestParam String nome
    ) {
        return null;
    }

    @PostMapping("/produto")
    public String novoProduto(@RequestBody Produto payload) {
        return null;
    }

    @PostMapping("/loja")
    public String novaLoja(@RequestBody Loja payload) {
        return null;
    }

    @PostMapping("/atividade")
    public String saveAtividade(@RequestBody Atividade payload) {
        return null;
    }
}
