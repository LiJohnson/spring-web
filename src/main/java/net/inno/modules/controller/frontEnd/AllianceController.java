package net.inno.modules.controller.frontEnd;

import net.inno.modules.controller.BaseController;
import net.inno.modules.pojo.Alliance;
import net.inno.modules.services.IAllianceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/alliance")
public class AllianceController extends BaseController {
	private static final String PATH = FRONT_END_PATH + "alliance/";
	
	@Autowired
	private IAllianceService allianceService;
	
	/**
	 * 制造联企业
	 * @return
	 */
	@RequestMapping(value="/index")
	public String index(ModelMap map) {
		map.put("alliance", this.allianceService.getByLogin_id(super.getUserId()));
		return PATH + "index";
	}
	
	/**
	 * 添加或修改制造联企业
	 * @param alliance
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/handleAlliance")
	public void handleAlliance(Alliance alliance, ModelMap map) {
		alliance.setLogin_id(super.getUserId());
		map.put("count", this.allianceService.handleAlliance(alliance));
	}

}
