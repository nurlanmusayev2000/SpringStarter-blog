package Mn.learning.SpringStarter.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Mn.learning.SpringStarter.models.Authority;
import Mn.learning.SpringStarter.repositories.AutorityRepository;

@Service
public class AuthorityService {
	@Autowired
	private AutorityRepository autorityRepository;

	public Authority save(Authority authority) {
		return autorityRepository.save(authority);
	}

	public Optional<Authority> findById(Long id) {
		return autorityRepository.findById(id);
	}
}
