package com.ejsell.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ejsell.store.entity.SellOut;
import com.opencsv.CSVReader;

public class readandwrite {

	@Test
	public void test1() {
		String field_model = "商品编号";
		String field_color = "颜色";
		String pre_model = "";// 上一个的商品编号，用来处理本次的商品编号为空的情况
		int model_row_num = 0;// 商品编号行位置
		int color_col_num = 0;// 颜色列位置
		int size_col_36 = 0;// 尺码36列位置
		int size_col_38 = 0;// 尺码38列位置
		int size_col_40 = 0;// 尺码40列位置
		int size_col_42 = 0;// 尺码42列位置
		int size_col_34 = 0;// 尺码34列位置
		int size_col_M = 0;// 尺码M列位置

		File inFile = new File("C:\\csvtest\\sell_return.csv");
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(inFile));
			CSVReader csvReader = new CSVReader(inputStreamReader, ',');
			List<String[]> listData = csvReader.readAll();
			int row_num = 0;
			int list_data_size = listData.size();
			// 初始化商品编号行位置、颜色列位置、尺码列位置
			for (int i = 0; i < list_data_size; i++) {
				String[] strs = listData.get(i);
				// 直到循环到商品编号列后，进行定位操作
				if (field_model.equals(strs[1])) {
					model_row_num = i;// 商品编号出现的行位置
					// 第一步，定位颜色列号
					for (int j = 0; j < strs.length; j++) {
						if (field_color.equals(strs[j])) {
							color_col_num = j;
							break;// 定位到颜色的列号后，退出j循环
						}
					}

					// 第二步，定位尺码列号
					String[] strs_size = listData.get(i + 1);
					for (int k = 0; k < strs_size.length; k++) {
						if ("34".equals(strs_size[k])) {
							size_col_34 = k;
						}
						if ("36".equals(strs_size[k])) {
							size_col_36 = k;
						}
						if ("38".equals(strs_size[k])) {
							size_col_38 = k;
						}
						if ("40".equals(strs_size[k])) {
							size_col_40 = k;
						}
						if ("42".equals(strs_size[k])) {
							size_col_42 = k;
						}
						if ("M".equals(strs_size[k])) {
							size_col_M = k;
						}
					}

					break;// 定位完尺码列序号后退出k循环
				}

				row_num++;

			}

			System.out.println("model_row_num(商品编号出现的位置):" + model_row_num + ",row_num(循环了几次):" + row_num);

			// 初始化销售出库列表
			List<SellOut> listSellOut = new ArrayList<SellOut>();
			// 从商品编号行+2开始读取，直到listData.size结束
			for (int i = model_row_num + 2; i < list_data_size; i++) {
				String[] data = listData.get(i);
				if (data[1] != null && !data[1].isEmpty()) {
					pre_model = data[1];// 赋值给上一个商品编号，如果商品编号为空，则可以使用此商品编号
				}

				// 初始化销售出库单
				// 如果尺码列34的编号有数字，则销售出库列表增加一条数据
				if (data[size_col_34] != null && !data[size_col_34].isEmpty()) {
					SellOut sellOut = new SellOut();
					sellOut.setModel(pre_model);
					sellOut.setColor(data[color_col_num]);
					sellOut.setSize("34");
					sellOut.setAmount(Integer.parseInt(data[size_col_34]));
					listSellOut.add(sellOut);
				}
				// 如果尺码列36的编号有数字，则销售出库列表增加一条数据
				if (data[size_col_36] != null && !data[size_col_36].isEmpty()) {
					SellOut sellOut = new SellOut();
					sellOut.setModel(pre_model);
					sellOut.setColor(data[color_col_num]);
					sellOut.setSize("36");
					sellOut.setAmount(Integer.parseInt(data[size_col_36]));
					listSellOut.add(sellOut);
				}
				// 如果尺码列38的编号有数字，则销售出库列表增加一条数据
				if (data[size_col_38] != null && !data[size_col_38].isEmpty()) {
					SellOut sellOut = new SellOut();
					sellOut.setModel(pre_model);
					sellOut.setColor(data[color_col_num]);
					sellOut.setSize("38");
					sellOut.setAmount(Integer.parseInt(data[size_col_38]));
					listSellOut.add(sellOut);
				}
				// 如果尺码列40的编号有数字，则销售出库列表增加一条数据
				if (data[size_col_40] != null && !data[size_col_40].isEmpty()) {
					SellOut sellOut = new SellOut();
					sellOut.setModel(pre_model);
					sellOut.setColor(data[color_col_num]);
					sellOut.setSize("40");
					sellOut.setAmount(Integer.parseInt(data[size_col_40]));
					listSellOut.add(sellOut);
				}
				// 如果尺码列42的编号有数字，则销售出库列表增加一条数据
				if (data[size_col_42] != null && !data[size_col_42].isEmpty()) {
					SellOut sellOut = new SellOut();
					sellOut.setModel(pre_model);
					sellOut.setColor(data[color_col_num]);
					sellOut.setSize("42");
					sellOut.setAmount(Integer.parseInt(data[size_col_42]));
					listSellOut.add(sellOut);
				}
				// 如果尺码列M的编号有数字，则销售出库列表增加一条数据
				if (data[size_col_M] != null && !data[size_col_M].isEmpty()) {
					SellOut sellOut = new SellOut();
					sellOut.setModel(pre_model);
					sellOut.setColor(data[color_col_num]);
					sellOut.setSize("M");
					sellOut.setAmount(Integer.parseInt(data[size_col_M]));
					listSellOut.add(sellOut);
				}

			}// for销售出库列表结束

			// 输出销售出库列表测试
			for (SellOut sellOut : listSellOut) {
				System.out.println("商品编号:" + sellOut.getModel() + ",颜色:" + sellOut.getColor() + ",尺码:" + sellOut.getSize() + ",数量:" + sellOut.getAmount());
			}

			csvReader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}