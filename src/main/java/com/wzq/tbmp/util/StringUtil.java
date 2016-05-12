package com.wzq.tbmp.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import com.wzq.tbmp.pojo.Menu;
import com.google.common.io.BaseEncoding;

public class StringUtil {
	
	public static boolean isEmpty(String string) {
		return string == null || string.trim().length() == 0||string.equalsIgnoreCase("null");
	}

	public static boolean isEmpty(Integer in) {
		return in == null || in == 0;
	}

	public static boolean isEmpty(Double in) {
		return in == null || in == 0;
	}

	public static boolean isEmpty(Long in) {
		return in == null || in == 0;
	}

	public static String getRandCode(int bits) {
		String code = "";
		String strings = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		Random random = new Random();
		for (int i = 1; i <= bits; i++) {
			int randValue = random.nextInt(strings.length());
			code += strings.substring(randValue, randValue + 1);
		}
		return code;
	}
	
	/**
	 * 生成密码
	 * 
	 * @return[0] encryptedPassword,[1]assistantPassword
	 */
	public static String[] generatePassword(String originalPassword) {
		try {
			String code = getRandCode(5);
			String assistantPassword = BaseEncoding.base64().encode(code.getBytes("UTF-8"));
			String encryptedPassword = MD5Util.hashToMD5(originalPassword + code);
			String[] passwords = {encryptedPassword, assistantPassword };
			return passwords;
		} catch (Exception e) {
			return new String[2];
		}
	}
	
	public static void main(String[] args) throws Exception {
		String token = "username=xx@appcup.com&op=1";
		System.out.println(BaseEncoding.base64().encode(token.getBytes("UTF-8")));
	}
	
	/**
	 * 验证密码
	 * 
	 * @param originalPassword
	 *            :明文密码
	 * @param encryptedPassword
	 *            ：数据库加密密码
	 * @param assistantPassword
	 *            ：数据库帮助密码
	 * @return
	 */
	public static boolean checkPassword(String originalPassword, String encryptedPassword, String assistantPassword) {
		try {
			assistantPassword = new String(BaseEncoding.base64().decode(assistantPassword), "utf-8");
			String password = MD5Util.hashToMD5(originalPassword + assistantPassword);
			if (!password.equals(encryptedPassword)) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	public static String hanyuToPinyin(String name) {
		try {
			String pinyinName = "";
			char[] nameChar = name.toCharArray();
			HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
			defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			for (int i = 0; i < nameChar.length; i++) {
				if (nameChar[i] > 128) {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0];
				} else {
					pinyinName += String.valueOf(nameChar[i]).toLowerCase();
				}
			}
			return pinyinName;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static LinkedHashMap<Menu, List<Menu>> turnListToMap(List<Menu> menuList) {
		LinkedHashMap<Menu, List<Menu>> map = new LinkedHashMap<Menu, List<Menu>>();
		for (Menu menu : menuList) {
			if (menu.getLevel() == 0) {
				List<Menu> list = new ArrayList<Menu>();
				map.put(menu, list);
			}
		}
		for (Menu menu : menuList) {
			if (menu.getLevel() == 1 && map.get(menu.getParent()) != null) {
				map.get(menu.getParent()).add(menu);
			}
		}
		return map;
	}
	
}
