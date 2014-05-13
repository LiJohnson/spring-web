package net.inno.modules.services.impl;

import java.util.Date;

import net.inno.modules.dao.QuestionMapper;
import net.inno.modules.dao.SurveyMapper;
import net.inno.modules.pojo.Survey;
import net.inno.modules.services.ISurveyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lcs
 */
@Service("surveyService")
public class SurveyService extends BaseService implements ISurveyService{
	
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private SurveyMapper surveyMapper;
	
	public int add(Survey survey) {
		if( survey == null )return ADD_ERROR;
		if( !super.isString(survey.getUserId()))return ADD_ERROR;
		if( !super.isString(survey.getResult()))return ADD_ERROR;
		
		survey.setTime(new Date());
		
		return this.surveyMapper.insert(survey);
	}

}
