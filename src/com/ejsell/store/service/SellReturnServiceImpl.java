package com.ejsell.store.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ejsell.store.entity.SellReturn;
import com.persistent.impl.Hibernate3CRUDImpl;

@Service
public class SellReturnServiceImpl extends Hibernate3CRUDImpl<SellReturn> implements SellReturnService {

	
	@Override
	public String genExportSql(List<String> listSizeName,int isExists) {
		StringBuffer sb = new StringBuffer(1000);
		sb.append("select ");
		for (String sizeName : listSizeName) {
			sb.append("SUM(SIZE_" + sizeName + ") AS SIZE_" + sizeName + ",");
		}

		sb.append("MODEL,COLOR from ( ");
		sb.append("select ");
		for (String sizeName : listSizeName) {
			sb.append("CASE SIZE WHEN '" + sizeName + "' THEN AMOUNT ELSE 0 END AS SIZE_" + sizeName + ",");
		}
		sb.append("MODEL,COLOR FROM ( ");

		sb.append("select MODEL,COLOR,SIZE,SUM(AMOUNT) AS AMOUNT from ( ");
		sb.append("select * from sell_return ser ");
		
		if(isExists==1){
			sb.append("where exists ( ");
		}else{
			sb.append("where not exists ( ");
		}
		
		sb.append("select 1 from sell_out seo ");
		sb.append("where ser.MODEL=seo.MODEL ");
		sb.append("and ser.COLOR=seo.COLOR ");
		sb.append("and ser.SIZE=seo.SIZE) ) ser_main ");
		sb.append("group by MODEL,COLOR,SIZE ");
		sb.append(") ser_main1 ");
		sb.append(") ser_main2 ");
		sb.append("group by MODEL,COLOR ");
		sb.append("order by MODEL,COLOR ");

		return sb.toString();
	}

}
