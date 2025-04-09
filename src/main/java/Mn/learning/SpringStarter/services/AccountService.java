package Mn.learning.SpringStarter.services;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Mn.learning.SpringStarter.models.Account;
import Mn.learning.SpringStarter.models.Authority;
import Mn.learning.SpringStarter.repositories.AccountRepository;
import Mn.learning.SpringStarter.util.constants.Roles;


@Service
public class AccountService implements UserDetailsService {
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Account save(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		if (account.getRole() == null) {
			account.setRole(Roles.USER.getRole());
		}
		if (account.getPhoto()==null) {
			String path = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.creativefabrica.com%2Fpl%2Fproduct%2Fuser-icon-24%2F&psig=AOvVaw0Q0GImxM0_Nkd843CP2_sN&ust=1744221622843000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCLCly4OCyYwDFQAAAAAdAAAAABAE";
			account.setPhoto(path);
		}
		return accountRepository.save(account);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Account> optionalAccount = accountRepository.findByEmailIgnoreCase(username);
		if (!optionalAccount.isPresent()) {
			throw new UsernameNotFoundException("Account not found");
		}

		Account account = optionalAccount.get();
		List<GrantedAuthority> grantedAuthority = new ArrayList<>();
		grantedAuthority.add(new SimpleGrantedAuthority(account.getRole()));

		for (Authority _auth : account.getAuthority()) {
					grantedAuthority.add(new SimpleGrantedAuthority(_auth.getName()));
		}

		return new User(
				account.getEmail(),
				account.getPassword(),
				grantedAuthority // or assign roles if needed
		);

	}

	public Optional<Account> findOneByEmail(String email) {
		return accountRepository.findByEmailIgnoreCase(email);
	}
	public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }
}
