package com.wzq.tbmp.util;

import java.math.BigDecimal;

public class DataUtil {
	
	public static double handleDoubleValue2(double value) {
		BigDecimal bd = new BigDecimal(value);
		return bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
