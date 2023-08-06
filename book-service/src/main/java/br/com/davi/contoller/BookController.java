package br.com.davi.contoller;

import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.com.davi.model.Book;
import br.com.davi.proxy.CambioProxy;
import br.com.davi.repository.BookRepository;
import br.com.davi.response.Cambio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Book endpoint")
@RestController
@RequestMapping("book-service")
public class BookController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private BookRepository repository;
	
	@Autowired
	private CambioProxy cambioProxy;

	@Operation(summary = "Find a specific book by id")
	@GetMapping(value = "/{id}/{currency}")
	public Book findBook(@PathVariable("id") Long id, 
			@PathVariable("currency") String currency) {
		
		var book = repository.getById(id);
		
		
		if(book == null) {
			throw new RuntimeException("Book not found");
		}
		
		/*HashMap<String, String> params = new HashMap<>();
		 * Código antigo utilizado com RestTemplate, mantido aqui
		 * para consulta somente
		 * params.put("amount", book.getPrice().toString());
		params.put("from", "USD");
		params.put("to", currency);
		
		var response = new RestTemplate().getForEntity
		("http://localhost:8000/cambio-service/{amount}/{from}/{to}",
				Cambio.class, params);
		
		var cambio = response.getBody();
		book.setPrice(cambio.getConvertedValue());
		*/
		
		var cambio = cambioProxy.getCambio(book.getPrice(), "USD", currency);
		
		book.setPrice(cambio.getConvertedValue());
		
		var port = environment.getProperty("local.server.port");
		book.setEnvironment("Book port: " + port + " FEIGN "
				+ " Cambio Port: " + cambio.getEnvironment());
		
		return book;
	}
}
