package spring.hello_test.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileUploadController {

    @Value("${spring.hello_test.upload_folder}")
    private String pathURI;
    private Path rootPath;

    @PostConstruct
    public void init(){
        rootPath = Paths.get(pathURI);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public @ResponseBody String provideUploadInfo(){
        return "You can use this URL for uploading";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("prefix") String prefix,
                                                 @RequestParam("file")MultipartFile file){
        if(!file.isEmpty()){
            try{
                InputStream is = file.getInputStream();
                Files.copy(is, rootPath.resolve(file.getOriginalFilename()));
                is.close();
                return "File successfully uploaded";
            }
            catch (Exception e){
                return "File upload is failed with: "+e.getMessage();
            }
        }
        else {
            return "File upload is failed. File is empty";
        }
    }

    @RequestMapping(value="/cleanUploadFolder")
    public @ResponseBody String cleanUploadFolder(){
        try {
            FileSystemUtils.deleteRecursively(rootPath);

            return "Folder clean-up successfully performed";
        }
        catch(Exception e){
            return "Folder clean-up failed: "+e.getMessage();
        }
    }

    @RequestMapping(value="/getFileList")
    public @ResponseBody String getFileList(){
        try {
            StringBuilder sb = new StringBuilder();
            Files.walk(rootPath, 1).forEach(file ->{if(file.toFile().isFile()) sb.append(file.getFileName()).append("</br>");});
            return sb.toString();
        }
        catch(Exception e){
            return "getFileList failed: "+e.getMessage();
        }
    }

    @RequestMapping(value="/loadFile"
            /*in case of FileSystemResource opens in browser page without this*/
            /*, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE*/)
    public @ResponseBody /*FileSystemResource*/HttpEntity loadFile(@RequestParam("fileName") String fileName){
        try{
            //works but name of the opening file - not defined
            //return new FileSystemResource(pathURI+fileName);

            byte[] documentBody = Files.readAllBytes(Paths.get(pathURI+fileName));
            HttpHeaders header = new HttpHeaders();
            header.set(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=" + fileName.replace(" ", "_"));
            header.setContentLength(documentBody.length);

            return new HttpEntity<byte[]>(documentBody, header);
        }
        catch(Exception e){
            return null;
        }
    }
}
