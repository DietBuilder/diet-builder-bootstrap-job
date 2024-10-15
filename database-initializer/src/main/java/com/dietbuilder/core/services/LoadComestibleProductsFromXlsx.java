package com.dietbuilder.core.services;

import com.dietbuilder.boot.config.InitializationProperties;
import com.dietbuilder.domain.model.comestible_product.ComestibleProduct;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

@Service
@Slf4j
public class LoadComestibleProductsFromXlsx {

    @Autowired
    private InitializationProperties initializationProperties;

    public List<ComestibleProduct> loadFromXlsxFile() {
        ArrayList<ComestibleProduct> comestibleProductList = new ArrayList<>();

        try (FileInputStream fis =
//            new FileInputStream(getFilePath())) {
            new FileInputStream(getFileAsStream())) {
            XSSFSheet sheet = new XSSFWorkbook(fis)
                    .getSheetAt(0);

            // removing header
            XSSFRow rowToRemove = sheet.getRow(0);
            sheet.removeRow(rowToRemove);

            // iterating through all xlsx rows
            Iterator<Row> rowIterator = sheet.rowIterator();
            log.info("Loading comestible products from xlsx file" + initializationProperties.getFilePath());
            final Consumer<Row> lambdaExpressionForRowIteration = row -> {
                ComestibleProduct product = loadComestibleProductFromXSSFRow((XSSFRow) row);
                comestibleProductList.add(product);
                log.debug("Product " + product.getProductName() + " added.");
            };

            rowIterator.forEachRemaining(lambdaExpressionForRowIteration);
        } catch (IOException e) {
            log.error("Loading ComestibleProducts from xlsx file has failed", e);
        }


        return comestibleProductList;
    }

    /**
     * @param row XSSFRow containing all required data
     * @return ComestibleProduct
     * Method pull data from row and save it a ComestibleProduct entity
     */
    private ComestibleProduct loadComestibleProductFromXSSFRow(XSSFRow row) {

        return ComestibleProduct.builder()
                .productName(row.getCell(0).getStringCellValue())
                .litter(row.getCell(1).getNumericCellValue())
                .foodEnergy((int) row.getCell(2).getNumericCellValue())
                .water(row.getCell(3).getNumericCellValue())
                .animalProtein(row.getCell(4).getNumericCellValue())
                .vegetableProtein(row.getCell(5).getNumericCellValue())
                .fat(row.getCell(6).getNumericCellValue())
                .ash(row.getCell(8).getNumericCellValue())
                .sodium(row.getCell(9).getNumericCellValue())
                .potassium(row.getCell(10).getNumericCellValue())
                .calcium(row.getCell(11).getNumericCellValue())
                .phosphorus(row.getCell(12).getNumericCellValue())
                .magnesium(row.getCell(13).getNumericCellValue())
                .iron(row.getCell(14).getNumericCellValue())
                .zinc(row.getCell(15).getNumericCellValue())
                .copper(row.getCell(16).getNumericCellValue())
                .manganese(row.getCell(17).getNumericCellValue())
                .retinol(row.getCell(19).getNumericCellValue())
                .betaCarotene(row.getCell(20).getNumericCellValue())
                .vitaminD(row.getCell(21).getNumericCellValue())
                .vitaminE(row.getCell(22).getNumericCellValue())
                .thiamin(row.getCell(23).getNumericCellValue())
                .riboflavin(row.getCell(24).getNumericCellValue())
                .niacin(row.getCell(25).getNumericCellValue())
                .vitaminB6(row.getCell(26).getNumericCellValue())
                .folates(row.getCell(27).getNumericCellValue())
                .vitaminB12(row.getCell(28).getNumericCellValue())
                .vitaminC(row.getCell(29).getNumericCellValue())
                .saturatedFattyAcids(row.getCell(41).getNumericCellValue())
                .monounsaturatedFattyAcids(row.getCell(49).getNumericCellValue())
                .polyunsaturatedFattyAcids(row.getCell(58).getNumericCellValue())
                .cholesterol(row.getCell(59).getNumericCellValue())
                .isoleucine(row.getCell(60).getNumericCellValue())
                .leucin(row.getCell(61).getNumericCellValue())
                .lysine(row.getCell(62).getNumericCellValue())
                .methionine(row.getCell(63).getNumericCellValue())
                .cystine(row.getCell(64).getNumericCellValue())
                .phenylalanine(row.getCell(65).getNumericCellValue())
                .tyrosine(row.getCell(66).getNumericCellValue())
                .threonine(row.getCell(67).getNumericCellValue())
                .tryptophan(row.getCell(68).getNumericCellValue())
                .valine(row.getCell(69).getNumericCellValue())
                .arginine(row.getCell(70).getNumericCellValue())
                .histidine(row.getCell(71).getNumericCellValue())
                .alanine(row.getCell(72).getNumericCellValue())
                .asparticAcid(row.getCell(73).getNumericCellValue())
                .glutamicAcid(row.getCell(74).getNumericCellValue())
                .glycine(row.getCell(75).getNumericCellValue())
                .proline(row.getCell(76).getNumericCellValue())
                .serine(row.getCell(77).getNumericCellValue())
                .sucrose(row.getCell(78).getNumericCellValue())
                .lactose(row.getCell(79).getNumericCellValue())
                .starch(row.getCell(80).getNumericCellValue())
                .dietaryFibre(row.getCell(81).getNumericCellValue())
                .percentOfEnergyFromProtein(row.getCell(82).getNumericCellValue())
                .percentOfEnergyFromFat(row.getCell(83).getNumericCellValue())
                .percentOfEnergyFromCarbohydrates(row.getCell(84).getNumericCellValue()).build();
    }

    private String getFilePath() {
        URL resource = LoadComestibleProductsFromXlsx.class.getClassLoader().getResource(initializationProperties.getFilePath());

        if (resource == null) {
            throw new RuntimeException(String.format("Failed to load file [%s]",
                initializationProperties.getFilePath()));
        }

        return resource.getFile();
    }

    private File getFileAsStream() {
//        return LoadComestibleProductsFromXlsx.class.getClassLoader().getResourceAsStream(initializationProperties.getFilePath()).
        PathMatchingResourcePatternResolver res =  new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = res.getResources("classpath*:/" + initializationProperties.getFilePath());
            return resources[0].getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        if (resource == null) {
//            throw new RuntimeException(String.format("Failed to load file [%s]",
//                initializationProperties.getFilePath()));
//        }
//
//        return resource.getFile();
    }
}
