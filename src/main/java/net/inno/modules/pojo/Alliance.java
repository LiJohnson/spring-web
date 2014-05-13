package net.inno.modules.pojo;

import org.json.JSONException;
import org.json.JSONObject;
 
/**
 * 制造联信息收集
 * @date 2014-05-08 17:01:15
 */
public class Alliance extends BasePojo {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private long allianceId;
	
	/**
	 * 用户ID
	 */
	private String login_id;

	/**
	 * 所属行业
	 */
	private long industryId;

	/**
	 * 省市区ID
	 */
	private int areaId;

	/**
	 * 街道门牌号
	 */
	private String stree;

	/**
	 * 企业名称
	 */
	private String name;

	/**
	 * 官方网站
	 */
	private String website;

	/**
	 * 企业规模
	 */
	private String scale;

	/**
	 * 联系信息，JSON
	 */
	private JSONObject contact;

	/**
	 * 主营产品
	 */
	private String mainProduct;

	/**
	 * 成本优势,N-0,Y-1
	 */
	private int cost;

	/**
	 * 工艺优势,N-0,Y-1
	 */
	private int crafts;

	/**
	 * 质量控制,N-0,Y-1
	 */
	private int quality;

	/**
	 * 交期,N-0,Y-1
	 */
	private int delivery;

	/**
	 * 产量,N-0,Y-1
	 */
	private int yield;

	/**
	 * 对企业竞争力/优势的描述
	 */
	private String description;
 
	public Alliance(){
		//为了检索的时候可以用上int的默认值0
		this.cost = this.crafts = this.quality = this.delivery = this.yield = -1;
		this.contact = new JSONObject();
	}


	/**
	 * 主键
	 */
	public long getAllianceId(){
		return this.allianceId;
	}

	/**
	 * 主键
	 */
	public void setAllianceId(long allianceId){
		 this.allianceId = allianceId; 
	}

	/**
	 * 用户ID
	 */
	public String getLogin_id() {
		return login_id;
	}

