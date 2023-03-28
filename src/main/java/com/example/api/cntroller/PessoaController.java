package com.example.api.cntroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.api.model.Pessoa;
import com.example.api.repository.PessoaRepository;

import jakarta.validation.Valid;

@Controller
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;

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

}
