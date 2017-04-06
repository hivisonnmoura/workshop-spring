package com.algaworks.vinhos.controller;

import javax.validation.Valid;

import com.algaworks.vinhos.repository.filter.VinhoFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.algaworks.vinhos.model.TipoVinho;
import com.algaworks.vinhos.model.Vinho;
import com.algaworks.vinhos.repository.Vinhos;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/vinhos")
public class VinhosController {
	
	@Autowired
	private Vinhos vinhos;
	
	@GetMapping("/novo")
	public ModelAndView novo(Vinho vinho){
		ModelAndView mv = new ModelAndView("vinho/cadastro-vinho");
		mv.addObject(vinho);
		mv.addObject("tipos", TipoVinho.values());
		return mv;
	}
	
	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Vinho vinho, BindingResult result,
							   RedirectAttributes redirectAttributes){
		if(result.hasErrors())
			return novo(vinho);
		
		vinhos.save(vinho);
		// Create a 'mini'section for keep the message on screen after redirect
		redirectAttributes.addFlashAttribute("mensagem", "Vinho salvo com sucesso!");
		return new ModelAndView("redirect:/vinhos/novo");
	}

	@GetMapping
	public  ModelAndView pesquisar(VinhoFilter vinhoFilter){
		ModelAndView modelAndView = new ModelAndView("/vinho/pesquisa-vinhos");
		modelAndView.addObject("vinhos", vinhos.findByNomeContainingIgnoreCase(
				Optional.ofNullable(vinhoFilter.getNome()).orElse("%")));
		return  modelAndView;
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo){
		Vinho vinho = vinhos.findOne(codigo);
		return novo(vinho);
	}
	
}