	/**
	 * 用户ID
	 */
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}


	/**
	 * 所属行业
	 */
	public long getIndustryId(){
		return this.industryId;
	}

	/**
	 * 所属行业
	 */
	public void setIndustryId(long industryId){
		 this.industryId = industryId; 
	}


	/**
	 * 省市区ID
	 */
	public int getAreaId(){
		return this.areaId;
	}

	/**
	 * 省市区ID
	 */
	public void setAreaId(int areaId){
		 this.areaId = areaId; 
	}


	/**
	 * 街道门牌号
	 */
	public String getStree(){
		return this.stree;
	}

	/**
	 * 街道门牌号
	 */
	public void setStree(String stree){
		 this.stree = stree; 
	}


	/**
	 * 企业名称
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * 企业名称
	 */
	public void setName(String name){
		 this.name = name; 
	}


	/**
	 * 官方网站
	 */
	public String getWebsite(){
		return this.website;
	}

	/**
	 * 官方网站
	 */
	public void setWebsite(String website){
		 this.website = website; 
	}


	/**
	 * 企业规模
	 */
	public String getScale(){
		return this.scale;
	}

	/**
	 * 企业规模
	 */
	public void setScale(String scale){
		 this.scale = scale; 
	}

	//-----------------------------------------------------------------------------------------------------
	
	/**
	 * 联系信息，JSON
	 */
	protected void setContact(String info){
		try {
			this.contact = new JSONObject(info);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 联系信息，JSON
	 */
	protected String getContact(){		
		return this.contact.toString();
	}
	
	/**
	 * 以key => value 的形式设置联系信息，JSON
	 * @param key	
	 * @param value
	 */
	private void setContact( String key , String value){
		 try {
			this.contact.put(key, value);
		} catch (JSONException e) {}
	}
	
	/**
	 * 根据key值获取联系信息，JSON
	 * @param key	
	 * @param value
	 */
	private String getContact( String key ){
		try {
			return this.contact.getString(key);
		} catch (JSONException e) {
			return null;
		}
	}
	
	public String getName1(){
		return this.getContact(Alliance.JsonKey.NAME1);
	}
	public void setName1(String name1){
		this.setContact(Alliance.JsonKey.NAME1, name1);
	}
	
	public String getName2(){
		return this.getContact(Alliance.JsonKey.NAME2);
	}
	public void setName2(String name2){
		this.setContact(Alliance.JsonKey.NAME2, name2);
	}
	
	public String getMobilephone1(){
		return this.getContact(Alliance.JsonKey.MOBILEPHONE1);
	}
	public void setMobilephone1(String mobilephone1){
		this.setContact(Alliance.JsonKey.MOBILEPHONE1, mobilephone1);
	}
	
	public String getMobilephone2(){
		return this.getContact(Alliance.JsonKey.MOBILEPHONE2);
	}
	public void setMobilephone2(String mobilephone2){
		this.setContact(Alliance.JsonKey.MOBILEPHONE2, mobilephone2);
	}
	
	public String getOfficephone1(){
		return this.getContact(Alliance.JsonKey.OFFICEPHONE1);
	}
	public void setOfficephone1(String officephone1){
		this.setContact(Alliance.JsonKey.OFFICEPHONE1, officephone1);
	}
	
	public String getOfficephone2(){
		return this.getContact(Alliance.JsonKey.OFFICEPHONE2);
	}
	public void setOfficephone2(String officephone2){
		this.setContact(Alliance.JsonKey.OFFICEPHONE2, officephone2);
	}
	
	public String getEmail1(){
		return this.getContact(Alliance.JsonKey.EMAIL1);
	}
	public void setEmail1(String email1){
		this.setContact(Alliance.JsonKey.EMAIL1, email1);
	}
	
	public String getEmail2(){
		return this.getContact(Alliance.JsonKey.EMAIL2);
	}
	public void setEmail2(String email2){
		this.setContact(Alliance.JsonKey.EMAIL2, email2);
	}
	
	/**
	 * json中的key
	 */
	public static class JsonKey{
		/**
		 * 联系人1
		 */
		public static final String NAME1 = "name1";
		/**
		 * 联系人2
		 */
		public static final String NAME2 = "name2";
		/**
		 * 联系手机1
		 */
		public static final String MOBILEPHONE1 = "mobilephone1";
		/**
		 * 联系手机2
		 */
		public static final String MOBILEPHONE2 = "mobilephone2";
		/**
		 * 固话1
		 */
		public static final String OFFICEPHONE1 = "officephone1";
		/**
		 * 固话2
		 */
		public static final String OFFICEPHONE2 = "officephone2";
		/**
		 * 邮箱1
		 */
		public static final String EMAIL1 = "email1";
		/**
		 * 邮箱2
		 */
		public static final String EMAIL2 = "email2";
	}
	
	//-----------------------------------------------------------------------------------------------------


	/**
	 * 主营产品
	 */
	public String getMainProduct(){
		return this.mainProduct;
	}

	/**
	 * 主营产品
	 */
	public void setMainProduct(String mainProduct){
		 this.mainProduct = mainProduct; 
	}


	/**
	 * 成本优势,N-0,Y-1
	 */
	public int getCost(){
		return this.cost;
	}

	/**
	 * 成本优势,N-0,Y-1
	 */
	public void setCost(int cost){
		 this.cost = cost; 
	}


	/**
	 * 工艺优势,N-0,Y-1
	 */
	public int getCrafts(){
		return this.crafts;
	}

	/**
	 * 工艺优势,N-0,Y-1
	 */
	public void setCrafts(int crafts){
		 this.crafts = crafts; 
	}


	/**
	 * 质量控制,N-0,Y-1
	 */
	public int getQuality(){
		return this.quality;
	}

	/**
	 * 质量控制,N-0,Y-1
	 */
	public void setQuality(int quality){
		 this.quality = quality; 
	}


	/**
	 * 交期,N-0,Y-1
	 */
	public int getDelivery(){
		return this.delivery;
	}

	/**
	 * 交期,N-0,Y-1
	 */
	public void setDelivery(int delivery){
		 this.delivery = delivery; 
	}


	/**
	 * 产量,N-0,Y-1
	 */
	public int getYield(){
		return this.yield;
	}

	/**
	 * 产量,N-0,Y-1
	 */
	public void setYield(int yield){
		 this.yield = yield; 
	}


	/**
	 * 对企业竞争力/优势的描述
	 */
	public String getDescription(){
		return this.description;
	}

	/**
	 * 对企业竞争力/优势的描述
	 */
	public void setDescription(String description){
		 this.description = description; 
	}
	
	/**
	 * 真假值的默认值
	 */
	public static final class YN{
		/**
		 * 真Y
		 */
		public static final int Y = 1;
		/**
		 * 假N
		 */
		public static final int N = 0;
	}

 
}
