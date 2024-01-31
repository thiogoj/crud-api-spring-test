package joaquinthiogo.crud.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import joaquinthiogo.crud.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileService {

    public List<Product> csvToProduct(String csvFilename) {
        Path path = Path.of("csv-file/" + csvFilename);

        try (Reader reader = Files.newBufferedReader(path)) {

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader().build();

            CSVParser csvParser = new CSVParser(reader, csvFormat);
            List<Product> productList = new ArrayList<>();
            csvParser.forEach(value -> {
                Product product = new Product();
                product.setId(Integer.parseInt(value.get(0)));
                product.setName(value.get(1));
                product.setDescription(value.get(2));
                product.setPrice(Long.parseLong(value.get(3)));

                productList.add(product);
            });
            return productList;

        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public List<FileCSV> files() {
        URI uri = Paths.get("csv-file").toUri();
        File csvFile = new File(uri);

        List<FileCSV> fileCSVS = new ArrayList<>();

        File[] listFiles = csvFile.listFiles();

        if (csvFile != null) {
            for (File file : listFiles) {
                if (file.getName().endsWith(".csv")) {
                    FileCSV fileCSV = new FileCSV(file.getName());

                    fileCSVS.add(fileCSV);
                }
            }
        }

        return fileCSVS;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FileCSV {

        private String name;

    }

}
