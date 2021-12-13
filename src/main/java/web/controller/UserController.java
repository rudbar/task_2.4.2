package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.repository.RoleRepo;
import web.repository.UserRepo;
import web.service.RoleService;
import web.service.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
//@RequestMapping("/")
public class UserController {
	private UserService userService;
	private UserRepo userRepo;
	private RoleService roleService;
	private RoleRepo roleRepo;

	@Autowired
	public UserController(UserService userService, UserRepo userRepo, RoleService roleService, RoleRepo roleRepo) {
		this.userService = userService;
		this.userRepo = userRepo;
		this.roleService = roleService;
		this.roleRepo = roleRepo;
	}

	@GetMapping("/")
	public String homePage() {
		return "home";
	}

	@GetMapping("/authenticated")
	public String pageForAuthenticatedUsers(Principal principal, Model model) {
		User user = userRepo.getUserByUsername(principal.getName());
//		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("userName", user.getUsername());
		model.addAttribute("userEmail", user.getEmail());
		model.addAttribute("userRoles", user.getRoles());
		model.addAttribute("userAuthorities", user.getAuthorities());
		return "authenticated";
	}

	@GetMapping("/admin/main")
	public String mainPageForAdmin(Principal principal, Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "/admin/main";
	}

	@GetMapping("/user/user")
	public String userPageForUserAndAdmin(Principal principal, Model model) {
		User user = userRepo.getUserByUsername(principal.getName());
		model.addAttribute("userId", user.getId());
		model.addAttribute("userName", user.getUsername());
		model.addAttribute("userEmail", user.getEmail());
		model.addAttribute("userRoles", user.getRoles());
		return "/user/user";
	}

	@GetMapping("/admin/new")
	public String sendNewUserPageForAdmin(Principal principal, @ModelAttribute("user") User user) {
		return "/admin/new";
	}

	@PostMapping()
	public String createNewUserPageForAdmin(Principal principal,
											@ModelAttribute("user") User user,
											@RequestParam(value = "rolesFromCheckBox") String[] rolesFromCheckBox) {
		Set<Role> roleSet = new HashSet<>();
		for (String role : rolesFromCheckBox) {
			roleSet.add(roleService.getRoleByName(role));
		}
		user.setRoles(roleSet);
		userService.addUser(user);
		return "redirect:/admin/main";
	}

	@GetMapping("/admin/{id}/edit")
	public String editUserPageForAdmin(Principal principal, Model model, @PathVariable("id") Long id) {
		model.addAttribute("user", userService.getUserById(id));
		return "admin/edit";
	}

	@PatchMapping("/admin/{id}")
	public String updateUserPageForAdmin(Principal principal, @ModelAttribute("user") User user, @PathVariable("id") Long id) {
		userService.editUser(user);
		return "redirect:/admin/main";
	}

	@DeleteMapping("/admin/{id}")
	public String deleteUserPageForAdmin(Principal principal, @PathVariable("id") Long id) {
		userService.deleteUser(id);
		return "redirect:/admin/main";
	}



//	private final UserService userService;
//	private final RoleService roleService;
//
//	@Autowired
//	public UserController(UserService userService, RoleService roleService) {
//		this.userService = userService;
//		this.roleService = roleService;
//	}
//
//	@RequestMapping(value = "hello", method = RequestMethod.GET)
//	public String printWelcome(ModelMap model) {
//		List<String> messages = new ArrayList<>();
//		messages.add("Hello!");
//		messages.add("I'm Spring MVC-SECURITY application");
//		messages.add("5.2.0 version by sep'19 ");
//		model.addAttribute("messages", messages);
//		model.addAttribute("users", userService.getAllUsers());
//		return "hello";
//	}
//
//    @RequestMapping(value = "login", method = RequestMethod.GET)
//    public String loginPage() {
//        return "login";
//    }

}

























