package com.algaworks.vinhos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.vinhos.model.Vinho;

import java.util.List;

public interface Vinhos  extends JpaRepository<Vinho, Long>{

    public List<Vinho> findByNomeContainingIgnoreCase(String nome);


}
