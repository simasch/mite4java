package ch.simas.mite4java.service;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
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

import ch.simas.mite4java.data.CustomerData;
import ch.simas.mite4java.data.ProjectData;
import ch.simas.mite4java.data.ReportFilter;
import ch.simas.mite4java.data.ServiceData;
import ch.simas.mite4java.data.TimeEntry;
import ch.simas.mite4java.data.TimeEntryData;
import ch.simas.mite4java.utils.Getconnection;

public class XlsxExpoter {

	Workbook wb = new XSSFWorkbook();
	
	Double grandTotal=0.0;
	int revenuePos = 0;


	public void exportTimeEntryData(String subDomain, String apiKey, String selectedFields, ReportFilter repFltr, String reportHeader, String reportFooter, OutputStream out) {

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

				ArrayList<TimeEntry> timeEntryList = new ArrayList<TimeEntry>();

				int allRowsCount = timeEntryListData.getTimeEntryList().size();

				for (int i = 0; i < allRowsCount; i++) {

					if (repFltr.getCustomerFl() != null) {

						if (repFltr.getCustomerSelected().equalsIgnoreCase(timeEntryListData.getTimeEntryList().get(i).getCustomerName()))
							timeEntryList.add(timeEntryListData.getTimeEntryList().get(i));

					} else if (repFltr.getServiceFl() != null) {

						if (repFltr.getServiceSelected().equalsIgnoreCase(timeEntryListData.getTimeEntryList().get(i).getServiceName()))
							timeEntryList.add(timeEntryListData.getTimeEntryList().get(i));

					} else if (repFltr.getProjectFl() != null) {

						if (repFltr.getProjectSelected().equalsIgnoreCase(timeEntryListData.getTimeEntryList().get(i).getProjectName()))
							timeEntryList.add(timeEntryListData.getTimeEntryList().get(i));

					} else {

						timeEntryList.add(timeEntryListData.getTimeEntryList().get(i));
					}

				}

				String delims = ";";
				String[] tokens = null;

				if (selectedFields == null)
					selectedFields = "1;2;3;4;5;6;";

				tokens = selectedFields.split(delims);

				int currentRowNumber = 0;

				Font fontHeader = wb.createFont();
				fontHeader.getBoldweight();
				fontHeader.setFontHeightInPoints((short) 18);

				Font fontBrown = wb.createFont();
				fontBrown.setColor(IndexedColors.BROWN.getIndex());

				CellStyle csHeader = wb.createCellStyle();
				csHeader.setFont(fontHeader);
				csHeader.setWrapText(false);
				csHeader.setFillBackgroundColor(IndexedColors.BROWN.getIndex());

				CellStyle csFooter = wb.createCellStyle();
				csFooter.setFont(fontBrown);
				csFooter.setWrapText(false);

				Sheet sheet = wb.createSheet("Time Entry");

				Row rowHeader = sheet.createRow(currentRowNumber);
				currentRowNumber++;
				Cell cellHeader = rowHeader.createCell(0);
				cellHeader.setCellValue(reportHeader);
				cellHeader.setCellStyle(csHeader);
				
				String blockHeader="";
				String blockFooter="";
				
				MiteSystem miteSystem = new MiteSystem();
				
				int filteredRowCount = timeEntryList.size();


				if (repFltr.getCustomerGr() != null){
					
					CustomerData customerListData = miteSystem.getMiteCustomerList(subDomain, apiKey);
					
					
					for (int i = 0; i < customerListData.getCustomerList().size(); i++) {

					String customer=customerListData.getCustomerList().get(i).getName();
					
					ArrayList<TimeEntry> groupTimeEntryList = new ArrayList<TimeEntry>();
					
					for (int j = 0; j < filteredRowCount; j++) {

					   if (timeEntryList.get(j).getCustomerName().equalsIgnoreCase(customer))
						   groupTimeEntryList.add(timeEntryList.get(j));
						
						}
					

					blockHeader="Customer : "+customer;
					if(groupTimeEntryList.size()>0)
					  currentRowNumber = addBlockDataInSheet(sheet, currentRowNumber, tokens, groupTimeEntryList, blockHeader,blockFooter);					
						
					}
						
				}else if (repFltr.getServiceGr() != null){
					
					ServiceData serviceListData = miteSystem.getMiteServiceList(subDomain, apiKey);
					
					for (int i = 0; i < serviceListData.getServiceList().size(); i++) {

					String service=serviceListData.getServiceList().get(i).getName();
					
					ArrayList<TimeEntry> groupTimeEntryList = new ArrayList<TimeEntry>();
					
					for (int j = 0; j < filteredRowCount; j++) {

					   if (timeEntryList.get(j).getServiceName().equalsIgnoreCase(service))
						   groupTimeEntryList.add(timeEntryList.get(j));
						
						}
					

					blockHeader="Service : "+service;
					if(groupTimeEntryList.size()>0)
					  currentRowNumber = addBlockDataInSheet(sheet, currentRowNumber, tokens, groupTimeEntryList, blockHeader,blockFooter);
					
					}
					
				}else if (repFltr.getProjectGr() != null){

					ProjectData projectListData = miteSystem.getMiteProjectList(subDomain, apiKey);
					
					for (int i = 0; i < projectListData.getProjectList().size(); i++) {

						String project=projectListData.getProjectList().get(i).getName();
						
						ArrayList<TimeEntry> groupTimeEntryList = new ArrayList<TimeEntry>();
						
						for (int j = 0; j < filteredRowCount; j++) {

						   if (timeEntryList.get(j).getProjectName().equalsIgnoreCase(project))
							   groupTimeEntryList.add(timeEntryList.get(j));
							
							}
						

					blockHeader="Project : "+project;
					if(groupTimeEntryList.size()>0)	
				       currentRowNumber = addBlockDataInSheet(sheet, currentRowNumber, tokens, groupTimeEntryList, blockHeader,blockFooter);
				
					}
					
				}else{
				
				if(timeEntryList.size()>0)
				   currentRowNumber = addBlockDataInSheet(sheet, currentRowNumber, tokens, timeEntryList, blockHeader,blockFooter);
				
				}
				
				Row rowSubTotal = sheet.createRow(currentRowNumber);
				Cell cell18 = rowSubTotal.createCell(revenuePos - 1);
				cell18.setCellValue("Total Revenue (€)");
				cell18.setCellStyle(csFooter);
				
			
				Cell cell19 = rowSubTotal.createCell(revenuePos);
				cell19.setCellValue(grandTotal);
			
				currentRowNumber++;
				Row rowFooter = sheet.createRow(currentRowNumber);
				Cell cellFooter = rowFooter.createCell(0);
				cellFooter.setCellValue(reportFooter);
				cellFooter.setCellStyle(csFooter);

				Sheet sheet1 = wb.getSheetAt(0);
				sheet1.autoSizeColumn(1); // adjust width of the second column
				sheet1.autoSizeColumn(2); // adjust width of the first column
				sheet1.autoSizeColumn(3); // adjust width of the second column
				sheet1.autoSizeColumn(4); // adjust width of the first column
				sheet1.autoSizeColumn(5); // adjust width of the second column
				sheet1.autoSizeColumn(6); // adjust width of the first column
				sheet1.autoSizeColumn(7); // adjust width of the second column

				wb.write(out);

			} else {
				System.out.println("HTTP error:" + rc);
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int addBlockDataInSheet(Sheet sheet, int currentRowNumber, String[] tokens, ArrayList<TimeEntry> timeEntryList, String blockHeader, String blockFooter) {

		Font fontBlueSmall = wb.createFont();
		fontBlueSmall.setColor(IndexedColors.BLUE.getIndex());

		Font fontBlockFooter = wb.createFont();
		fontBlockFooter.getBoldweight();
		fontBlockFooter.setFontHeightInPoints((short) 14);

		CellStyle csBlockHeader = wb.createCellStyle();
		csBlockHeader.setFont(fontBlueSmall);
		csBlockHeader.setWrapText(false);
	
		CellStyle csBlockFooter = wb.createCellStyle();
		csBlockFooter.setFont(fontBlockFooter);
		csBlockFooter.setWrapText(false);
		
		currentRowNumber++;
		Row rowHeader = sheet.createRow(currentRowNumber);
		Cell cellHeader = rowHeader.createCell(0);
		cellHeader.setCellValue(blockHeader);
		cellHeader.setCellStyle(csBlockHeader);
		
		
		for (int i = 0; i < tokens.length; i++)
			System.out.println(tokens[i]);

		
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


		DecimalFormat twoDForm = new DecimalFormat("#.##");

		currentRowNumber++;

		Row row0 = sheet.createRow(currentRowNumber);
		currentRowNumber++;
		int k = 0;
		String EntryTimeFields[] = { "Project", "Service", "Customer", "User", "Hours", "Revenue(€)", "Update Date" };

		if (tokens.length > 1) {
			for (int i = 0; i < tokens.length; i++) {

				Cell cell0 = row0.createCell(k);

				cell0.setCellValue(EntryTimeFields[Integer.valueOf(tokens[i]) - 1]);
				cell0.setCellStyle(csLabel);
				k++;

			}

		} else {

			for (int i = 0; i < EntryTimeFields.length; i++) {

				Cell cell0 = row0.createCell(k);

				cell0.setCellValue(EntryTimeFields[i]);
				cell0.setCellStyle(csLabel);
				k++;

			}

		}

		int rowsCount = timeEntryList.size();

		Double totalRevenue = 0.0;
		
		for (int i = 1; i <= rowsCount; i++) {

			Row row = sheet.createRow(currentRowNumber);

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
						int hours = integerPart(timeEntryList.get(i - 1).getMinutes() / 60.00);
						int minutes = timeEntryList.get(i - 1).getMinutes() - hours * 60;
						cell15.setCellValue(hours + ":" + minutes);
						cell15.setCellStyle(cs);
						k++;
					} else if (Integer.valueOf(tokens[j].toString()) == 6) {
						Cell cell16 = row.createCell(k);
						cell16.setCellValue(Double.valueOf(twoDForm.format(timeEntryList.get(i - 1).getRevenue() / 100)));
						cell16.setCellStyle(cs);
						totalRevenue += Double.valueOf(twoDForm.format(timeEntryList.get(i - 1).getRevenue()));
						revenuePos = k;
						k++;
					} else if (Integer.valueOf(tokens[j].toString()) == 7) {
						Cell cell17 = row.createCell(k);
						cell17.setCellValue(timeEntryList.get(i - 1).getUpdatedAt());
						cell17.setCellStyle(cs);
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
				int hours = integerPart(timeEntryList.get(i - 1).getMinutes() / 60.00);
				int minutes = timeEntryList.get(i - 1).getMinutes() - hours * 60;
				cell15.setCellValue(hours + ":" + minutes);
				cell15.setCellStyle(cs);

				Cell cell16 = row.createCell(5);
				cell16.setCellValue(Double.valueOf(twoDForm.format(timeEntryList.get(i - 1).getRevenue() / 100)));
				cell16.setCellStyle(cs);
				totalRevenue += Double.valueOf(twoDForm.format(timeEntryList.get(i - 1).getRevenue()));
				revenuePos = 5;

				Cell cell17 = row.createCell(6);
				cell17.setCellValue(timeEntryList.get(i - 1).getUpdatedAt());
				cell17.setCellStyle(cs);
			}
		}

		Row rowSubTotal = sheet.createRow(currentRowNumber);
		Cell cell18 = rowSubTotal.createCell(revenuePos - 1);
		cell18.setCellValue("Revenue (€)");
		
	
		Cell cell19 = rowSubTotal.createCell(revenuePos);
		cell19.setCellValue(Double.valueOf(twoDForm.format(totalRevenue / 100)));
		grandTotal+=Double.valueOf(twoDForm.format(totalRevenue / 100));
		
		currentRowNumber++;
		
		Row rowBlockFooter = sheet.createRow(currentRowNumber);
		Cell cellblockFooter = rowBlockFooter.createCell(0);
		cellblockFooter.setCellValue(blockFooter);
		cellblockFooter.setCellStyle(csBlockFooter);
		
		currentRowNumber++;

		return currentRowNumber;
	}

	public Integer integerPart(double d) {
		return (d <= 0) ? (int) Math.ceil(d) : (int) Math.floor(d);
	}
}
