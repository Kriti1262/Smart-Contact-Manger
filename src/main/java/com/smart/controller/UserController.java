package com.smart.controller;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;


import org.springframework.ui.Model;



@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userrepository;
	@Autowired
	private ContactRepository contactrepository;
	 @ModelAttribute
	public void addCommonData(Model model,Principal principal) {
		 if (principal != null) {
	            String username = principal.getName();
	            User user = userrepository.getUserByUserName(username);
	            System.out.println(user);
	            model.addAttribute("user", user);
	        }
	}
	@RequestMapping("/index")
	public String dashboard(Model model,Principal principal) {
		addCommonData(model, principal);
		 model.addAttribute("title", "User Dashboard");
		    model.addAttribute("content", "user_dashboard :: content");
		return "user_dashboard";
	}
	@GetMapping("/add-contact")
	public String addContact(Model model) {
		model.addAttribute("title","Add new Contact");
		model.addAttribute("contact",new Contact());
		return "add-contact";
	}

	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact,
	                             @RequestParam("contactImage") MultipartFile file,
	                             Principal principal) {
	    try {
	        String name = principal.getName();

	        if (!file.isEmpty()) {
	            contact.setImage(file.getOriginalFilename());

	            String uploadDir = "uploads/images";  // external directory
	            File uploadPath = new File(uploadDir);
	            if (!uploadPath.exists()) {
	                uploadPath.mkdirs();
	            }

	            Path path = Paths.get(uploadPath.getAbsolutePath(), file.getOriginalFilename());
	            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
	            System.out.println("File saved at: " + path.toString());
	        } else {
	            contact.setImage(null); // or default image name
	        }

	        User user = userrepository.getUserByUserName(name);
	        contact.setUser(user);
	        user.getContacts().add(contact);

	        System.out.println("Saving user with contact: " + contact);
	        this.userrepository.save(user);

	        return "redirect:/user/view-contacts";
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	        e.printStackTrace();
	        return "add-contact";
	    }
	}

		@GetMapping("/view-contacts")
		public String viewContacts(Model model, Principal principal) {
		    String username = principal.getName();
		    User user = userrepository.getUserByUserName(username);
		    List<Contact>contacts=contactrepository.findContactsByUser(user.getId());
		    model.addAttribute("contacts", contacts);
		    model.addAttribute("title", "View Contacts");
		    return "view-contacts";
		}
	
}
