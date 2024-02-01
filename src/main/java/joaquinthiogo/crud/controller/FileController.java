package joaquinthiogo.crud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import joaquinthiogo.crud.entity.Product;
import joaquinthiogo.crud.service.FileService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class FileController {

    @Autowired
    private ObjectMapper objectMapper;

    private FileService fileService = new FileService();

    @PostMapping(path = "/upload/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<String> uploadCSV(@RequestPart(name = "csv")MultipartFile file) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String format = simpleDateFormat.format(new Date());

        String fileName = FilenameUtils.removeExtension(file.getOriginalFilename()) + format + "." + FilenameUtils.getExtension(file.getOriginalFilename());

        Path path = Paths.get("csv-file/" + fileName);
        file.transferTo(path);

        return ResponseEntity.ok(objectMapper.writeValueAsString(Map.of(
                "message", "Success upload " + fileName
        )));
    }

    @GetMapping(path = "/api/csv", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> readCSV() throws JsonProcessingException {
        return ResponseEntity.ok(objectMapper.writeValueAsString(fileService.files()));
    }

    @GetMapping(path = "/api/csv/{filename}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> csvToProductJson(@PathVariable(name = "filename") String filename) throws JsonProcessingException {
        List<Product> products = fileService.csvToProduct(filename);

        return products != null ? ResponseEntity.ok(objectMapper.writeValueAsString(products)) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectMapper.writeValueAsString(Map.of(
                "error", "File CSV not found"
        )));
    }

    @DeleteMapping(path = "/api/csv/{filename}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> deleteFile(@PathVariable(name = "filename") String filename) throws JsonProcessingException {
        File file = new File("csv-file/" + filename);
        if (file.isFile()) {
            file.delete();
            return ResponseEntity.ok(objectMapper.writeValueAsString(Map.of(
                    "message", filename + " successs deleted"
            )));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(objectMapper.writeValueAsString(Map.of(
                    "message", filename + " fail to delete"
            )));
        }
    }

}
