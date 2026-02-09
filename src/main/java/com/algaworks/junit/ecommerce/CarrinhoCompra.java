package com.algaworks.junit.ecommerce;

import java.math.BigDecimal;
import java.util.*;

public class CarrinhoCompra {

	private final Cliente cliente;
	private final List<ItemCarrinhoCompra> itens;

	public CarrinhoCompra(Cliente cliente) {
		this(cliente, new ArrayList<>());
	}

	public CarrinhoCompra(Cliente cliente, List<ItemCarrinhoCompra> itens) {
		Objects.requireNonNull(cliente);
		Objects.requireNonNull(itens);
		this.cliente = cliente;
		this.itens = new ArrayList<>(itens); //Cria lista caso passem uma imutável
	}

	public List<ItemCarrinhoCompra> getItens() {
		return new ArrayList<>(itens);
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void adicionarProduto(Produto produto, int quantidade) {
        Objects.requireNonNull(produto);

        validarQuantidade(quantidade);

        encontrarItemPeloProduto(produto).ifPresentOrElse(item -> item.adicionarQuantidade(quantidade), () -> itens.add(new ItemCarrinhoCompra(produto, quantidade)));
	}

	public void removerProduto(Produto produto) {
        Objects.requireNonNull(produto);

        ItemCarrinhoCompra itemCarrinhoCompra = encontrarItemPeloProduto(produto).orElseThrow(RuntimeException::new);

        itens.remove(itemCarrinhoCompra);
	}

	public void aumentarQuantidadeProduto(Produto produto) {
        Objects.requireNonNull(produto);

        ItemCarrinhoCompra itemCarrinhoCompra = encontrarItemPeloProduto(produto).orElseThrow(RuntimeException::new);

        itemCarrinhoCompra.adicionarQuantidade(1);
    }

    public void diminuirQuantidadeProduto(Produto produto) {
        Objects.requireNonNull(produto);

        ItemCarrinhoCompra itemCarrinhoCompra = encontrarItemPeloProduto(produto).orElseThrow(RuntimeException::new);

        if (itemCarrinhoCompra.getQuantidade() == 1) {
            itens.remove(itemCarrinhoCompra);
        } else {
            itemCarrinhoCompra.subtrairQuantidade(1);
        }
	}

    public BigDecimal getValorTotal() {
		return itens.stream().reduce(BigDecimal.ZERO, (acumulador, item) -> acumulador.add(item.getValorTotal()), BigDecimal::add);
    }

	public int getQuantidadeTotalDeProdutos() {
		return itens.stream().reduce(0, (acumulador, item) -> acumulador + item.getQuantidade(), Integer::sum);
	}

	public void esvaziar() {
        itens.clear();
	}

    private void validarQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException();
        }
    }

    private Optional<ItemCarrinhoCompra> encontrarItemPeloProduto(Produto produto) {
        return itens.stream().filter(item -> item.getProduto().equals(produto)).findFirst();
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CarrinhoCompra that = (CarrinhoCompra) o;
		return Objects.equals(itens, that.itens) && Objects.equals(cliente, that.cliente);
	}

	@Override
	public int hashCode() {
		return Objects.hash(itens, cliente);
	}
}