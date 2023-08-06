package br.com.davi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.davi.model.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

	
	
	
}
