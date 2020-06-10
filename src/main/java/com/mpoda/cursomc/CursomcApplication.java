package com.mpoda.cursomc;

import java.text.SimpleDateFormat;
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
import com.mpoda.cursomc.domain.Pagamento;
import com.mpoda.cursomc.domain.PagamentoComBoleto;
import com.mpoda.cursomc.domain.PagamentoComCartao;
import com.mpoda.cursomc.domain.Pedido;
import com.mpoda.cursomc.domain.Produto;
import com.mpoda.cursomc.domain.enums.EstadoPagamento;
import com.mpoda.cursomc.domain.enums.TipoCliente;
import com.mpoda.cursomc.repositories.CategoriaRepository;
import com.mpoda.cursomc.repositories.CidadeRepository;
import com.mpoda.cursomc.repositories.ClienteRepository;
import com.mpoda.cursomc.repositories.EnderecoRepository;
import com.mpoda.cursomc.repositories.EstadoRepository;
import com.mpoda.cursomc.repositories.PagamentoRepository;
import com.mpoda.cursomc.repositories.PedidoRepository;
import com.mpoda.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido pedi1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cliente1, endereco1);
		Pedido pedi2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cliente1, endereco2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedi1, cliente1, endereco1, 6);
		pedi1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedi2, cliente1, endereco2, 
				sdf.parse("20/10/2017 00:00"), null);
		pedi2.setPagamento(pagto2);
		
		cliente1.getPedidos().addAll(Arrays.asList(pedi1, pedi2));
		
		pedidoRepository.saveAll(Arrays.asList(pedi1, pedi2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
	}

}
