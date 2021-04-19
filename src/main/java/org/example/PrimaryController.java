package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PrimaryController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField linkField;

    @FXML
    private Button okBtn;

    private static Workbook book;

    static ArrayList<Ad> ads = new ArrayList<>();

    public static Document getPage(String URL) throws IOException {
        return Jsoup.connect(URL).get();
    }

    @FXML
    void parseLink(ActionEvent event) {
        Document page;
        if(!linkField.getText().isEmpty()) {
            try {
                page = getPage(linkField.getText());
                Elements items = page.getElementsByAttributeValue("class", "iva-item-body-NPl6W");
                for(Element item : items){
                    Ad ad = createAd(item);
                    if(!ads.contains(ad)){
                        ads.add(ad);
                    }
                }
                System.out.println(ads.size());
                createTable();
            } catch (IOException e) {
                System.out.println("Ошибка");
            }
        }
        else{
            System.out.println("Поле не заполнено");
        }
    }

    public static Ad createAd(Element item){
        String href = item.getElementsByClass("iva-item-titleStep-2bjuh").get(0).getElementsByAttribute("href").first().attr("href");
        String name = item.getElementsByClass("iva-item-titleStep-2bjuh").get(0).getElementsByAttribute("href").first().attr("title");
        String cost = item.getElementsByClass("iva-item-priceStep-2qRpg").select("meta").get(1).attr("content");
        String date = item.getElementsByClass("iva-item-dateStep-pZ3hT").select("span").first().select("div").get(0).text();
        return new Ad(name, cost, date, href);
    }

    public static void createTable() throws IOException {
        book = new HSSFWorkbook();//создание книги
        Sheet sheet = book.createSheet();//создание листа

        Row row = sheet.createRow(0); //первая строка
        row.createCell(0).setCellValue("Name");
        row.createCell(1).setCellValue("Cost"); //заполнение второй ячейки
        row.createCell(2).setCellValue("Date");
        row.createCell(3).setCellValue("Href");

        for(int i = 0; i < ads.size(); i++){
            Row row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(ads.get(i).getName());
            row1.createCell(1).setCellValue(ads.get(i).getCost());
            row1.createCell(2).setCellValue(ads.get(i).getDate());
            row1.createCell(3).setCellValue(ads.get(i).getHref());
        }

        book.write(new FileOutputStream(new File("/Users/admin/Desktop/book.xls")));
        book.close();
    }
}
