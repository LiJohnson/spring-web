package net.inno.myBatisPlugin.page.model;


/**
 * Created by IntelliJ IDEA.
 * User: hqq
 * Date: 12-4-15
 * Time: 下午5:20
 * To change this template use File | Settings | File Templates.
 * 
 * modify : 2012-07-19 17:49:22 添加  currentPage totallPage  totallRecord 三个属性
 */

public class PageRowBounds extends org.apache.ibatis.session.RowBounds {
	
    /**
     * 从多少开始
     */
    private int start;
    /**
     * 每页个数
     */
    private int pageSize;
    
    /**
     * 当前页码
     */
    private int currentPage ;
    
    /**
     * 当前页码(兼容分页标签里page属性)
     */
   // private int page;
   
    /**
     * 总记录数
     */
    private int totalRecord ;
    
    /**
     * 是否统计页码
     */
    private boolean isCountPage;

    public PageRowBounds(){
    	this.currentPage = 1 ;
    	this.pageSize = 12 ;
    	this.setCountPage(true);
    }
    public PageRowBounds(int start,int pageSize){
    	this();
        this.start  = start;
        this.setPageSize(pageSize);
    }

    public int getStart() {
        if((this.currentPage - 1) * this.pageSize==0){
            return start;
        }else{
            return (this.currentPage - 1) * this.pageSize;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
		return currentPage;
	}

    public void setCurrentPage(int currentPage) {
    	if(currentPage < 1){
    		currentPage = 1;
    	}
		this.currentPage = currentPage;
	}

    public int getTotalPage() {
		return (this.totalRecord + this.pageSize - 1) / this.pageSize;
	}

    public int getTotalRecord() {
		return totalRecord;
	}

    public void setTotalRecord(int totallRecord) {
		this.totalRecord = totallRecord;
	}
    public void setPage(Integer page) {
    	if( page == null || page < 1){
    		page = 1;
    	}
		this.currentPage = page;
	}

    public String getQueryKey(){
        return getStart()+"_"+getPageSize();
    }
    
	public boolean isCountPage() {
		return this.isCountPage;
	}
	/**
	 * 设置是否统计页码
	 * @param isCountPage
	 */
	public void setCountPage(boolean isCountPage) {
		this.isCountPage = isCountPage;
	}
}
