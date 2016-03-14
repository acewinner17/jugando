package ibm.com;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.impl.JsonWriteContext;
//import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jackson.map.ObjectMapper;
import model.FileMeta;
import model.FilesMeta;
import model.UploadesFiles;
import utils.MultipartRequestHandler;
 
//this to be used with Java Servlet 3.0 API
@MultipartConfig
public class FileUploadServlet extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
 
    // this will store uploaded files
    private static List<FileMeta> filesM = new LinkedList<FileMeta>();
    private static FilesMeta files = new FilesMeta();
    private static UploadesFiles filesF = new UploadesFiles();
    /***************************************************
     * URL: /upload
     * doPost(): upload the files and other parameters
     ****************************************************/
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
 
    	doAlgoF(request, response);
 
    }
    
    protected void doAlgoF(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
 
        // 1. Upload File Using Java Servlet API
        //files.addAll(MultipartRequestHandler.uploadByJavaServletAPI(request));            
 
        // 1. Upload File Using Apache FileUpload
        filesF = MultipartRequestHandler.uploadByOtherFileUpload(request);
        // Remove some files
        while(filesF.getFiles().size() > 20)
        {
            filesF.getFiles().remove(0);
        }
 
        // 2. Set response type to json
        response.setContentType("application/json");
 
        // 3. Convert List<FileMeta> into JSON format
        ObjectMapper mapper = new ObjectMapper();
 
        // 4. Send resutl to client
        //mapper.createObjectNode();
        System.out.println(filesF.toString());
        mapper.writeValue(response.getOutputStream(), filesF);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(filesF));
 
    }
    
    protected void doAlgo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
 
        // 1. Upload File Using Java Servlet API
        //files.addAll(MultipartRequestHandler.uploadByJavaServletAPI(request));            
 
        // 1. Upload File Using Apache FileUpload
        files = MultipartRequestHandler.uploadByApacheFileUploads(request);
        // Remove some files
        while(files.getFiles().size() > 20)
        {
            files.getFiles().remove(0);
        }
 
        // 2. Set response type to json
        response.setContentType("application/json");
 
        // 3. Convert List<FileMeta> into JSON format
        ObjectMapper mapper = new ObjectMapper();
 
        // 4. Send resutl to client
        //mapper.createObjectNode();
        System.out.println(files.toString());
        mapper.writeValue(response.getOutputStream(), files);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(files));
 
    }
    
    protected void doAlgoM(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
 
        // 1. Upload File Using Java Servlet API
        //files.addAll(MultipartRequestHandler.uploadByJavaServletAPI(request));            
 
        // 1. Upload File Using Apache FileUpload
        filesM.addAll(MultipartRequestHandler.uploadByApacheFileUpload(request));
 
        // Remove some files
        while(filesM.size() > 20)
        {
            filesM.remove(0);
        }
 
        // 2. Set response type to json
        response.setContentType("application/json");
 
        // 3. Convert List<FileMeta> into JSON format
        ObjectMapper mapper = new ObjectMapper();
 
        // 4. Send resutl to client
        mapper.createObjectNode();
        mapper.writeValue(response.getOutputStream(), filesM);
        System.out.println(mapper.writeValueAsString(filesM.get(0)));
 
    }
    /***************************************************
     * URL: /upload?f=value
     * doGet(): get file of index "f" from List<FileMeta> as an attachment
     ****************************************************/
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
 
         // 1. Get f from URL upload?f="?"
         String value = request.getParameter("f");
         if(value==null||("").equals(value)) return;
         // 2. Get the file of index "f" from the list "files"
         FileMeta getFile = filesM.get(Integer.parseInt(value));
 
         try {        
                 // 3. Set the response content type = file content type 
                 response.setContentType(getFile.getFileType());
 
                 // 4. Set header Content-disposition
                 response.setHeader("Content-disposition", "attachment; filename=\""+getFile.getFileName()+"\"");
 
                 // 5. Copy file inputstream to response outputstream
                InputStream input = getFile.getContent();
                OutputStream output = response.getOutputStream();
                byte[] buffer = new byte[1024*10];
 
                for (int length = 0; (length = input.read(buffer)) > 0;) {
                    output.write(buffer, 0, length);
                }
 
                output.close();
                input.close();
         }catch (IOException e) {
               System.out.println("error: "+e.getMessage());
         }
 
    }
}