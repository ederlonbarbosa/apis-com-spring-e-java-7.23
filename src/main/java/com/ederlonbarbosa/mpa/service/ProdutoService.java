package com.ederlonbarbosa.mpa.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProdutoService {

    public void obter() {
        log.info("Chamou meu SERVICE");
    }
}
