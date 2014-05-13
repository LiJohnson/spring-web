package net.inno.tags.page;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.inno.myBatisPlugin.page.model.PageRowBounds;

import org.apache.commons.lang.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-5-31
 * Time: 下午4:50
 * To change this template use File | Settings | File Templates.
 */
public class PageStyleTag extends BodyTagSupport {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
     * property declaration for tag attribute: TotalPages.
     */
    private int totalPages;
    /**
     * property declaration for tag attribute: CurrentPage.
     */
    private int currentPage;
    /**
     * property declaration for tag attribute: Language.
     */
    private java.lang.String language = "Simplified Chinese";
    /**
     * property declaration for tag attribute: Style.
     */
    private java.lang.String style = "Standard";
    /**
     * Holds value of property totalRecords.
     */
    private int totalRecords;
    private String frontPath;
    private int displayNum;
    private boolean isDisplaySelect;
    private boolean isDisplayGoToPage;
    private boolean isReWrite;
    private String reWriteUrl;
    private String sParams;
    private String previousStyle;//上一页样式
    private String previousHtml;//上一页html
    private boolean isFirstPage;//是否是示第一页html
    private String firstPageHtml;//首页html
    private boolean isLastPage;//是否显示最后一页html
    private String lastPageHtml;//最后一页html
    private String nextStyle;//下一页样式
    private String nextHtml;//下一页html
    private boolean isDivId;
    private String divCss;
    private String itemStyle;
    private String nowPageStyle;
    private boolean isStyleBootstrap;
    private boolean isNextRemind;//是否提示"目前已是第一页/最后一页"
    private PageRowBounds page;
    private String filterParam;	//过滤不要的参数 

    public PageStyleTag() {
        super();
        displayNum = 10;
        isDivId = true;
        isDisplaySelect = true;
        isFirstPage = false;
        isLastPage = false;
        isReWrite = false;
        isDisplayGoToPage = false;
        isStyleBootstrap  = false;
        isNextRemind = true;
    }

    public String getNowPageStyle() {
        return nowPageStyle;
    }

    public void setNowPageStyle(String nowPageStyle) {
        this.nowPageStyle = nowPageStyle;
    }

    public boolean isDisplaySelect() {
        return isDisplaySelect;
    }

    public void setDisplaySelect(boolean displaySelect) {
        isDisplaySelect = displaySelect;
    }

    public String getLastPageHtml() {
        return lastPageHtml;
    }

    public void setLastPageHtml(String lastPageHtml) {
        this.lastPageHtml = lastPageHtml;
    }

    public String getFirstPageHtml() {
        return firstPageHtml;
    }

    public void setFirstPageHtml(String firstPageHtml) {
        this.firstPageHtml = firstPageHtml;
    }

    public String getItemStyle() {
        return itemStyle;
    }

    public void setItemStyle(String itemStyle) {
        this.itemStyle = itemStyle;
    }

    public String getDivCss() {
        return divCss;
    }

    public void setDivCss(String divCss) {
        this.divCss = divCss;
    }

    public boolean getIsDivId() {
        return isDivId;
    }

    public void setIsDivId(boolean divId) {
        isDivId = divId;
    }

    public String getNextHtml() {
        return nextHtml;
    }

    public void setNextHtml(String nextHtml) {
        this.nextHtml = nextHtml;
    }

    public String getNextStyle() {
        return nextStyle;
    }

    public void setNextStyle(String nextStyle) {
        this.nextStyle = nextStyle;
    }

    public boolean getIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public boolean getIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public String getPreviousStyle() {
        return previousStyle;
    }

    public void setPreviousStyle(String previousStyle) {
        this.previousStyle = previousStyle;
    }

    public String getPreviousHtml() {
        return previousHtml;
    }

    public void setPreviousHtml(String previousHtml) {
        this.previousHtml = previousHtml;
    }

    public boolean getIsStyleBootstrap() {
        return isStyleBootstrap;
    }

    public void setIsStyleBootstrap(boolean styleBootstrap) {
        isStyleBootstrap = styleBootstrap;
    }

