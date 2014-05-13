package net.inno.modules.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-7-11
 * Time: 上午10:05
 * To change this template use File | Settings | File Templates.
 */
public class ListCondition implements Serializable {
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private List<String> listSql;
    private boolean distinct;
    private String orderSql;
    private String groupSql;

    public String getGroupSql() {
		return groupSql;
	}

	public void setGroupSql(String groupSql) {
		this.groupSql = groupSql;
	}

	public ListCondition(){
        listSql = new ArrayList<String>();
    }

    public List<String> getListSql() {
        return listSql;
    }

    public void setListSql(List<String> listSql) {
        this.listSql = listSql;
    }


    public boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public String getOrderSql() {
        return orderSql;
    }

    public void setOrderSql(String orderSql) {
        this.orderSql = orderSql;
    }

    public String getQueryKey(){
        StringBuilder keys = new StringBuilder();
        for(String condition : listSql){
            if(keys.length()>0){
                keys.append("_");
            }
            keys.append(condition);
        }
        return keys.toString();
    }
}
