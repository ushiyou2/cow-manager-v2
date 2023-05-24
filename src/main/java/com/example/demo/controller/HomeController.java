package com.example.demo.controller;

import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Cow;
import com.example.demo.model.SiteUser;
import com.example.demo.repository.CowRepository;
import com.example.demo.service.OCRService;
import com.example.demo.service.SiteUserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final CowRepository cowRepository;	
	private final OCRService ocrService;
	private final SiteUserService userService;
	
	@GetMapping("/")
	public String index(OAuth2AuthenticationToken authentication, @AuthenticationPrincipal OAuth2User oauth2User, Model model) {
		if(Objects.isNull(authentication)) {
			return "redirect:/login";
		}
		
		SiteUser loginUser = userService.findUserFromOAuth2(oauth2User);
	    model.addAttribute("username", loginUser.getUsername());
	    return "index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/{username}/cows")
	public String showCows(@AuthenticationPrincipal OAuth2User oauth2User, Model model) {
		
		SiteUser loginUser = userService.findUserFromOAuth2(oauth2User);
		model.addAttribute("cows", cowRepository.findBySiteUser(loginUser));
		model.addAttribute("siteUser", loginUser);
		model.addAttribute("username", loginUser.getUsername());
		model.addAttribute("cow", new Cow());
        return "cows";
	}
	
	@PostMapping("/{username}/cows/add")
	public String addCow(@PathVariable String username, @AuthenticationPrincipal OAuth2User oauth2User, @Validated @ModelAttribute Cow cow, BindingResult result) {
	    if (result.hasErrors()) {
	        return "redirect:/" + username + "/cows";
	    }
	    
	    SiteUser loginUser = userService.findUserFromOAuth2(oauth2User);
	    cow.setSiteUser(loginUser);
        cowRepository.save(cow);
        return "redirect:/" + username + "/cows";

	}
	
	@PostMapping("/{username}/cows/uploadImage")
	public String uploadImage(@PathVariable String username, @RequestParam("file") MultipartFile file, Model model) {
	    String extractedText = ocrService.extractTextFromImage(file);
	    System.out.println("Extracted Text: " + extractedText);
	    model.addAttribute("recognizedText", extractedText);
	    return "redirect:/" + username + "/cows";
	}

	
	@GetMapping("/{username}/cows/edit/{id}")
	public String editCow(@PathVariable String username, @PathVariable Long id, Model model) {
		model.addAttribute("cows", cowRepository.findById(id));
		return "redirect:/" + username + "/cows";
	}
	
	@PostMapping("/{username}/cows/delete/{id}")
	public String deleteCow(@PathVariable String username, @PathVariable Long id) {
		cowRepository.deleteById(id);
		return "redirect:/" + username + "/cows";
	}
	
	@ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException e, Model model) {
		//Log the exception message if needed
        System.err.println(e.getMessage());
        // Redirect the user to the login page
        return "redirect:/login";
    }
}
