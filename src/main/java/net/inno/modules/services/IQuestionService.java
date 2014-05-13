package net.inno.modules.services;

import java.util.List;

import net.inno.modules.pojo.Question;

/**
 * @author lcs
 */
public interface IQuestionService {
	/**
	 * 添加
	 * @param question
	 * @return
	 */
	int add(Question question);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(long... id);

	/**
	 * 更新
	 * @param question
	 * @return
	 */
	int update(Question question);

	/**
	 * 获取问题（大分类）
	 * @return
	 */
	List<Question> getQuestions();
	
	/**
	 * 根据父Id获取问题
	 * @param parentId
	 * @return
	 */
	List<Question> getQuestions(long parentId);
	
	/**
	 * 根据父Id获取问题
	 * @param parentId
	 * @return
	 */
	List<Question> getAll();
}
