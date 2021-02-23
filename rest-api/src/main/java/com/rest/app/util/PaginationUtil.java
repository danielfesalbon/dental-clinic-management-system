/**
 * 
 */
package com.rest.app.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * @author danielf
 *
 */
@Service
public class PaginationUtil {

	public PaginateValues getPageValues(Integer row, Integer count) {
		try {
			List<Integer> options = new ArrayList<Integer>();
			PaginateValues values = new PaginateValues();
			values.setCount(count);
			values.setRow(row);
			if (values.getCount() >= 20) {
				options = new ArrayList<Integer>();
				options.add(10);
				options.add(15);
				options.add(20);
			} else if (values.getCount() >= 15 && values.getCount() < 20) {
				options = new ArrayList<Integer>();
				options.add(10);
				options.add(15);
			} else if (values.getCount() >= 10 && values.getCount() < 15) {
				options = new ArrayList<Integer>();
				options.add(10);
			} else {
				options = new ArrayList<Integer>();
				options.add((int) values.getCount());
			}
			values.setRowoptions(options);
			return values;

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return new PaginateValues();
	}

}
