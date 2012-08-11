package ch.simas.mite4java.service;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

import ch.simas.mite4java.data.TimeEntryData;
import ch.simas.mite4java.utils.Getconnection;

public class XlsxExpoter {

	public void exportTimeEntryData(String subDomain,String apiKey,String selectedFields,OutputStream out)
	{
		

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

				String delims = ";";
				String[] tokens = selectedFields.split(delims);

				for (int i = 0; i < tokens.length; i++)
					System.out.println(tokens[i]);

				Workbook wb = new XSSFWorkbook();

				Sheet sheet = wb.createSheet("Fonts");

				Font font0 = wb.createFont();
				font0.setColor(IndexedColors.BROWN.getIndex());

				CellStyle csl = wb.createCellStyle();
				csl.setWrapText(true);
				csl.setFont(font0);
				csl.setWrapText(true);

				Row row0 = sheet.createRow(0);
				int k = 0;
				String EntryTimeFields[] = { "", "Project", "Service", "Customer", "User", "Hours", "Update Date" };

				if (tokens.length > 1) {
					for (int i = 0; i < tokens.length; i++) {

						Cell cell0 = row0.createCell(k);

						cell0.setCellValue(EntryTimeFields[Integer.valueOf(tokens[i])]);
						cell0.setCellStyle(csl);
						k++;

					}

				} else {

					for (int i = 1; i < EntryTimeFields.length; i++) {

						Cell cell0 = row0.createCell(k);

						cell0.setCellValue(EntryTimeFields[i]);
						cell0.setCellStyle(csl);
						k++;

					}

				}

				int rowsCount = timeEntryListData.getTimeEntryList().size();

				
				for (int i = 1; i <= rowsCount; i++) {

					CellStyle cs = wb.createCellStyle();
					cs.setWrapText(true);

					Row row = sheet.createRow(i);
					k = 0;
					
					if (tokens.length > 1) {

						for (int j = 0; j < tokens.length; j++) {

							if (Integer.valueOf(tokens[j].toString()) == 1) {
								Cell cell11 = row.createCell(k);
								cell11.setCellValue(timeEntryListData.getTimeEntryList().get(i - 1).getProjectName());
								cell11.setCellStyle(cs);
								k++;
							} else if (Integer.valueOf(tokens[j].toString()) == 2) {
								Cell cell12 = row.createCell(k);
								cell12.setCellValue(timeEntryListData.getTimeEntryList().get(i - 1).getServiceName());
								cell12.setCellStyle(cs);
								k++;
							} else if (Integer.valueOf(tokens[j].toString()) == 3) {
								Cell cell13 = row.createCell(k);
								cell13.setCellValue(timeEntryListData.getTimeEntryList().get(i - 1).getCustomerName());
								cell13.setCellStyle(cs);
								k++;
							} else if (Integer.valueOf(tokens[j].toString()) == 4) {
								Cell cell14 = row.createCell(k);
								cell14.setCellValue(timeEntryListData.getTimeEntryList().get(i - 1).getUserName());
								cell14.setCellStyle(cs);
								k++;
							} else if (Integer.valueOf(tokens[j].toString()) == 5) {
								Cell cell15 = row.createCell(k);
								double hours = timeEntryListData.getTimeEntryList().get(i - 1).getMinutes() / 60.00;
								cell15.setCellValue(hours);
								cell15.setCellStyle(cs);
								k++;
							} else if (Integer.valueOf(tokens[j].toString()) == 6) {
								Cell cell16 = row.createCell(k);
								cell16.setCellValue(timeEntryListData.getTimeEntryList().get(i - 1).getUpdatedAt());
								cell16.setCellStyle(cs);
								k++;
							}

						}

					} else {

						Cell cell11 = row.createCell(0);
						cell11.setCellValue(timeEntryListData.getTimeEntryList().get(i - 1).getProjectName());
						cell11.setCellStyle(cs);

						Cell cell12 = row.createCell(1);
						cell12.setCellValue(timeEntryListData.getTimeEntryList().get(i - 1).getServiceName());
						cell12.setCellStyle(cs);

						Cell cell13 = row.createCell(2);
						cell13.setCellValue(timeEntryListData.getTimeEntryList().get(i - 1).getCustomerName());
						cell13.setCellStyle(cs);

						Cell cell14 = row.createCell(3);
						cell14.setCellValue(timeEntryListData.getTimeEntryList().get(i - 1).getUserName());
						cell14.setCellStyle(cs);

						Cell cell15 = row.createCell(4);
						double hours = timeEntryListData.getTimeEntryList().get(i - 1).getMinutes() / 60.00;
						cell15.setCellValue(hours);
						cell15.setCellStyle(cs);

						Cell cell16 = row.createCell(5);
						cell16.setCellValue(timeEntryListData.getTimeEntryList().get(i - 1).getUpdatedAt());
						cell16.setCellStyle(cs);
					}
				}

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
