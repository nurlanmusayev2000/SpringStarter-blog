package Mn.learning.SpringStarter.Controller;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import Mn.learning.SpringStarter.models.Account;
import Mn.learning.SpringStarter.services.AccountService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




@Controller
public class AccountController {


@Autowired
private AccountService accountService;

@Value("${password.token.reset.timeout.minutes}")
private int password_token_timeout;


@GetMapping("/register")
public String register(Model model) {
	Account account = new Account();
	model.addAttribute("account", account);
	return "register";
}

@PostMapping("/register")
public String register_user(@Valid @ModelAttribute Account account, BindingResult result) {
	if (result.hasErrors()) {
		return "/register";
	}
	accountService.save(account);
	return "redirect:/";
}

@GetMapping("/login")
public String login_user(Model model) {
	return "login";
}


@GetMapping("/forgot-password")
public String forgot_password(Model model) {
	return "forgot_password";
}

//@PostMapping("/reset_password")
//public String reset_password(@RequestParam("email") String _email, RedirectAttributes attributes, Model model) {
//	Optional<Account> optionalAcc = accountService.findOneByEmail(_email);
//	if (optionalAcc.isPresent()) {
//		Account acc = accountService.findById(optionalAcc.get().getId()).get();
//		String resetToken = UUID.randomUUID().toString();
//		acc.setPassword_reset_toke(resetToken);
//		acc.setPassword_reset_token_expiry(LocalDateTime.now().plusMinutes(password_token_timeout));
//		accountService.save(acc);
//	  attributes.addFlashAttribute("message", "Password reset sent to email");

//		return "redirect:/login";
//	} else {
//		  attributes.addFlashAttribute("error", "No User Found with this email");
//			return "redirect:/forgot_password";

//	}
//}
	@PostMapping("/reset_password")
	public String reset_password(@RequestParam("email") String email, RedirectAttributes attributes) {
			Optional<Account> optionalAcc = accountService.findOneByEmail(email);

			if (optionalAcc.isPresent()) {
					Account acc = optionalAcc.get();
					String resetToken = UUID.randomUUID().toString();

					acc.setPassword_reset_toke(resetToken);
					acc.setPassword_reset_token_expiry(LocalDateTime.now().plusMinutes(password_token_timeout));
					accountService.save(acc);

					attributes.addFlashAttribute("message", "Password reset instructions sent to your email.");
					return "redirect:/login";
			} else {
					attributes.addFlashAttribute("error", "No user found with this email.");
					return "redirect:/forgot-password"; // ✅ make sure your mapping and file match
			}
	}
}
