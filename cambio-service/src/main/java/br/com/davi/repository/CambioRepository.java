package br.com.davi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.davi.model.Cambio;

public interface CambioRepository extends JpaRepository<Cambio, Long>{

	Cambio findByFromAndTo(String from, String to);
}
