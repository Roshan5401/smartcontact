package com.smart.controller;

import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
		@Autowired
		private UserRepository userRepository;
		@GetMapping("/")
		public String home()
		{
			return "home";
		}
		
		///clicking on the submit this url will work
		@RequestMapping(value="/do_register",method=RequestMethod.POST)
		public String registerUser(@ModelAttribute("user") User user,@RequestParam(value="agreement",defaultValue="false")boolean agreement,Model model,HttpSession session)
		{
			try
			{
				if(!agreement)
				{
					System.out.println("You have to check the agreement first");
					throw new Exception("You have to check the agreement first");
				}
				user.setEnabled(true);
				user.setRole("ROLE_USER");
				
				User result=this.userRepository.save(user);
				System.out.println(result);
				model.addAttribute("user",new User());
				session.setAttribute("message",new com.smart.helper.Message("Done", "alert-success"));
				
			}catch (Exception e) {
				
				e.printStackTrace();
				
				model.addAttribute("user",user);
				session.setAttribute("message", new com.smart.helper.Message("Something went wrong "+e.getMessage(),"alert-error"));
			}
			return "signup";
		}

		@GetMapping("/about")
		public String about()
		{
			return "about";
		}
		
		
		//it will load the signup page
		@RequestMapping("/signup")
		public String signup(Model model)
		{
			model.addAttribute("user",new User());
			return "signup";
		}
		@GetMapping("/test")
		@ResponseBody
		public String test()
		{
			User user=new User();
			user.setName("Roshan");
			user.setEmail("Roshan@gmail.com");
			
			Contact contact=new Contact();
			user.getContacts().add(contact);
			
			userRepository.save(user);
			return "working";
		}
}
