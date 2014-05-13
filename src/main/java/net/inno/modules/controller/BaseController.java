package net.inno.modules.controller;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;


/**
 * @author lcs
 */
@Controller
public class BaseController {
	protected static final String ERROR_PATH = "error";
	protected static final String FRONT_END_PATH = "frontEnd/";
	protected static final String BACK_END_PATH = "backEnd/";
	protected static final String REDIRECT = "redirect:";
	
    @Autowired(required=false)
    protected HttpServletRequest request;
    @Autowired(required=false)
    protected HttpSession session;    

    public void setSuccess(ModelMap model) {
        model.put("success", true);
    }

    public void setFailure(ModelMap model) {
        model.put("success", false);
    }

    @InitBinder
    public void InitBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Date.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                    	if(StringUtils.isBlank(value)){
                    		setValue(null);
                    	}else{
                    		try {
                                setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                        .parse(value));
                            } catch (Exception e) {
                                try {
                                    setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm")
                                            .parse(value));
                                } catch (ParseException e1) {
                                	try{
                                		setValue(new SimpleDateFormat("yyyy-MM-dd")
                                				.parse(value));
                                	}catch (ParseException e2) {
                                		try{
                                			setAsText(value);
                                		}catch(Exception e3){
                                			setValue(null);
                                		}
    									
    								}
                                }
                            }
                    	}
                    }

                    public String getAsText() {
                        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .format((Date) getValue());
                    }

                });

        dataBinder.registerCustomEditor(Long.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                        try {
                            setValue(Long.parseLong(value));
                        } catch (Exception e) {
                            setValue(-1L);
                        }
                    }

                    public String getAsText() {
                        return (String) getValue();
                    }
                });
        
        dataBinder.registerCustomEditor(long.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                        try {
                            setValue(Long.parseLong(value));
                        } catch (Exception e) {
                            setValue(0L);
                        }
                    }

                    public String getAsText() {
                        return (String) getValue();
                    }
                });

        dataBinder.registerCustomEditor(Integer.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                        try {
                            setValue(new Double(value).intValue());
                        } catch (Exception e) {
                            setValue(null);
                        }
                    }

                    public String getAsText() {
                        return (String) getValue();
                    }
                });
        
        dataBinder.registerCustomEditor(int.class,
                new PropertyEditorSupport() {
                    public void setAsText(String value) {
                        try {
                            setValue(new Double(value).intValue());
                        } catch (Exception e) {
                            setValue(0);
                        }
                    }

                    public String getAsText() {
                        return (String) getValue();
                    }
                });
    }

    /**
     * 异步访问错误的统一的出口
     *
     * @param messages
     * @return
     */
    public void createJSONError(ModelMap map, String... messages) {
        map.put("hasError", true);
        for (int i = 0, len = messages.length; i < len; i++) {
            map.put("error_" + i, messages[i]);
        }
    }
    
    protected String getUserId() {
		return (String) this.session.getAttribute("login");
	}
    
    protected String currentUrl() {
    	return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI();
	}
}