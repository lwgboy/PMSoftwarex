package org.tarak.pms.controllers;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tarak.pms.models.User;
import org.tarak.pms.services.UserService;
import org.tarak.pms.utils.UserUtils;

/**
 * Created by Tarak on 12/4/2016.
 */

@RequestMapping("/")
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private HttpSession session;

	@RequestMapping("/")
	public String index(Model model) {
		if (UserUtils.validSession(session)) {
			return "home";
		}
		if (!model.containsAttribute("user")) {
			model.addAttribute("user", new User());
		}
		return "index";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "index";
		}
		try {
			userService.saveAndFlush(user);
		} catch (DataIntegrityViolationException e) {
			String args[] = { "User", user.getUsername() };
			bindingResult.rejectValue("name", "error.alreadyExists", args,
					"User with name " + user.getUsername() + " already exists");
			return "index";
		} catch (Exception e) {
			String args[] = { "User", user.getUsername() };
			bindingResult.rejectValue("name", "error.alreadyExists", args,
					"Unknown error! Please contact Administrator");
			return "index";
		}
		model.addAttribute("user", new User());
		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Valid User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("user", user);
			return "index";
		}
		User u = userService.findByUsername(user.getUsername());
		if (u != null && u.getPassword().equals(user.getPassword())) {
			Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH) + 1;
			String finYear = "";
			if (month >= 4) {
				finYear = year + "-" + (year + 1);
			} else {
				finYear = (year - 1) + "-" + year;
			}
			u.setFinancialYear(finYear);
			session.setAttribute("user", u);
			return "home";
		}
		else
		{
			bindingResult.rejectValue("username", "error.incorrectCredentials", null,
					"Incorrect Username/password. Try again");
			return "index";
		}
		
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model) {
		session.removeAttribute("user");
		session.invalidate();
		model.addAttribute("user",new User());
		return "index";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody List<User> listCategories() {
		List<User> list = userService.findAll();
		return list;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteUser(@PathVariable Integer id, Model model) {

		userService.delete(id);
		return index(model);
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editUser(@PathVariable Integer id, Model model) {
		User user = userService.findOne(id);
		model.addAttribute("user", user);
		return "edit";
	}

}
