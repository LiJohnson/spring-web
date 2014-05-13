package net.inno.modules.pojo;

import java.util.List;
 

public class Question extends BasePojo {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private long questionId;

	/**
	 * parentId
	 */
	private long parentId;

	/**
	 * 问题
	 */
	private String title;

	/**
	 * 问题类型
	 */
	private String type;

	/**
	 * 分数
	 */
	private int score;

	/**
	 * 答案
	 */
	private String answer;

	/**
	 * 其它信息
	 */
	private String other;

	/**
	 * 排序
	 */
	private int pos;
	
	/**
	 * 子问题
	 */
	private List<Question> subQuestions;
 
	public Question(){}


	/**
	 * id
	 */
	public long getQuestionId(){
		return this.questionId;
	}

	/**
	 * id
	 */
	public void setQuestionId(long questionId){
		 this.questionId = questionId; 
	}


	/**
	 * parentId
	 */
	public long getParentId(){
		return this.parentId;
	}

	/**
	 * parentId
	 */
	public void setParentId(long parentId){
		 this.parentId = parentId; 
	}


	/**
	 * 问题
	 */
	public String getTitle(){
		return this.title;
	}

	/**
	 * 问题
	 */
	public void setTitle(String title){
		 this.title = title; 
	}


	/**
	 * 问题类型
	 */
	public String getType(){
		return this.type;
	}

	/**
	 * 问题类型
	 */
	public void setType(String type){
		 this.type = type; 
	}


	/**
	 * 分数
	 */
	public int getScore(){
		return this.score;
	}

	/**
	 * 分数
	 */
	public void setScore(int score){
		 this.score = score; 
	}


	/**
	 * 答案
	 */
	public String getAnswer(){
		return this.answer;
	}

	/**
	 * 答案
	 */
	public void setAnswer(String answer){
		 this.answer = answer; 
	}


	/**
	 * 其它信息
	 */
	public String getOther(){
		return this.other;
	}

	/**
	 * 其它信息
	 */
	public void setOther(String other){
		 this.other = other; 
	}


	/**
	 * 排序
	 */
	public int getPos(){
		return this.pos;
	}

	/**
	 * 排序
	 */
	public void setPos(int pos){
		 this.pos = pos; 
	}

	/**
	 * 子问题
	 * @return
	 */
	public List<Question> getSubQuestions() {
		return this.subQuestions;
	}

	/**
	 * 子问题
	 * @param subQuestions
	 */
	public void setSubQuestions(List<Question> subQuestions) {
		this.subQuestions = subQuestions;
	}
	
	/**
	 * 答案
	 * @return
	 
	public JSONArray getAnswers(){
		if( this.answer != null ){
			try {
				return new JSONArray(this.answer).t;
			} catch (JSONException e) {}
		}
		return new JSONArray();
	}
	*/
 
}
