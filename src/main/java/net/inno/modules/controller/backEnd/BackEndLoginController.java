package net.inno.modules.controller.backEnd;

import net.inno.modules.controller.BaseController;
import net.inno.modules.services.IQuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author lcs
 *
 */
@Controller
@RequestMapping(value="/backEnd")
public class BackEndLoginController extends BaseController {
	private static final String PATH = BACK_END_PATH + "login/";
	
	@Autowired
	private IQuestionService questionService;
	
	@RequestMapping(value="login",method=RequestMethod.GET)
	public String login() {
		return PATH + "login";
	}
	
	@RequestMapping(value="login",method=RequestMethod.POST)
	public String login(String userName , String pass) {
		if( "root123456".equals(userName + pass) ){
			super.session.setAttribute("admin","admin");
			return "redirect:" + "/backEnd/question/index";
		}
		return "redirect:" + "login?error=shit";
	}
}
