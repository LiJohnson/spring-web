package net.inno.modules.controller.backEnd;

import net.inno.modules.controller.BaseController;
import net.inno.modules.pojo.Alliance;
import net.inno.modules.services.IAllianceService;
import net.inno.myBatisPlugin.page.model.PageRowBounds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/backEnd/alliance")
public class BackEndAllianceController extends BaseController {
	private static final String PATH = BACK_END_PATH + "alliance/";
	
	@Autowired
	private IAllianceService allianceService;
	
	/**
	 * 制造联企业
	 * @param alliance
	 * @param map
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/index")
	public String index(Alliance alliance, ModelMap map, PageRowBounds page) {
		map.put("list", this.allianceService.getList(alliance, page));
		return PATH + "index";
	}
	
	/**
	 * 添加或修改制造联企业
	 * @param alliance
	 * @param map
	 * @param page
	 * @return
	 */
	@RequestMapping(value="/deleteAlliance")
	public void deleteAlliance(long allianceId, ModelMap map) {
		map.put("count", this.allianceService.deleteAlliance(allianceId));
	}
}
