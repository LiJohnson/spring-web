package net.inno.modules.controller.frontEnd;

import net.inno.modules.bean.OauthConfig;
import net.inno.modules.controller.BaseController;
import net.inno.modules.services.IQuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lcs
 */
@Controller
public class LoginController extends BaseController {
	
	@Autowired
	private IQuestionService questionService;
	
	@RequestMapping(value="login")
	public String index( String userId , String name ) {
		if( userId == null ){
			return REDIRECT + OauthConfig.OauthURL + "?callback=" + this.currentUrl();
		}else{
			super.session.setAttribute("login", userId);
			super.session.setAttribute("name", name);
			return REDIRECT + "/ok";
		}
	}
}
