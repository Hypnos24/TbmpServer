package com.wzq.tbmp.util;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtil {
	
//	public static String exportPlayer(List<PlayerVo> playerList) throws Exception{
//		String fileName = UUID.randomUUID().toString()+ ".xls";
//		HSSFWorkbook wb = new HSSFWorkbook();
//		HSSFCellStyle style = wb.createCellStyle();
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 居中设置
//		HSSFSheet sheet = wb.createSheet("玩家列表"); 
//		String[] title = new String[]{"区服名称","玩家编号","玩家昵称","爵位","创建时间","最后在线","玩家等级","经验值",
//				"可用钻石","充值钻石","掠夺排名","竞技场排名","金币","声誉币","海盗币","筹码","建造队列","金币容量","副本",
//				"精英副本","占星馆","无尽之海","登陆次数","签到次数","商业仓明细","军事仓明细","水手明细"};
//		short[] width = new short[]{5000,4000,5000,4000,5000,5000,2000,4000,4000,2000,2000,2000,5000,3000,
//				3000,3000,2000,5000,3000,3000,3000,3000,3000,3000,20000,20000,20000};
//		insertTitle(sheet, style, title, width);
//		int currentRow = 0;
//		for (PlayerVo player : playerList) {
//			currentRow++;
//			HSSFRow row = sheet.createRow(currentRow);
//			insertData(row, player.getAreaName(),player.getGpId(), player.getName(),player.getNobilityDesc(),player.getCreatedAtDesc(),player.getUpdatedAtDesc(),
//					String.valueOf(player.getLevel()),String.valueOf(player.getExp()),String.valueOf(player.getMoney()),String.valueOf(player.getMoneyTotal()),
//					String.valueOf(player.getPillageRank()),String.valueOf(player.getArenaRank()),String.valueOf(player.getCoin()),
//					String.valueOf(player.getFameCoin()),String.valueOf(player.getPirateCoin()),String.valueOf(player.getSlotScore()),String.valueOf(player.getBuilderNum()),
//					String.valueOf(player.getCoinCapacity()),String.valueOf(player.getStoryId()),String.valueOf(player.getEliteChapterId()),String.valueOf(player.getStarId()),
//					String.valueOf(player.getEndlessId()),String.valueOf(player.getTotalLoginCount()),String.valueOf(player.getTotalSignInCount()),player.getBusinessCarbinStr4Export(),player.getBattleCarbinStr4Export(),player.getSailorStr4Export());
//		}
//		String filePath = Constant.TOMCAT_SERVICE_ADDRESS + "/" + fileName;
//		writeToDisk(wb, filePath);
//		return filePath;
//	}
	
	private static void insertTitle(HSSFSheet sheet, HSSFCellStyle style, String[] title, short[] width) {
		HSSFRow headRow = sheet.createRow(0);
		for (int i = 0; i < title.length; i++) {
			HSSFCell cell = headRow.createCell((short)i, HSSFCell.CELL_TYPE_STRING);
			HSSFRichTextString ts = new HSSFRichTextString(title[i]);
			cell.setCellValue(ts);
			cell.setCellStyle(style);
			sheet.setColumnWidth((short)i, width[i]);
		}
	}
	
	private static void insertData(HSSFRow row, Object... data) {
		for (int i = 0; i < data.length; i++) {
			HSSFCell cell = row.createCell((short)i, HSSFCell.CELL_TYPE_STRING);
			HSSFRichTextString ts = new HSSFRichTextString((String)data[i]);
			cell.setCellValue(ts);
		}
	}
	
	private static void insertTitle(HSSFSheet sheet, HSSFCellStyle style, String[] title) {
		HSSFRow headRow = sheet.createRow(0);
		for (int i = 0; i < title.length; i++) {
			HSSFCell cell = headRow.createCell((short)i, HSSFCell.CELL_TYPE_STRING);
			HSSFRichTextString ts = new HSSFRichTextString(title[i]);
			cell.setCellValue(ts);
			cell.setCellStyle(style);
			sheet.setColumnWidth((short)i, (short)(title[i].length() * 1000));
		}
	}
	
	private static void writeToDisk(HSSFWorkbook wb, String filePath) throws Exception{
		File file = new File(filePath);
		FileOutputStream fos = new FileOutputStream(file);
		wb.write(fos);
		fos.close();
	}
	
	public static String getCellValue(HSSFCell cell) {
		if (cell == null) {
			return "";
		}
		String value = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			value = cell.getRichStringCellValue().getString();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			double numTxt = cell.getNumericCellValue();
			value = String.valueOf((int)numTxt);
			break;
		}
		return value.trim();
	}
}
