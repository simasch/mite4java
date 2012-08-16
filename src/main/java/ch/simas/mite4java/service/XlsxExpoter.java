package ch.simas.mite4java.service;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ch.simas.mite4java.data.ReportFilter;
import ch.simas.mite4java.data.TimeEntry;
import ch.simas.mite4java.data.TimeEntryData;
import ch.simas.mite4java.utils.Getconnection;

public class XlsxExpoter {

	public void exportTimeEntryData(String subDomain, String apiKey, String selectedFields,ReportFilter repFltr,String reportHeader, String reportFooter, OutputStream out) {

		String TARGET_HTTPS_SERVER = subDomain + ".mite.yo.lk";
		String TARGET_URL = "https://" + subDomain + ".mite.yo.lk/time_entries.xml?api_key=" + apiKey;

		Getconnection gcon = new Getconnection(TARGET_HTTPS_SERVER, "true");

		gcon.getCon();

		try {

			URL serverAddress = new URL(TARGET_URL);

			HttpURLConnection connection = (HttpURLConnection) serverAddress.openConnection();
			connection.connect();
			int rc = connection.getResponseCode();
			if (rc == 200) {
				BufferedReader br = new BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));

				JAXBContext jaxbContext = JAXBContext.newInstance(TimeEntryData.class);

				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				TimeEntryData timeEntryListData = (TimeEntryData) jaxbUnmarshaller.unmarshal(br);

				System.out.println(timeEntryListData.getTimeEntryList().get(0).getProjectName());
				
				
				ArrayList<TimeEntry> timeEntryList=new ArrayList<TimeEntry>();
				
				int allRowsCount = timeEntryListData.getTimeEntryList().size();

				for (int i = 0; i < allRowsCount; i++) {
					
					if(repFltr.getCustomerFl()!=null){
						
						if(repFltr.getCustomerSelected().equalsIgnoreCase(timeEntryListData.getTimeEntryList().get(i).getCustomerName()))
							timeEntryList.add(timeEntryListData.getTimeEntryList().get(i));
						
					}else if(repFltr.getServiceFl()!=null){
						
						if(repFltr.getServiceSelected().equalsIgnoreCase(timeEntryListData.getTimeEntryList().get(i).getServiceName()))
							timeEntryList.add(timeEntryListData.getTimeEntryList().get(i));
					
						
					}else if(repFltr.getProjectFl()!=null){
						
						if(repFltr.getProjectSelected().equalsIgnoreCase(timeEntryListData.getTimeEntryList().get(i).getProjectName()))
							timeEntryList.add(timeEntryListData.getTimeEntryList().get(i));
					
						
					}else {
						
						timeEntryList.add(timeEntryListData.getTimeEntryList().get(i));
					}
					
				}

				String delims = ";";
				String[] tokens = null;

				if (selectedFields==null)
					selectedFields = "1;2;3;4;5;6;";
				
				tokens = selectedFields.split(delims);

				for (int i = 0; i < tokens.length; i++)
					System.out.println(tokens[i]);

				Workbook wb = new XSSFWorkbook();

				Sheet sheet = wb.createSheet("Time Entry");
				
				Font fontBrown = wb.createFont();
				fontBrown.setColor(IndexedColors.BROWN.getIndex());
				
				Font fontBlueSmall = wb.createFont();
				fontBlueSmall.setColor(IndexedColors.BLUE.getIndex());
				
				Font fontHeader = wb.createFont();
				fontHeader.getBoldweight();
				fontHeader.setFontHeightInPoints((short)18);

				CellStyle csLabel = wb.createCellStyle();
				csLabel.setFont(fontBlueSmall);
				csLabel.setFillForegroundColor(IndexedColors.BLUE.getIndex());
				csLabel.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
				csLabel.setWrapText(false);
				csLabel.setBorderBottom(CellStyle.BORDER_THIN);
				csLabel.setBorderTop(CellStyle.BORDER_THIN);
				csLabel.setBorderLeft(CellStyle.BORDER_THIN);
				csLabel.setBorderRight(CellStyle.BORDER_THIN);
				
				csLabel.setBottomBorderColor(IndexedColors.BLACK.getIndex());
				csLabel.setTopBorderColor(IndexedColors.BLACK.getIndex());
				csLabel.setLeftBorderColor(IndexedColors.BLACK.getIndex());
				csLabel.setRightBorderColor(IndexedColors.BLACK.getIndex());
				
				CellStyle csHeader = wb.createCellStyle();
				csHeader.setFont(fontHeader);
				csHeader.setWrapText(false);
				csHeader.setFillBackgroundColor(IndexedColors.BROWN.getIndex());
				
			
				
				CellStyle csFooter= wb.createCellStyle();
				csFooter.setFont(fontBrown);
				csFooter.setWrapText(false);
				
				
					
				int currentRowNumber=0;
				Row rowHeader = sheet.createRow(currentRowNumber);
				currentRowNumber++;
				Cell cellHeader = rowHeader.createCell(0);
				cellHeader.setCellValue(reportHeader);
				cellHeader.setCellStyle(csHeader);

				currentRowNumber++;
				
				Row row0 = sheet.createRow(currentRowNumber);
				currentRowNumber++;
				int k = 0;
				String EntryTimeFields[] = { "", "Project", "Service", "Customer", "User", "Hours", "Update Date" };

				if (tokens.length > 1) {
					for (int i = 0; i < tokens.length; i++) {

						Cell cell0 = row0.createCell(k);

						cell0.setCellValue(EntryTimeFields[Integer.valueOf(tokens[i])]);
						cell0.setCellStyle(csLabel);
						k++;

					}

				} else {

					for (int i = 1; i < EntryTimeFields.length; i++) {

						Cell cell0 = row0.createCell(k);

						cell0.setCellValue(EntryTimeFields[i]);
						cell0.setCellStyle(csLabel);
						k++;

					}

				}

				int rowsCount = timeEntryList.size();

				for (int i = 1; i <= rowsCount; i++) {

					Row row = sheet.createRow(currentRowNumber);
					CellStyle cs = wb.createCellStyle();
					cs.setWrapText(false);
					cs.setBorderBottom(CellStyle.BORDER_THIN);
					cs.setBorderTop(CellStyle.BORDER_THIN);
					cs.setBorderLeft(CellStyle.BORDER_THIN);
					cs.setBorderRight(CellStyle.BORDER_THIN);
					
					cs.setBottomBorderColor(IndexedColors.BLACK.getIndex());
					cs.setTopBorderColor(IndexedColors.BLACK.getIndex());
					cs.setLeftBorderColor(IndexedColors.BLACK.getIndex());
					cs.setRightBorderColor(IndexedColors.BLACK.getIndex());
					currentRowNumber++;
					k = 0;

					if (tokens.length > 1) {

						for (int j = 0; j < tokens.length; j++) {
							
							
							if (Integer.valueOf(tokens[j].toString()) == 1) {
								Cell cell11 = row.createCell(k);
								cell11.setCellValue(timeEntryList.get(i - 1).getProjectName());
								cell11.setCellStyle(cs);
								k++;
							} else if (Integer.valueOf(tokens[j].toString()) == 2) {
								Cell cell12 = row.createCell(k);
								cell12.setCellValue(timeEntryList.get(i - 1).getServiceName());
								cell12.setCellStyle(cs);
								k++;
							} else if (Integer.valueOf(tokens[j].toString()) == 3) {
								Cell cell13 = row.createCell(k);
								cell13.setCellValue(timeEntryList.get(i - 1).getCustomerName());
								cell13.setCellStyle(cs);
								k++;
							} else if (Integer.valueOf(tokens[j].toString()) == 4) {
								Cell cell14 = row.createCell(k);
								cell14.setCellValue(timeEntryList.get(i - 1).getUserName());
								cell14.setCellStyle(cs);
								k++;
							} else if (Integer.valueOf(tokens[j].toString()) == 5) {
								Cell cell15 = row.createCell(k);
								double hours = timeEntryList.get(i - 1).getMinutes() / 60.00;
								cell15.setCellValue(hours);
								cell15.setCellStyle(cs);
								k++;
							} else if (Integer.valueOf(tokens[j].toString()) == 6) {
								Cell cell16 = row.createCell(k);
								cell16.setCellValue(timeEntryList.get(i - 1).getUpdatedAt());
								cell16.setCellStyle(cs);
								k++;
							}

						}

					} else {

						Cell cell11 = row.createCell(0);
						cell11.setCellValue(timeEntryList.get(i - 1).getProjectName());
						cell11.setCellStyle(cs);

						Cell cell12 = row.createCell(1);
						cell12.setCellValue(timeEntryList.get(i - 1).getServiceName());
						cell12.setCellStyle(cs);

						Cell cell13 = row.createCell(2);
						cell13.setCellValue(timeEntryList.get(i - 1).getCustomerName());
						cell13.setCellStyle(cs);

						Cell cell14 = row.createCell(3);
						cell14.setCellValue(timeEntryList.get(i - 1).getUserName());
						cell14.setCellStyle(cs);

						Cell cell15 = row.createCell(4);
						double hours = timeEntryList.get(i - 1).getMinutes() / 60.00;
						cell15.setCellValue(hours);
						cell15.setCellStyle(cs);

						Cell cell16 = row.createCell(5);
						cell16.setCellValue(timeEntryList.get(i - 1).getUpdatedAt());
						cell16.setCellStyle(cs);
					}
				}
				
				currentRowNumber++;
				Row rowFooter= sheet.createRow(currentRowNumber);
				Cell cellFooter = rowFooter.createCell(0);
				cellFooter.setCellValue(reportFooter);
				cellFooter.setCellStyle(csFooter);
				
				Sheet sheet1 = wb.getSheetAt(0);
			    sheet1.autoSizeColumn(1); //adjust width of the second column
			    sheet1.autoSizeColumn(2); //adjust width of the first column
			    sheet1.autoSizeColumn(3); //adjust width of the second column
			    sheet1.autoSizeColumn(4); //adjust width of the first column
			    sheet1.autoSizeColumn(5); //adjust width of the second column
			    sheet1.autoSizeColumn(6); //adjust width of the first column
			    sheet1.autoSizeColumn(7); //adjust width of the second column
			 

				wb.write(out);

			} else {
				System.out.println("HTTP error:" + rc);
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