    ////////////////////////////////////////////////////////////////
    ///                                                          ///
    ///   User methods.                                          ///
    ///                                                          ///
    ///   Modify these methods to customize your tag handler.    ///
    ///                                                          ///
    ////////////////////////////////////////////////////////////////
    //
    // methods called from doStartTag()
    //


	public boolean getIsNextRemind() {
		return isNextRemind;
	}

	public void setIsNextRemind(boolean isNextRemind) {
		this.isNextRemind = isNextRemind;
	}

	/**
     * Fill in this method to perform other operations from doStartTag().
     */
    public void otherDoStartTagOperations() {

        //
        // TODO: code that performs other operations in doStartTag
        //       should be placed here.
        //       It will be called after initializing variables,
        //       finding the parent, setting IDREFs, etc, and
        //       before calling theBodyShouldBeEvaluated().
        //
        //       For example, to print something out to the JSP, use the following:
        //
        try {
            JspWriter out = pageContext.getOut();
            String outPrintHtml = "";
            if(isStyleBootstrap){
                outPrintHtml = showBootStrapStyle();
            }else{
                outPrintHtml = showStandardStyle();
            }
            out.println(outPrintHtml);
        } catch (java.io.IOException ex) {
            // do something
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String showBootStrapStyle() throws Exception {
        StringBuilder re = new StringBuilder();
        String cleanUrl = getCleanUrl();

        re.append("<div id='bootStrapPage'><ul>");

        if (currentPage > 1) {
            re.append("<li><a href='" + getCompleteUrl(cleanUrl, currentPage - 1) +"'  title='上一页'>上一页</a></li>");
        } else if(isNextRemind){
        		re.append("<li class='disabled'><a title='目前已是第一页'>上一页</a></li>");
        }

        //计算显示的页
        if (displayNum == 0) {
            re.append("<li ><span class='currentPage'>" + currentPage + "</span>/" + totalPages + "</li>");
        } else {
            int pagenumber = displayNum;
            int pagecenter = pagenumber / 2 - 1;
            int pagebet = pagenumber / 2 + 1;
            int beginPage = 1;
            int endPage = 1;

            if (currentPage < pagebet) {
                beginPage = 1;
            } else {
                beginPage = currentPage - pagecenter;
            }

            if (currentPage + pagecenter > totalPages) {
                endPage = totalPages;
            } else {
                endPage = currentPage + pagecenter;
            }

            if (currentPage + pagecenter < pagenumber) {
                endPage = pagenumber;
            }

            if (endPage - currentPage < pagecenter) {
                beginPage = totalPages - (pagenumber - 1);
                if (beginPage != 1) {
                    beginPage += 1;
                }
            }

            if (beginPage <= 0) {
                beginPage = 1;
            }

            if (endPage > totalPages) {
                endPage = totalPages;
            }

            if (currentPage >= pagebet && beginPage != 1) {
                re.append("<li><a href='" + getCompleteUrl(cleanUrl, 1) + "'>1</a></li>");
                if (currentPage != pagebet) {
                    re.append("<li class='disabled'><a href='#'>...</a></li>");
                }
            }

            for (int i = beginPage; i <= endPage; i++) {
                if (i != currentPage) {
                    re.append("<li><a href='" + getCompleteUrl(cleanUrl, i) + "'>" + i + "</a></li>");
                } else {
                    re.append("<li class='active'><a>" + i + "</a></li>");
                }
            }
        }
        if (currentPage < totalPages) {
            re.append("<li><a href='" + getCompleteUrl(cleanUrl, currentPage + 1) + "'  title='下一页'>下一页</a></li>");
        } else if(isNextRemind){
            re.append("<li class='disabled'><a title='目前已是最后一页'>下一页</a></li>");
        }

        boolean isSelect = isDisplaySelect;
        if (isDisplayGoToPage) {
            isSelect = false;
            re.append("<li>&nbsp;&nbsp;到第&nbsp;<input id='inputPage' value='" + currentPage + "'/>&nbsp;页</li>");
            String script = "javascript:var goPage=this.parentNode.parentNode.getElementsByTagName('input')[0].value;if(isNaN(goPage)||goPage>"
                    + totalPages + "||goPage<1||goPage==" + currentPage + ")return;document.location='" + getCompleteUrlNoParam(cleanUrl)
                    + "page='+goPage;return false;";
            re.append("<li><a href='javascript:;' onclick=\"" + script + "\" class='goToPage'></a></li>");
        }

        if (isSelect) {
            //下拉的翻页可以不显示
            re.append("<li>&nbsp;&nbsp;到第&nbsp;<select name='select2' onchange=\"window.location.href='" +
                    getCompleteUrlNoParam(cleanUrl) +
                    "page='+this.options[this.selectedIndex].value + ''\">");
            for (int iCount = 1; iCount <= totalPages; iCount++) {
                String strSelected = "";
                if (iCount == currentPage) {
                    strSelected = "selected";
                }
                re.append("<option value='" + iCount + "' " + strSelected + ">-" + iCount + "-</option>");
            }
            re.append("</select>&nbsp;页</li>");
        }
        re.append("</ul></div>");
        return re.toString();
    }

    /**
     * Fill in this method to determine if the tag body should be evaluated
     * Called from doStartTag().
     */
    public boolean theBodyShouldBeEvaluated() {

        //
        // TODO: code that determines whether the body should be
        //       evaluated should be placed here.
        //       Called from the doStartTag() method.
        //
        return true;

    }
    //
    // methods called from doEndTag()
    //

    /**
     * Fill in this method to perform other operations from doEndTag().
     */
    public void otherDoEndTagOperations() {    //
        // TODO: code that performs other operations in doEndTag
        //       should be placed here.
        //       It will be called after initializing variables,
        //       finding the parent, setting IDREFs, etc, and
        //       before calling shouldEvaluateRestOfPageAfterEndTag().
        //
    }

    /**
     * Fill in this method to determine if the rest of the JSP page
     * should be generated after this tag is finished.
     * Called from doEndTag().
     */
    public boolean shouldEvaluateRestOfPageAfterEndTag() {

        //
        // TODO: code that determines whether the rest of the page
        //       should be evaluated after the tag is processed
        //       should be placed here.
        //       Called from the doEndTag() method.
        //
        return true;

    }

    public int doStartTag() throws JspException, JspException {
        otherDoStartTagOperations();

        if (theBodyShouldBeEvaluated()) {
            return EVAL_BODY_BUFFERED;
        } else {
            return SKIP_BODY;
        }
    }

    /**
     * .
     * <p/>
     * <p/>
     * This method is called after the JSP engine finished processing the tag.
     *
     * @return EVAL_PAGE if the JSP engine should continue evaluating the JSP page, otherwise return SKIP_PAGE.
     *         This method is automatically generated. Do not modify this method.
     *         Instead, modify the methods that this method calls.
     */
    public int doEndTag() throws JspException, JspException {
        otherDoEndTagOperations();

        if (shouldEvaluateRestOfPageAfterEndTag()) {
            return EVAL_PAGE;
        } else {
            return SKIP_PAGE;
        }
    }

    public java.lang.String getLanguage() {
        return language;
    }

    public void setLanguage(java.lang.String value) {
        language = value;
    }

    public java.lang.String getStyle() {
        return style;
    }

    public void setStyle(java.lang.String value) {
        style = value;
    }

    /**
     * .
     * Fill in this method to process the body content of the tag.
     * You only need to do this if the tag's BodyContent property
     * is set to "JSP" or "tagdependent."
     * If the tag's bodyContent is set to "empty," then this method
     * will not be called.
     */
    public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException {
        //
        // TODO: insert code to write html before writing the body content.
        //       e.g.  out.println("<B>" + getAttribute1() + "</B>");
        //             out.println("   <BLOCKQUOTE>");

        //
        // write the body content (after processing by the JSP engine) on the output Writer
        //
        bodyContent.writeOut(out);

        //
        // Or else get the body content as a string and process it, e.g.:
        //     String bodyStr = bodyContent.getString();
        //     String result = yourProcessingMethod(bodyStr);
        //     out.println(result);
        //

        // TODO: insert code to write html after writing the body content.
        //       e.g.  out.println("   <BLOCKQUOTE>");

        // clear the body content for the next time through.
        bodyContent.clearBody();
    }

    /**
     * .
     * <p/>
     * Handles exception from processing the body content.
     */
    public void handleBodyContentException(Exception ex) throws JspException {
        // Since the doAfterBody method is guarded, place exception handing code here.
        throw new JspException("error in PageSeperatorTag: " + ex);
    }

    /**
     * .
     * <p/>
     * <p/>
     * This method is called after the JSP engine processes the body content of the tag.
     *
     * @return EVAL_BODY_AGAIN if the JSP engine should evaluate the tag body again, otherwise return SKIP_BODY.
     *         This method is automatically generated. Do not modify this method.
     *         Instead, modify the methods that this method calls.
     */
    public int doAfterBody() throws JspException {
        try {
            //
            // This code is generated for tags whose bodyContent is "JSP"
            //
            BodyContent bodyContent = getBodyContent();
            JspWriter out = bodyContent.getEnclosingWriter();

            writeTagBodyContent(out, bodyContent);
        } catch (Exception ex) {
            handleBodyContentException(ex);
        }

        if (theBodyShouldBeEvaluatedAgain()) {
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
    }

    /**
     * Fill in this method to determine if the tag body should be evaluated
     * again after evaluating the body.
     * Use this method to create an iterating tag.
     * Called from doAfterBody().
     */
    public boolean theBodyShouldBeEvaluatedAgain() {
        //
        // TODO: code that determines whether the tag body should be
        //       evaluated again after processing the tag
        //       should be placed here.
        //       You can use this method to create iterating tags.
        //       Called from the doAfterBody() method.
        //
        return false;
    }

    private String getCleanUrl() throws Exception {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        Map<String,String[]> parameters = request.getParameterMap();
        boolean isFirst = true;
        Set<Entry<String, String[]>> entries = parameters.entrySet();
        Iterator<Entry<String, String[]>> it = entries.iterator();
        String reqUrl = (String) request.getAttribute("originurl");
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        StringBuilder params = new StringBuilder();
        StringBuilder reqUrls = new StringBuilder();
        reqUrls.append(reqUrl);
        String lastName="";
        while (it.hasNext()) {
        	Entry<String, String[]> entry =  it.next();
            String Name = entry.getKey();
            String[] Value = entry.getValue();
            String[] temp = new String[Value.length];
           
            for (int i = 0; i < Value.length; i++) {
                temp[i] = URLEncoder.encode(Value[i], "UTF-8");
            }
            if( this.getFilterParam().indexOf(Name) != -1 )
            	continue;
            
            if (Name.equalsIgnoreCase("page") == false) {
                for (int j = 0; j < temp.length; j++) {
                    if (StringUtils.isNotEmpty(temp[j])) {
                        if(lastName.equalsIgnoreCase(Name)){
                            continue;
                        }
                        if (isFirst) {
                            isFirst = false;
                            if (isReWrite) {
                                params.append(Name + "=" + temp[j]);
                            } else {
                                reqUrls.append("?");
                                reqUrls.append(Name);
                                reqUrls.append("=");
                                reqUrls.append(temp[j]);
                                //reqUrl += "?" + Name + "=" + temp[j];
                            }
                        } else {
                            String param = "&" + Name + "=" + temp[j];
                            String param2 = "&" + Name + "=" + temp[j] + "&";
                            if (reqUrl.indexOf(param2) < 0) {
                                if (reqUrl.endsWith(param) == false) {
                                    if (isReWrite) {
                                        params.append(param);
                                    } else {
                                        reqUrls.append(param);
                                        //reqUrl += param;
                                    }
                                }
                            }
                        }
                        lastName = Name;
                    }
                }
            }
        }
        String enCodeUrl = "";
        if (isReWrite) {
            sParams = params.toString();
            enCodeUrl = response.encodeURL(reWriteUrl + sParams);
        } else {
            enCodeUrl = response.encodeURL(reqUrls.toString());
        }
        return enCodeUrl;
    }

    private String getCompleteUrl(String reqUrl, int page) {
        if (isReWrite) {
            if (sParams.length() > 1) {
                return reqUrl + "&page=" + page + ".html";
            } else {
                return reqUrl + "page=" + page + ".html";
            }
        } else {
            if (reqUrl.indexOf("?") > 0) {
                return reqUrl + "&page=" + page;
            } else {
                return reqUrl + "?page=" + page;
            }
        }

    }

    private String getCompleteUrlNoParam(String reqUrl) {
        if (reqUrl.indexOf("?") > 0) {
            return reqUrl + "&";
        } else {
            return reqUrl + "?";
        }
    }

    private String showStandardStyle() throws Exception {
        StringBuilder re = new StringBuilder();
        String cleanUrl = getCleanUrl();
        if( this.totalPages <= 1 ){
        	return "";
        }
        re.append("<div " + (isDivId ? "id='infoPage'" : "") + " " + (StringUtils.isNotBlank(divCss) ? "class='" + divCss + "'" : "") + "><ul>");

        if (currentPage > 1) {
            if (isFirstPage) {
                re.append("<li><a href='" + getCompleteUrl(cleanUrl, 1) + "' class='pag2' title='第一页'>" + (StringUtils.isNotBlank(firstPageHtml) ? firstPageHtml : "<<") + "</a></li>");
            }
            re.append("<li><a href='" + getCompleteUrl(cleanUrl, currentPage - 1) +
                    "' class='" + (StringUtils.isNotBlank(previousStyle) ? previousStyle : "upPage") + "' title='上一页'>" + (StringUtils.isNotBlank(previousHtml) ? previousHtml : "上一页") + "</a></li>");
        } else if(isNextRemind){
        		re.append("<li><a class='" + (StringUtils.isNotBlank(previousStyle) ? previousStyle : "upPage") + "' title='目前已是第一页'>" + (StringUtils.isNotBlank(previousHtml) ? previousHtml : "") + "</a></li>");
        }

        //计算显示的页
        if (displayNum == 0) {
            re.append("<li class='pages'><span class='currentPage'>" + currentPage + "</span>/" + totalPages + "</li>");
        } else {
            int pagenumber = displayNum;
            int pagecenter = pagenumber / 2 - 1;
            int pagebet = pagenumber / 2 + 1;
            int beginPage = 1;
            int endPage = 1;

            if (currentPage < pagebet) {
                beginPage = 1;
            } else {
                beginPage = currentPage - pagecenter;
            }

            if (currentPage + pagecenter > totalPages) {
                endPage = totalPages;
            } else {
                endPage = currentPage + pagecenter;
            }

            if (currentPage + pagecenter < pagenumber) {
                endPage = pagenumber;
            }

            if (endPage - currentPage < pagecenter) {
                beginPage = totalPages - (pagenumber - 1);
                if (beginPage != 1) {
                    beginPage += 1;
                }
            }

            if (beginPage <= 0) {
                beginPage = 1;
            }

            if (endPage > totalPages) {
                endPage = totalPages;
            }

            if (currentPage >= pagebet && beginPage != 1) {
                re.append("<li><a href='" + getCompleteUrl(cleanUrl, 1) + "' class='" + (StringUtils.isNotBlank(itemStyle) ? itemStyle : "everyPage") + "'>1</a></li>");
                if (currentPage != pagebet) {
                    re.append("<li>...&nbsp;</li>");
                }
            }

            for (int i = beginPage; i <= endPage; i++) {
                if (i != currentPage) {
                    re.append("<li><a href='" + getCompleteUrl(cleanUrl, i) + "' class='" + (StringUtils.isNotBlank(itemStyle) ? itemStyle : "everyPage") + "'>" + i + "</a></li>");
                } else {
                    re.append("<li><a class='"+(StringUtils.isNotBlank(nowPageStyle) ?  nowPageStyle : "nowPageStyle")+"'>" + i + "</a></li>");
                }
            }
        }
        if (currentPage < totalPages) {
            re.append("<li><a href='" + getCompleteUrl(cleanUrl, currentPage + 1) + "' class='" + (StringUtils.isNotBlank(nextStyle) ? nextStyle : "downPage") + "' title='下一页'>" + (StringUtils.isNotBlank(nextHtml) ? nextHtml : "下一页") + "</a></li>");
            if (isLastPage) {
                re.append("<li><a href='" + (getCompleteUrl(cleanUrl, totalPages)) + "' class='pag2' title='最后一页'>" + (StringUtils.isNotBlank(lastPageHtml) ? lastPageHtml : ">>") + "</a></li>");
            }
        } else if(isNextRemind){
        		re.append("<li><a class='downPage' title='目前已是最后一页'>"+(StringUtils.isNotBlank(nextHtml) ? nextHtml : "") +"</a></li>");
        }

        boolean isSelect = isDisplaySelect;
        if (isDisplayGoToPage) {
            isSelect = false;
            re.append("<li class='rightpage'><form class='jumpPage' onsubmit='return false'><label>跳转到</label><input value='" + (currentPage == totalPages ? 1 : (currentPage+1) ) + "' type=number min=1 step=1 max="+totalPages+" />");
            String script = "javascript:var goPage=this.parentNode.parentNode.getElementsByTagName('input')[0].value;if(isNaN(goPage)||goPage>"
                    + totalPages + "||goPage<1||goPage==" + currentPage + ")return false;document.location='" + getCompleteUrlNoParam(cleanUrl)
                    + "page='+goPage;return false;";
            re.append("<input type='submit' value='GO' class='goBtn' onclick=\"" + script + "\"></input></form></li>");
        }

        if (isSelect) {
            //下拉的翻页可以不显示
            re.append("<li>&nbsp;&nbsp;到第&nbsp;<select name='select2' onchange=\"window.location.href='" +
                    getCompleteUrlNoParam(cleanUrl) +
                    "page='+this.options[this.selectedIndex].value + ''\">");
            for (int iCount = 1; iCount <= totalPages; iCount++) {
                String strSelected = "";
                if (iCount == currentPage) {
                    strSelected = "selected";
                }
                re.append("<option value='" + iCount + "' " + strSelected + ">-" + iCount + "-</option>");
            }
            re.append("</select>&nbsp;页</li>");
        }
        re.append("</ul></div>");
        return re.toString();
    }

    /**
     * Getter for property totalRecords.
     *
     * @return Value of property totalRecords.
     */
    public int getTotalRecords() {
        return this.totalRecords;
    }

    /**
     * Setter for property totalRecords.
     *
     * @param totalRecords New value of property totalRecords.
     */
    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int value) {
        totalPages = value;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int value) {
        currentPage = value;
    }

    public String getFrontPath() {
        return frontPath;
    }

    public int getDisplayNum() {
        return displayNum;
    }

    public boolean isIsDisplaySelect() {
        return isDisplaySelect;
    }

    public void setFrontPath(String value) {
        frontPath = value;
    }

    public void setDisplayNum(int displayNum) {
        this.displayNum = displayNum;
    }

    public void setIsDisplaySelect(boolean isDisplaySelect) {
        this.isDisplaySelect = isDisplaySelect;
    }

    public boolean isIsDisplayGoToPage() {
        return isDisplayGoToPage;
    }

    public void setIsDisplayGoToPage(boolean isDisplayGoToPage) {
        this.isDisplayGoToPage = isDisplayGoToPage;
    }

    public boolean getIsReWrite() {
        return isReWrite;
    }

    public void setIsReWrite(boolean reWrite) {
        isReWrite = reWrite;
    }

    public String getReWriteUrl() {
        return reWriteUrl;
    }

    public void setReWriteUrl(String reWriteUrl) {
        this.reWriteUrl = reWriteUrl;
    }
    public PageRowBounds getPage() {
		return page;
	}

	public void setPage(PageRowBounds page) {
		this.page = page;
		if( this.page != null ){
			this.setTotalRecords(this.page.getTotalRecord());
			this.setTotalPages(this.page.getTotalPage());
			this.setCurrentPage(this.page.getCurrentPage());
		}
	}

	public String getFilterParam() {
		return filterParam == null ? "" : filterParam;
	}

	public void setFilterParam(String filterParam) {
		this.filterParam = filterParam;
	}
	
	
}

