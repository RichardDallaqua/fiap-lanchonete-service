package com.fiap.lanchonete.adapter.driver;

import com.fiap.lanchonete.core.applications.services.ProdutoService;
import com.fiap.lanchonete.core.domain.Produto;
import com.fiap.lanchonete.core.domain.dto.ProdutoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<Produto> cadastrarProduto(@RequestBody @Valid ProdutoDTO produtoDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.cadastrarProduto(produtoDTO));
    }

    @GetMapping("/categorias/{categoria}")
    public ResponseEntity<List<Produto>> buscarProdutosPorCategoria(@PathVariable("categoria") String categoria){
        return ResponseEntity.ok(produtoService.buscarProdutosCategoria(categoria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable("id") String id, @RequestBody ProdutoDTO produtoDTO){
        return ResponseEntity.ok(produtoService.atualizarProduto(id, produtoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable("id") String id){
        produtoService.deletarProduto(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
