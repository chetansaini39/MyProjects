/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.thoughtworks.xstream.XStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *Main class to start thread  for two sockets
 * @author Chetan
 */
public class MyWebServer
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
      {
        // TODO code application logic here
        
        Thread webServer= new Thread(new WebServer());
        webServer.start();
        Thread bccServer= new Thread(new BCC_Server());
        bccServer.start();
        
        
      }

}

/**
 * Class for running the Webserver
 * @author Chetan
 */
class WebServer implements Runnable
{

    @Override
    public void run()
      {
        int port = 2540;
        try
            {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Waiting for web server client connection..."+port);
            while (true)
                {
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(new ClientConnected(socket));
                thread.start();
                }

            } catch (IOException ex)
            {
            Logger.getLogger(MyWebServer.class.getName()).log(Level.SEVERE, null, ex);
            }

      }

}

/**
 * Class for BackChannel work
 * @author Chetan
 */
class BCC_Server implements Runnable
{

    @Override
    public void run()
      {
        int port = 2570;
        try
            {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Waiting for BCC client connection..."+port);
            while (true)
                {
                Socket socket = serverSocket.accept();
                Thread thread = new Thread(new BCC_Connection(socket));
                thread.start();
                }

            } catch (IOException ex)
            {
            Logger.getLogger(MyWebServer.class.getName()).log(Level.SEVERE, null, ex);
            }

      }
    
}
/**
 *File Server
 * @author Chetan
 */
class ClientConnected implements Runnable
{
    
    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter out;
    String clientRequest;
    String suffix_txt = ".txt";
    String suffix_TXT = ".TXT";
    String suffix_html = ".html";
    String suffix_java = ".java";
    String suffix4_favIcon = ".ico";
    MyHelper myHelper = new MyHelper();
    
    public ClientConnected(Socket socket)
      {
        this.socket = socket;
      }
    
    @Override
    public void run()
      {
        System.out.println("Connected to client : " + socket);
        try
            {
            myHelper = new MyHelper();
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
            System.out.println("Reading data from the client ");
            String clientData = bufferedReader.readLine();
            myHelper.removeGetAndHttp(clientData);
            clientRequest = myHelper.getRequestedContent();
            
            if (clientRequest.equalsIgnoreCase("mimer-data.xyz"))
                {
                processMimer_XYZ();
                }
            else if (clientData.equalsIgnoreCase(StaticVariables.indexPage))
                {
                displayIndexPage();
                }
            else if (clientRequest.equalsIgnoreCase(StaticVariables.directoryPage))
                {
                System.out.println("Displaying directories");
                displayDirectory("");
                }
            else if (clientRequest.contains(StaticVariables.receiveNumber))
                {
                System.out.println("Add number page");
                addReceivedNumbers(clientRequest);
                }
            else if (clientRequest.contains("addNum"))
                {
                FileData fileData = new FileData();
                System.out.println("Client Requested TXT/HTML/JAVA file");
                fileData.findFile("addnum.html", out, socket);
                }
            else if (clientRequest.contains("."))
                {
                System.out.println("Client is requesting a file");
                if (clientRequest.contains(suffix_txt) || clientRequest.contains(suffix_TXT) || clientRequest.contains(suffix_html) || clientRequest.contains(suffix_java))
                    {
                    FileData fileData = new FileData();
                    System.out.println("CLient Requested TXT/HTML/JAVA file");
                    fileData.findFile(myHelper.getRequestedContent(), out, socket);
                    }
                else
                    {
                    System.out.println("System cannot process the file");
                    }
                }
            else
                {
                System.out.println("client is requesting the directory");
                displayDirectory(clientRequest);
                }
            
            } catch (Exception e)
            {
            System.out.println("Exception: " + e);
            
            } finally
            {
            CloseAll();
            }
      }

