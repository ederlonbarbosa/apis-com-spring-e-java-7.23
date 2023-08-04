package com.ederlonbarbosa.mpa.service;

import com.ederlonbarbosa.mpa.model.Produto;
import com.ederlonbarbosa.mpa.repository.ProdutoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto criarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> buscarTodosProdutos() {
        return produtoRepository.findAll();
    }

    public Produto buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    public Produto atualizarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public Produto atualizarParcialmenteProduto(Long id, Map<String, Object> camposAtualizados) throws JsonProcessingException {
        Produto produtoExistente = produtoRepository.findById(id).orElse(null);


        if (produtoExistente != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectReader reader = objectMapper.readerForUpdating(produtoExistente);
            Produto produtoAtualizado = reader.readValue(objectMapper.writeValueAsString(camposAtualizados));


            return produtoRepository.save(produtoAtualizado);
        } else {
            return null;
        }
    }
}