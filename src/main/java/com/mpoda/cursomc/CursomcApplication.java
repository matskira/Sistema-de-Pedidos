package com.mpoda.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mpoda.cursomc.domain.Categoria;
import com.mpoda.cursomc.domain.Cidade;
import com.mpoda.cursomc.domain.Cliente;
import com.mpoda.cursomc.domain.Endereco;
import com.mpoda.cursomc.domain.Estado;
import com.mpoda.cursomc.domain.Produto;
import com.mpoda.cursomc.domain.enums.TipoCliente;
import com.mpoda.cursomc.repositories.CategoriaRepository;
import com.mpoda.cursomc.repositories.CidadeRepository;
import com.mpoda.cursomc.repositories.ClienteRepository;
import com.mpoda.cursomc.repositories.EnderecoRepository;
import com.mpoda.cursomc.repositories.EstadoRepository;
import com.mpoda.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	EstadoRepository estadoRepository;
	
	@Autowired
	CidadeRepository cidadeRepository;

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto prod1 = new Produto(null, "Computador", 2000.00);
		Produto prod2 = new Produto(null, "Impressora", 800.00);
		Produto prod3 = new Produto(null, "Mouse", 80.00);
	
		cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
		cat2.getProdutos().addAll(Arrays.asList(prod2));
		
		prod1.getCategorias().addAll(Arrays.asList(cat1));
		prod2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		prod3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(prod1, prod2, prod3));
		
		Estado estado1 = new Estado(null, "Minas Gerais");
		Estado estado2 = new Estado(null, "São Paulo");
		
		Cidade cidade1 = new Cidade(null, "Uberlandia", estado1);
		Cidade cidade2 = new Cidade(null, "São Paulo", estado2);
		Cidade cidade3 = new Cidade(null, "Campinas", estado1);
		
		estado1.getCidades().addAll(Arrays.asList(cidade1));
		estado2.getCidades().addAll(Arrays.asList(cidade2, cidade3));
		
		estadoRepository.saveAll(Arrays.asList(estado1, estado2));
		cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));
		
		Cliente cliente1 = new Cliente(null, "Maria", "maria@dominio.com", "48872965845", TipoCliente.PESSOAFISICA);
		cliente1.getTelefones().addAll(Arrays.asList("2752-3698", "48878-5858"));
		
		Endereco endereco1 = new Endereco(null, "Rua Dianis", "130", "Bloco01 Apt82", "Uberlandia", "544646546", cliente1, cidade1);
		Endereco endereco2 = new Endereco(null, "Rua Flores", "130", "Bloco01 Apt82", "Barueri", "06230150", cliente1, cidade2);
		
		cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
		
		clienteRepository.saveAll(Arrays.asList(cliente1));
		enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));
	}

}
