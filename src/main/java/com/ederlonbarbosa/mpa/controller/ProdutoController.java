package com.ederlonbarbosa.mpa.controller;


import com.ederlonbarbosa.mpa.model.Produto;
import com.ederlonbarbosa.mpa.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Principais anotacoes do Spring e sua hierarquia
 * @Component
 * @Controller @Service                @Repository
 * @RestController
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

//    @Autowired
//    ProdutoService produtoService;

    // Via Construtor
    private final ProdutoService produtoService;

//    public ProdutoController(ProdutoService produtoService) {
//        this.produtoService = produtoService;
//    }

    //setMethods
//    ProdutoService produtoService;
//    @Autowired
//    public void setProdutoService(ProdutoService produtoService) {
//        this.produtoService = produtoService;
//    }


    @GetMapping
//    @ResponseBody
    public String hello() {
        produtoService.obter();
        return "hello";
    }

    @PostMapping()
    public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
        log.info("Descricao do produto: {}", produto.getDescricao());
        return ResponseEntity.ok(produto);
    }


}
