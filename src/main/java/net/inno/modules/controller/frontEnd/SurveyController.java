package net.inno.modules.controller.frontEnd;

import net.inno.modules.controller.BaseController;
import net.inno.modules.pojo.Question;
import net.inno.modules.pojo.Survey;
import net.inno.modules.services.IQuestionService;
import net.inno.modules.services.ISurveyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * @author lcs
 */
@Controller
public class SurveyController extends BaseController {
	private static final String PATH = FRONT_END_PATH + "survey/";
	
	@Autowired
	private IQuestionService questionService;
	@Autowired
	private ISurveyService surveyService;
	
	/**
	 * 进入测试页面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="index",method=RequestMethod.GET)
	public String index( ModelMap map ) {
		map.put("name", super.session.getAttribute("name"));
		return PATH + "index";
	}
	
	/**
	 * 进入测试页面（impress版）
	 * @param map
	 * @return
	 */
	@RequestMapping(value="impress",method=RequestMethod.GET)
	public String impress( ModelMap map ) {
		map.put("name", super.session.getAttribute("name"));
		return PATH + "impress";
	}
	
	/**
	 * 加载题目
	 * @param question
	 * @param map
	 */
	@RequestMapping(value="load")
	public void load( Question question,  ModelMap map ){
		map.put("list", this.questionService.getAll());
	}
	
	/**
	 * 加载测试结果
	 * @param question
	 * @param map
	 */
	@RequestMapping(value="loadSurvey")
	public void loadSurvey( Question question,  ModelMap map ){
		map.put("list", this.questionService.getQuestions());
		map.put("survey", super.session.getAttribute("survey"));
	}
	
	/**
	 * 提交测试结果
	 * @param survey
	 * @param map
	 */
	@RequestMapping(value="submit")
	public void submit( Survey survey , ModelMap map ){
		survey.setUserId(super.getUserId());
		super.session.setAttribute("survey", survey);
		map.put("count", 1);
	}
	
	/**
	 * 保存测试结果
	 * @param survey
	 * @param map
	 */
	@RequestMapping(value="save")
	public void save( Survey survey , ModelMap map ){
		if(super.session.getAttribute("save") == null){
			map.put("count", this.surveyService.add((Survey)super.session.getAttribute("survey")));
			super.session.setAttribute("save","save");
		}else{
			map.put("count", 0);
		}
	}
	
	/**
	 * 进入查看测试结果页面
	 * @param map
	 * @return
	 */
	@RequestMapping(value="ok")
	public String ok( ModelMap map ){
		if( super.session.getAttribute("survey") == null ){
			return REDIRECT + "index";
		}
		map.put("survey", super.session.getAttribute("survey"));
		if( super.getUserId() != null ){
			return PATH + "chart";
		}else{
			return PATH + "noLogin";
		}
	}
}
