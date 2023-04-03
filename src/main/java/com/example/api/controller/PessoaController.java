package com.example.api.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.api.model.Pessoa;
import com.example.api.repository.PessoaRepository;
import com.example.api.services.PdfGenerateService;

import jakarta.validation.Valid;

@Controller
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
    private PdfGenerateService pdfGenerateService;

	@GetMapping("/index")
	public String showUserList(Model model) {
		model.addAttribute("pessoas", pessoaRepository.findAll());
		return "index";
	}

	@GetMapping("/signup")
	public String showSignUpForm(Pessoa pessoa) {
		return "add-pessoa";
	}

	@PostMapping("/addpessoa")
	public String addUser(@Valid Pessoa pessoa, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-pessoa";
		}

		pessoaRepository.save(pessoa);

		return "redirect:/index";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
		Pessoa pessoa = pessoaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		model.addAttribute("pessoa", pessoa);

		return "update-user";
	}

	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") Integer id, @Valid Pessoa pessoa, BindingResult result, Model model) {
		Pessoa pessoaOld = pessoaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		
		if (result.hasErrors()) {
//			pessoa.setCod(id);
			return "update-user";
		}
		
		pessoaOld.setNome(pessoa.getNome());
		pessoaOld.setEmail(pessoa.getEmail());
		pessoaOld.setGenero(pessoa.getGenero());

		pessoaRepository.save(pessoaOld);

		return "redirect:/index";
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") Integer id, Model model) {
		Pessoa pessoa = pessoaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		pessoaRepository.delete(pessoa);

		return "redirect:/index";
	}
	
	@GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> pessoasReport() throws IOException {
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		pessoas = (List<Pessoa>) pessoaRepository.findAll();

		ByteArrayInputStream bis = pdfGenerateService.peoplePDFReport(pessoas);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=pessoas.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));

	}

}
