package net.inno.modules.controller.frontEnd;

import net.inno.modules.controller.BaseController;
import net.inno.modules.services.IIndustryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndustryController extends BaseController {
	@Autowired
	private IIndustryService industryService;
	
	/**
	 * 获取制造联行业列表
	 * @param map
	 */
	@RequestMapping(value="/industry.json")
	public void industry(Long industryId, ModelMap map) {
		map.put("list", this.industryService.loadIndustry(industryId));
	}
	
}
