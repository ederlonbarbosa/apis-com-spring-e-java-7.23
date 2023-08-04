package com.ederlonbarbosa.mpa.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "produto")
public class Produto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "A descrição do produto é obrigatória")
    private String descricao;


    @NotNull(message = "O peso do produto é obrigatório")
    @DecimalMin(value = "0.1", message = "O peso do produto deve ser maior que 0")
    private Double peso;


    @NotNull(message = "A altura do produto é obrigatória")
    @DecimalMin(value = "0.1", message = "A altura do produto deve ser maior que 0")
    private Double altura;


    @NotNull(message = "A largura do produto é obrigatória")
    @DecimalMin(value = "0.1", message = "A largura do produto deve ser maior que 0")
    private Double largura;


    @NotBlank(message = "A cor do produto é obrigatória")
    private String cor;


    @NotNull(message = "O preço do produto é obrigatório")
    @DecimalMin(value = "0.01", message = "O preço do produto deve ser maior que 0")
    private Double preco;


    @NotBlank(message = "O SKU do produto é obrigatório")
    private String sku;
}