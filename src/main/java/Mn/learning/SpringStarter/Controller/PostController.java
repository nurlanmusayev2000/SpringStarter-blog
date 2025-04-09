package Mn.learning.SpringStarter.Controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import Mn.learning.SpringStarter.models.Account;
import Mn.learning.SpringStarter.models.Post;
import Mn.learning.SpringStarter.services.AccountService;
import Mn.learning.SpringStarter.services.PostService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;






@Controller
public class PostController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private PostService postService;

	@GetMapping("/post/{id}")
	public String getPost(@PathVariable Long id, Model model, Principal principal) {
		Optional<Post> optionalPost = postService.getById(id);
		String authUser = "username";
		if (optionalPost.isPresent()) {
			Post post = optionalPost.get();
			model.addAttribute("post", post);
			if (principal != null) {
				authUser = principal.getName();
			}
			if (authUser.equals(post.getAccount().getEmail())) {
				model.addAttribute("isOwner", true);
			} else {
				model.addAttribute("isOwner", false);
			}
			return "post_views/post";
		} else {
			return "404";
		}
	}

@GetMapping("/post/add")
public String getMethodName(Model model, Principal principal) {
	String authUser = "email";
	if (principal != null) {
		authUser = principal.getName();
	}
	Optional<Account> optionalAcc = accountService.findOneByEmail(authUser);

	if (optionalAcc.isPresent()) {
		Post post = new Post();
		post.setAccount(optionalAcc.get());
		model.addAttribute("post", post);
		return "/post_views/post_add";
	}

	return "redirect:/";
}


@PostMapping("/post/add")
@PreAuthorize("isAuthenticated()")
public String addNewPost(@Valid @ModelAttribute Post post, BindingResult result, Principal principal) {
	if (result.hasErrors()) {
		return "/post/add";
	}

	String authUser = "email";

	if (principal != null) {
		authUser = principal.getName();
	}

		if (!post.getAccount().getEmail().equalsIgnoreCase(authUser)) {
        return "redirect:/post/add?error=unauthorized";
    }
	postService.save(post);
	return "redirect:/";
}

@GetMapping("/post/{id}/edit")
@PreAuthorize("isAuthenticated()")
public String getPostForEdit(@PathVariable Long id, Model model, Principal principal) {
	Optional<Post> optionalPost = postService.getById(id);
	if (optionalPost.isPresent()) {
		Post post = optionalPost.get();
		model.addAttribute("post", post);
		return "post_views/post_edit";
	} else {
		return "404";
	}
	}

	@PostMapping("/post/{id}/edit")
	@PreAuthorize("isAuthenticated()")
	public String updatePost( @PathVariable Long id, @Valid @ModelAttribute Post post, BindingResult result) {
	if (result.hasErrors()) {
		return "/post/{id}/edit";
	}

		Optional<Post> optinalPost = postService.getById(id);
		if (optinalPost.isPresent()) {
			Post existPost = optinalPost.get();
			existPost.setTitle(post.getTitle());
			existPost.setBody(post.getBody());
			postService.save(existPost);
		}
		return "redirect:/post/" + post.getId();
	}

	@DeleteMapping("/post/{id}/delete")
	@PreAuthorize("isAuthenticated()")
	public String deletePost(@PathVariable Long id) {
		Optional<Post> optPost = postService.getById(id);
		if (optPost.isPresent()) {
			Post existPost = optPost.get();
			postService.delete(existPost);
			return "redirect:/"; // <- better than just "/"
		}



		return "404";
	}


}
