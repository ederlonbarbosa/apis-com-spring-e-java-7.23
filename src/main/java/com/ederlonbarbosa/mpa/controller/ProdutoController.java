package com.ederlonbarbosa.mpa.controller;


import com.ederlonbarbosa.mpa.model.Produto;
import com.ederlonbarbosa.mpa.service.ProdutoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto criarProduto(@RequestBody @Valid Produto produto) {
        return produtoService.criarProduto(produto);
    }

    @GetMapping
    public List<Produto> buscarTodosProdutos() {
        return produtoService.buscarTodosProdutos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long id) {
        Produto produto = produtoService.buscarProdutoPorId(id);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody @Valid Produto produto) {
        Produto produtoExistente = produtoService.buscarProdutoPorId(id);
        if (produtoExistente != null) {
            produto.setId(id);
            Produto produtoAtualizado = produtoService.atualizarProduto(produto);
            return ResponseEntity.ok(produtoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        Produto produtoExistente = produtoService.buscarProdutoPorId(id);
        if (produtoExistente != null) {
            produtoService.deletarProduto(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Produto> atualizarParcialmenteProduto(@PathVariable Long id, @RequestBody Map<String, Object> camposAtualizados) throws JsonProcessingException {
        Produto produtoAtualizado = produtoService.atualizarParcialmenteProduto(id, camposAtualizados);
        if (produtoAtualizado != null) {
            return ResponseEntity.ok(produtoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}