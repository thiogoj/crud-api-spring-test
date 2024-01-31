package joaquinthiogo.crud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import joaquinthiogo.crud.entity.Product;
import joaquinthiogo.crud.service.FileService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


class FileControllerTest {

    private FileService fileService = new FileService();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void uploadCSV() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String format = simpleDateFormat.format(new Date());

        System.out.println(format);
    }

    @Test
    void readCsv() {
        Path path = Path.of("csv-file/product2024-01-31_18-36-35.csv");

        try (Reader reader = Files.newBufferedReader(path)) {

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setHeader().build();

            CSVParser csvParser = new CSVParser(reader, csvFormat);
            for (CSVRecord record : csvParser) {
                System.out.println(record.get(0));
                System.out.println(record.get(1));
                System.out.println(record.get(2));
                System.out.println(record.get(3));
            }

        } catch (IOException e) {
            Assertions.fail(e);
        }
    }

    @Test
    void readAllFile() throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(fileService.files()));
    }

    @Test
    void csvToJson() throws JsonProcessingException {
        List<Product> products = fileService.csvToProduct("test.csv");
        System.out.println(objectMapper.writeValueAsString(products));
    }

    @Test
    void deleteFile() {
        File file = new File("csv-file/test123.csv");
        if (file.isFile()) {
            file.delete();
        }
    }
}