    /**
     * Method to close stream and socket
     */
    private void CloseAll()
      {
        if (!socket.isClosed())
            {
            try
                {
                bufferedReader.close();
                out.close();
                socket.close();
                System.out.println("Socket/buffreader/output closed...");
                System.out.println("\n Waiting for client Connection...");
                } catch (IOException ex)
                {
                Logger.getLogger(ClientConnected.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
      }

    /**
     * Method for displaying the index page
     */
    private void displayIndexPage()
      {
        
        out.println("HTTP/1.0 200");
        out.println("Content-type: text/html");
        out.println("Server-name: myserver");
        String response = "<html>\n"
                + "<head>\n"
                + "<title></title>\n"
                + "<h2><strong><span style=\"color:#FF8C00;\">Welcome to Chetan&#39;s Webserver.</span></strong></h2>\n"
                + "<hr />\n"
                + "<p><span style=\"color:#ff00ee;\">This server displays directories/files and can help you add two numbers</span></p>\n"
                 + "<p><h3><span style=\"color:#ff00ee;\">The server also process the MIME type XYZ</span></h3></p>\n"
                + "\n"
                + "<p>&nbsp;</p>\n"
                + "<a href=\"showDirectories\"> Show me directories/files</a>\n"
                + "<p><a href=\"addNum\"> Add Two Numbers For Me</a></p>\n"
               + "<p><h2><a href=\"mimer-data.xyz\"> Mimer-data.xyz</a></h2></p>\n"
                + "\n"
                + "</head>\n"
                + "<body>\n"
                + "\n"
                + "</body>\n"
                + "</html>​";
        out.println("Content-length: " + response.length());
        out.println("");
        out.println(response);
        out.println("\r\n\r\n");
        out.flush();
        out.close();
        out.close();
        System.out.println("Index Page Called");
        
      }

    /**
     * Handling the request for MIMER.XYZ
     */
    private void processMimer_XYZ()
      {
        System.out.println("Handling : " + clientRequest);
        FileData fileData = new FileData();
        try
            {
            fileData.SendXYZ(clientRequest, out, socket);
            } catch (IOException ex)
            {
            Logger.getLogger(ClientConnected.class.getName()).log(Level.SEVERE, null, ex);
            }
        
      }

    /**
     * Method to check the contents of the directory
     */
    private void displayDirectory(String directory)
      {
        StringBuilder sb = new StringBuilder();
        FileData fileData = new FileData();
        fileData.checkContentsOfFolder(StaticVariables.serveFilesFromRootDirectory + directory);
        for (String s : fileData.getArrayList())
            {
            //String temp = "<br><a href= " + s + ">" + s + "</a></br>";
            sb.append("<br><a href= " + s + ">" + s + "</a></br>");
            sb.append("\n");
            }
        out.println("HTTP/1.0 200");
        out.println("Content-type: text/html");
        out.println("Server-name: myserver");
        String response = "<html><head>"
                + "<title>Chetan's Web Server</title></head>\n"
                + "<h1>Directories/Files : This Server Can display *.html/*.txt/*.java files in browser window</h1>\n"
                + sb.toString() + "\n"
                + "</html>\n";
        out.println("Content-length: " + response.length());
        out.println("");
        out.println(response);
        out.println("\r\n\r\n");
        out.flush();
        out.close();
        out.close();
        
      }

    /**
     * Method to add the numbers received from the addNum page
     *
     * @param txt
     */
    private void addReceivedNumbers(String txt)
      {
        String firstNo = "&firstNo=";
        String secondNo = "&secondNo=";
        String searchName = "?person=";
        // String name =
        String operatorAnd = "&";
        int indexOfName = txt.indexOf(searchName);
        int indexOf_FirstNo = txt.indexOf(firstNo);
        int indexOf_SecondNo = txt.indexOf(secondNo);
        String nameOfPerson = txt.substring(indexOfName + searchName.length(), indexOf_FirstNo);
        String firstSub = txt.substring(indexOf_FirstNo + firstNo.length(), indexOf_SecondNo);
        System.out.println("Index Of First No= " + indexOf_FirstNo);
        System.out.println("Index of Second no=" + indexOf_SecondNo);
        System.out.println("First Number = " + firstSub);
        float n1 = Integer.parseInt(firstSub);
        String secondSub = txt.substring(indexOf_SecondNo + secondNo.length(), txt.length());
        float n2 = Integer.parseInt(secondSub);
        float sum = n1 + n2;
        System.out.println("Addition of Two numbers : " + sum);
        out.println("HTTP/1.0 200");
        out.println("Content-type: text/html");
        out.println("Server-name: myserver");
        String response = "<html><head>"
                + "<title>Addition of two numbers</title></head>\n"
                + "<h1> Dear " + nameOfPerson + ", the Sum of " + firstSub + " and " + secondSub + "=" + String.valueOf(sum) + "</h1>\n"
                + "<p>This page is hosted by Chetan's web server.\n</p>"
                + "</html>\n";
        out.println("Content-length: " + response.length());
        out.println("");
        out.println(response);
        out.println("\r\n\r\n");
        out.flush();
        out.close();
        out.close();
        
      }
    
}

/**
 *Back channel communication class
 * @author Chetan
 */
 class BCC_Connection implements Runnable
{

    Socket socket;
    BufferedReader bufferedReader;
    private int i;
    PrintStream out = null;

    public BCC_Connection(Socket socket)
      {
        this.socket = socket;
      }

     String[] xmlLines = new String[15];
    String[] testLines = new String[10];
    String xml;
    String temp;
    XStream xstream = new XStream();
    final String newLine = System.getProperty("line.separator");
    myDataArray da = new myDataArray();
    
    @Override
    public void run()
      {
        System.out.println("Connected to client : " + socket);
         System.out.println("Called BC worker.");
        try
            {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintStream(socket.getOutputStream()); // to send ack back to client
           i = 0; xml = "";
	while(true){
	  temp = bufferedReader.readLine();
	  if (temp.indexOf("end_of_xml") > -1) break;
	  else xml = xml + temp + newLine; // Should use StringBuilder in 1.5
	}
	System.out.println("The XML marshaled data:");
	System.out.println(xml);
	out.println("Acknowledging Back Channel Data Receipt"); // send the ack
	out.flush(); 	
        da = (myDataArray) xstream.fromXML(xml); // deserialize / unmarshal data
	System.out.println("Here is the restored data: ");
	for(i = 0; i < da.num_lines; i++){
	  System.out.println(da.lines[i]);
	}
            } catch (Exception e)
            {
            System.out.println("Exception : " + e);
            } finally
            {
            try
                {
                if (!socket.isClosed())
                    {
                    bufferedReader.close();//closing the socket
                    socket.close();
                    System.out.println("Socket Closed");
                    System.out.println("Waiting for connection ...");
                    }
                } catch (IOException ex)
                {
                Logger.getLogger(BCC_Connection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
      }

}

class myDataArray
{

    int num_lines = 0;
    String[] lines = new String[10];
}



/**
 * Checks the contents of the folders and if outputs the txt/java files to
 * screen.
 *
 * @author Chetan
 */
 class FileData
{

    private StringBuffer sb = new StringBuffer();
    private ArrayList<String> arrayList;

    /**
     * Method that traverse the contents of the folder.
     *
     * @param dir
     */
    public void checkContentsOfFolder(String dir)
      {

        arrayList = new ArrayList<>();
        FileSystem filesystems = FileSystems.getDefault();
        Path path = filesystems.getPath(dir);
        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS))
            {
            System.out.println("Currrent Path is a directory ");
            File folder = new File(path.toUri());
            String[] folderList = folder.list();
            System.out.println("Number Contents in the Directory : " + folderList.length);
            for (String s : folderList)
                {
                //check if the displayed file is direcotry or file
                Path p = filesystems.getPath(path.toAbsolutePath() + "/" + s);
                if (Files.isDirectory(p, LinkOption.NOFOLLOW_LINKS))
                    {
                    System.out.printf("%-20s \n", s + "\t" + "[DIR]");
                    String str = s + "\t [DIR]";
                    getArrayList().add(str);
                    getSb().append(str);
                    getSb().append(System.lineSeparator());
                    }
                else
                    {

                    System.out.println(s);
                    String str = s + "\t Size: " + p.toFile().length() + " bytes";
                    getArrayList().add(str);
                    getSb().append(s);
                    getSb().append(System.lineSeparator());
                    }
                }
            }

      }

    /**
     * @return the sb
     */
    public StringBuffer getSb()
      {
        return sb;
      }

    /**
     * Simple file visitor which searches for a file and then gives it to client
     *
     * @param file2
     * @throws IOException
     */
    public void fileVisitor(String file2) throws IOException
      {
        Files.walkFileTree(Paths.get(""), new SimpleFileVisitor<Path>()
        {

            Path fileReturned;

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
              {
                System.out.println("file: " + file);
                if (file.endsWith(file2))
                    {
                    fileReturned = file;
                    return FileVisitResult.TERMINATE;
                    }
                else
                    {
                    fileReturned = null;
                    return FileVisitResult.CONTINUE;
                    }
              }
        });
      }

    /**
     * MEthod for sending the txt/html/java file contents to client
     *
     * @param txt
     * @param out
     * @param socket
     * @throws IOException
     */
    public void findFile(String txt, PrintWriter out, Socket socket) throws IOException
      {
        FileSystem filesystems = FileSystems.getDefault();
        Files.walkFileTree(Paths.get(""), new SimpleFileVisitor<Path>()
        {

            Path fileReturned;

            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException
              {
                // System.out.println("file: " + path);
                if (path.endsWith(txt))
                    {
                    fileReturned = path;
                    System.out.println("found " + path);
                    ReaderInputFromFile read = new ReaderInputFromFile();
                    read.readFileData(path.toFile());
                    out.println("HTTP/1.0 200");
                    out.println("Content-type: text/plain,text/html");
                    out.println("Server-name: myserver");
                    String response = read.getFileData();
                    System.out.println(response);
                    out.println("Content-length: " + response.length());
                    out.println("");
                    out.println(response);
                    out.println("\r\n\r\n");
                    out.flush();
                    out.close();
                    out.close();

                    return FileVisitResult.TERMINATE;
                    }
                else
                    {
                    fileReturned = null;
                    return FileVisitResult.CONTINUE;
                    }
              }
        });

      }

    /**
     * @return the arrayList
     */
    public ArrayList<String> getArrayList()
      {
        return arrayList;
      }

    /**
     * Method for sending the xyz file
     *
     * @param txt
     * @param out
     * @param socket
     * @throws IOException
     */
    public void SendXYZ(String txt, PrintWriter out, Socket socket) throws IOException
      {
        OutputStream os = socket.getOutputStream();
        Files.walkFileTree(Paths.get(""), new SimpleFileVisitor<Path>()
        {
            Path fileReturned;
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException
              {
                // System.out.println("file: " + path);
                if (path.endsWith(txt))
                    {
                    fileReturned = path;
                    System.out.println("found " + path);
                    File file = path.toFile();
                    byte[] fileBytes = new byte[(int) file.length()];
                    FileInputStream fis = new FileInputStream(file);
                    fis.read(fileBytes);
                    out.println("HTTP/1.0 200");
                    out.println("Content-type: application/xyz");
                    String response = new String(fileBytes);
                    out.println("Content-length: " + response.length());
                    out.println("");
                    out.println(response);
                    out.println("\r\n\r\n");
                    out.flush();
                    os.flush();
                    os.close();
                    out.close();
                    return FileVisitResult.TERMINATE;
                    }
                else
                    {
                    fileReturned = null;
                    return FileVisitResult.CONTINUE;
                    }
              }
        });

      }

}

/**
 *Helper class that removes the get and http info from request
 * @author Chetan
 */
 class MyHelper
{

    private String requestedContent;

    public void removeGetAndHttp(String txt)
      {

            {
            if (!txt.isEmpty())
                {
                String s1 = txt.replace("GET /", "");
                requestedContent = s1.replace(" HTTP/1.1", "");
                //  System.out.println("String with main content " + requestedContent);
                }
            }
      }

    /**
     * @return the requestedContent
     */
    public String getRequestedContent()
      {
        System.out.println("Client Requested :" + requestedContent);
        return requestedContent;
      }

}

/**
 * class that reads the contents a file and then outputs it
 * @author Chetan
 */
 class ReaderInputFromFile
{

    StringBuffer sb = new StringBuffer();

    public String getFileData()
      {
        return sb.toString();
      }

    public void readFileData(File f)
      {
        FileReader fileReader = null;
        try
            {            
            fileReader = new FileReader(f);
            BufferedReader br = new BufferedReader(fileReader);
            String s;
            while ((s = br.readLine()) != null)
                {
                System.out.println(s);
                sb.append(s);
                sb.append(System.lineSeparator());
                }
            fileReader.close();
            }
            catch (FileNotFoundException ex)
            {
            Logger.getLogger(ReaderInputFromFile.class.getName()).log(Level.SEVERE, null, ex);
            }catch (IOException ex)
            {
            Logger.getLogger(ReaderInputFromFile.class.getName()).log(Level.SEVERE, null, ex);
            }
      }

      }

/**
 *Class to hold static variables
 * @author Chetan
 */
 class StaticVariables
{

   final public static String indexPage = "GET / HTTP/1.1";
   final public static String dogPage = "GET /Dog.txt HTTP/1.1";
   final public static String catPage = "GET /cat.html HTTP/1.1";
   final public static String addNumPage="GET /addnum.html HTTP/1.1";
   final public static String receiveNumber="addnums.cgi?person";
   final public static String directoryPage="showDirectories";
   final public static String favIcon="GET /favicon.ico HTTP/1.1";
   final public static String serveFilesFromRootDirectory="./";
   final public static String mimer_xyz="mimer-data.xyz";
    
}
