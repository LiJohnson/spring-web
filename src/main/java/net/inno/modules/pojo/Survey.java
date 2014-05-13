package net.inno.modules.pojo;

import net.inno.modules.pojo.BasePojo;
import java.util.Date;
 

public class Survey extends BasePojo {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private long surveyId;

	/**
	 * 用户id（智慧岛的）
	 */
	private String userId;

	/**
	 * 测试时间
	 */
	private Date time;

	/**
	 * 分数
	 */
	private int score;

	/**
	 * 结果
	 */
	private String result;
 
	public Survey(){}


	/**
	 * id
	 */
	public long getSurveyId(){
		return this.surveyId;
	}

	/**
	 * id
	 */
	public void setSurveyId(long surveyId){
		 this.surveyId = surveyId; 
	}


	/**
	 * 用户id（智慧岛的）
	 */
	public String getUserId(){
		return this.userId;
	}

	/**
	 * 用户id（智慧岛的）
	 */
	public void setUserId(String userId){
		 this.userId = userId; 
	}


	/**
	 * 测试时间
	 */
	public Date getTime(){
		return this.time;
	}

	/**
	 * 测试时间
	 */
	public void setTime(Date time){
		 this.time = time; 
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
	 * 结果
	 */
	public String getResult(){
		return this.result;
	}

	/**
	 * 结果
	 */
	public void setResult(String result){
		 this.result = result; 
	}

 
}
