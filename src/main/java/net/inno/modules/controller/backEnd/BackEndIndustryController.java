package net.inno.modules.controller.backEnd;

import net.inno.modules.controller.BaseController;
import net.inno.modules.pojo.Industry;
import net.inno.modules.services.IIndustryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/backEnd/industry")
public class BackEndIndustryController extends BaseController {
	private static final String PATH = BACK_END_PATH + "industry/";
	
	@Autowired
	private IIndustryService industryService;
	
	/**
	 * 制造联行业页面
	 * @return
	 */
	@RequestMapping(value="index")
	public String index() {
		return PATH + "index";
	}
	
	/**
	 * 获取制造联行业
	 * @param map
	 */
	@RequestMapping(value="list")
	public void list(ModelMap map) {
		map.put("list", this.industryService.loadIndustry(null));
	}
	
	/**
	 * 添加或者修改制造联行业
	 * 1、主键 <= 0 为添加
	 * 2、parentId <= 0 为一级分类
	 * @param industry
	 * @return
	 */
    @RequestMapping(value="/handleIndustry")
    public void handleIndustry(Industry industry, ModelMap map) {
    	map.put("count", this.industryService.handleIndustry(industry));
    }
    
    /**
	 * 删除制造联行业
	 * @param industryId
	 * @return
	 */
    @RequestMapping(value="/deleteIndustry")
    public void deleteIndustry(long industryId, ModelMap map) {
    	map.put("count", this.industryService.deleteIndustry(industryId));
    }
	
}
