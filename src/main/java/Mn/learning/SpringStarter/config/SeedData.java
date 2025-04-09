package Mn.learning.SpringStarter.config;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import Mn.learning.SpringStarter.models.Account;
import Mn.learning.SpringStarter.models.Authority;
import Mn.learning.SpringStarter.models.Post;
import Mn.learning.SpringStarter.services.AccountService;
import Mn.learning.SpringStarter.services.AuthorityService;
import Mn.learning.SpringStarter.services.PostService;
import Mn.learning.SpringStarter.util.constants.Privilage;
import Mn.learning.SpringStarter.util.constants.Roles;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorityService authorityService;

    @Override
    public void run(String... args) throws Exception {

        for (Privilage auth : Privilage.values()) {
            Authority authority = new Authority();
            authority.setId(auth.getId());
            authority.setName(auth.getPrivilage());
            authorityService.save(authority);
        }

        Account account01 = new Account();
        account01.setEmail("account01@gmail.com");
        account01.setPassword("password123");
        account01.setFirstname("User01");
        account01.setLastname("Lasty");
        account01.setGender("Male");
        account01.setAge(25);
        account01.setDate_of_birth(LocalDate.of(2000, 1, 1));
        account01.setRole(Roles.USER.getRole());

        Account account02 = new Account();
        account02.setEmail("admin@gmail.com");
        account02.setPassword("adminpass");
        account02.setFirstname("Admin");
        account02.setLastname("Lastnmee");
        account02.setGender("Female");
        account02.setAge(30);
        account02.setDate_of_birth(LocalDate.of(1995, 6, 15));
        account02.setRole(Roles.ADMIN.getRole());

        Account account03 = new Account();
        account03.setEmail("editor@gmail.com");
        account03.setPassword("editorpass");
        account03.setFirstname("Editor");
        account03.setLastname("Lastname");
        account03.setGender("Other");
        account03.setAge(28);
        account03.setDate_of_birth(LocalDate.of(1997, 3, 10));
        account03.setRole(Roles.EDITOR.getRole());

        Account account04 = new Account();
        account04.setEmail("sp_editor@gmail.com");
        account04.setPassword("speditorpass");
        account04.setFirstname("Special");
        account04.setLastname("Editor");
        account04.setGender("Female");
        account04.setAge(35);
        account04.setDate_of_birth(LocalDate.of(1989, 9, 20));
        account04.setRole(Roles.EDITOR.getRole());

        Set<Authority> authorities = new HashSet<>();
        authorityService.findById(Privilage.RESET_ANY_USER_PASSWORD.getId()).ifPresent(authorities::add);
        authorityService.findById(Privilage.ACCESS_ADMIN_PANEL.getId()).ifPresent(authorities::add);
        account04.setAuthority(authorities);

        accountService.save(account01);
        accountService.save(account02);
        accountService.save(account03);
        accountService.save(account04);

        if (postService.getAll().isEmpty()) {
            Post post01 = new Post();
            post01.setTitle("Post 01");
            post01.setBody("post 01 body .......................");
            post01.setAccount(account01);
            postService.save(post01);

            Post post02 = new Post();
            post02.setTitle("Post 02");
            post02.setBody("post 02 body .......................");
            post02.setAccount(account02);
            postService.save(post02);
        }
    }
}
