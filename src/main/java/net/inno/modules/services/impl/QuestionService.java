package net.inno.modules.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.inno.modules.bean.ListCondition;
import net.inno.modules.dao.QuestionMapper;
import net.inno.modules.pojo.Question;
import net.inno.modules.services.IQuestionService;
/**
 * 
 * @author lcs
 *
 */
@Service("questionService")
public class QuestionService extends BaseService implements IQuestionService{
	
	@Autowired
	private QuestionMapper questionMapper;
	
	public int add(Question question) {
		if( question == null )return ADD_ERROR;
		if( !super.isString(question.getTitle()))return ADD_ERROR;

		return this.questionMapper.insert(question);
	}

	public int delete(long... ids) {
		int effect = 0;
		if( ids == null || ids.length == 0 )return effect;
		
		for( long id : ids ){
			if( id <= 0 )continue;
			
			List<Question> list = this.getQuestions(id);
			if( list != null ){
				long[] subIds = new long[list.size()];
				for(int i = 0 ; i < subIds.length ; i++  ){
					subIds[i] = list.get(i).getQuestionId();
				}
				effect += delete(subIds);
			}
			effect += this.questionMapper.delete(id);
			
		}
		return effect;
	}

	public int update(Question question) {
		if( question == null )return UPDATE_ERROR;
		if( !super.checkPojoId( question.getQuestionId() ))return UPDATE_ERROR;
		if( !super.isString(question.getTitle()))return UPDATE_ERROR;
		
		return this.questionMapper.update(question);
	}

	public List<Question> getQuestions() {
		return this.getQuestions(0);
	}

	public List<Question> getQuestions(long parentId) {
		ListCondition condition = new ListCondition();
		condition.getListSql().add(" parentId = " + parentId);
		condition.setOrderSql(" pos");
		return this.questionMapper.search(condition);
	}

	public List<Question> getAll() {
		ListCondition condition = new ListCondition();
		condition.setOrderSql(" pos");
		List<Question> list = this.questionMapper.search(condition);
		
		return list == null ? list : getSub(0,list);
	}

	private List<Question> getSub( long parentId , List<Question> sorce){
		List<Question> list = new ArrayList<Question>();
		
		for( Question q : sorce ){
			if( q.getParentId() == parentId ){
				list.add(q);
			}
		}
		
		for( Question q : list ){
			q.setSubQuestions(getSub(q.getQuestionId(),sorce));
		}
		return list;
	}

}
