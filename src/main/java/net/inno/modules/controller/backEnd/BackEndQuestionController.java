package net.inno.modules.controller.backEnd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import net.inno.modules.controller.BaseController;
import net.inno.modules.pojo.Question;
import net.inno.modules.services.IQuestionService;

/**
 * 
 * @author lcs
 *
 */
@Controller
@RequestMapping(value="/backEnd/question")
public class BackEndQuestionController extends BaseController {
	private static final String PATH = BACK_END_PATH + "question/";
	
	@Autowired
	private IQuestionService questionService;
	
	@RequestMapping(value="index")
	public String index() {
		return PATH + "index";
	}
	
	@RequestMapping(value="add")
	public void add( Question question,  ModelMap map ){
		map.put("count", this.questionService.add(question));
	}
	
	@RequestMapping(value="edit")
	public void update( Question question,  ModelMap map ){
		map.put("count", this.questionService.update(question));
	}
	
	@RequestMapping(value="load")
	public void load( Question question,  ModelMap map ){
		map.put("list", this.questionService.getQuestions(question.getParentId()));
	}
	
	@RequestMapping(value="delete")
	public void delete( Question question,  ModelMap map ){
		map.put("count", this.questionService.delete(question.getQuestionId()));
	}
	
}
