package Mn.learning.SpringStarter.Controller;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import Mn.learning.SpringStarter.models.Account;
import Mn.learning.SpringStarter.services.AccountService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;




@Controller
public class ProfileController {


	@Autowired
	private AccountService accountService;

	@GetMapping("/profile")
	public String getProfile(Model model, Principal principal) {
		String authuser = "email";
		if (principal != null) {
			authuser = principal.getName();
		}
		Optional<Account> optiopnaAcc = accountService.findOneByEmail(authuser);
		if (optiopnaAcc.isPresent()) {
			Account account = optiopnaAcc.get();
			model.addAttribute("account", account);
			model.addAttribute("photo", account.getPhoto());
			return "profile_views/profile";
		} else {
			return "redirect:/";
		}
	}


@PostMapping("profile")
public String updateProfile(@Valid @ModelAttribute Account account, BindingResult bindingResult, Principal principal) {
	if (bindingResult.hasErrors()) {
		return "profile_views/profile";
	}
	String authUser = "email";
	if (principal != null) {
		authUser = principal.getName();
	}

	Optional<Account> optionalAcc = accountService.findOneByEmail(authUser);
	if (optionalAcc.isPresent()) {
		Account acc = optionalAcc.get();
		acc.setAge(account.getAge());
		acc.setDate_of_birth(account.getDate_of_birth());
		acc.setFirstname(account.getFirstname());
		acc.setGender(account.getGender());
		acc.setLastname(account.getLastname());
		acc.setPassword(account.getPassword());

		accountService.save(acc);

		SecurityContextHolder.clearContext();
		return "redirect:/";
	} else {
		return "redirect:/?error";
	}



}


	}
