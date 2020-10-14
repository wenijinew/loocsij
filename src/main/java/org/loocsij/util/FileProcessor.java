package org.loocsij.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.*;
/**
 *<p>
 * Decorator to send command, read output and save to specified file. This decorator is used when the output of the command is predictably huge.<br />
 * Usage example:
 <code><pre>
 File logFile = new File("/path/to/log/file.log");
 DecoratorLargeDataFileReader largeDataDecorator = new DecoratorLargeDataFileReader(logFile);
 // ExtendedCli eCli = ...
 Decorator decorator = Decorators.decorateOneSendExecution(largeDataDecorator, eCli);
 eCli.addDecorator(decorator);
 </pre></code>
 *</p>
 */

public class FileProcessor {
    private static Logger log = LogManager.getLogger(FileProcessor.class);
    /**
     * formater
     */
    private static SimpleDateFormat parser;
    /**
     * file
     */
    private File file;
    /**
     * random accessor
     */
    private RandomAccessFile accessor;

    /**
     * file pointer
     */
    private long filePointer = -1L;

    public FileProcessor() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     *
     * @param file
     * @throws FileNotFoundException
     */
    public FileProcessor(File file) throws FileNotFoundException {
        super();
        if (file == null) {
            throw new RuntimeException(
                    "Usage:FileProcessor(File file),file can not be NULL!");
        }
        File parent = new File(file.getParent());
        if (!parent.exists()) {
            parent.mkdirs();
        }
        this.file = file;
        this.accessor = new RandomAccessFile(this.file, "rws");
    }

    public synchronized void writeLine(String content) throws IOException {
        accessor.seek(file.length());
        byte[] bts = content.getBytes();
        /*
         * specify the file pointer the parameter is not "file.length()" but
         * "file.length-2" this purpose is make no "linear white space"
         */
        for (int i = 0, l = bts.length; i < l; i++) {
            accessor.writeByte(bts[i]);
        }
        /*
         * first:Carriage Return second:line feed
         */
        accessor.writeByte(13);
        accessor.write(10);
    }

    public synchronized void writeLine(long content) throws IOException {
        String c = new Long(content).toString();
        this.writeLine(c);
    }

    public String readLineFromEOF() throws IOException {
        if (file.length() == 0) {
            return null;
        }
        if (filePointer == -1L) {
            filePointer = file.length();
        }
        // set file pointer in the last character
        if (filePointer == file.length()) {
            accessor.seek(--filePointer);
        }
        /*
         * there are two '\n' between the beginning of one line and the end of
         * another line
         */
        int descLineNumber = 0;
        int count = 2;
        String lineContent = null;
        if (accessor.read() > 0 && filePointer > 0) {
            count = 2;
            while (count > 0 && filePointer > 0) {
                /*
                 * '\n'==13,so you can also write in the following method if
                 * (accessor.read() == 13) {
                 */
                if (accessor.read() == '\n') {
                    count--;
                }
                if (count > 0 && filePointer > 0) {
                    accessor.seek(filePointer--);
                }
            }
            /*
             * if the line will be read is not the first line there is three
             * bytes in front of the line: 1:the last charachter's low byte of
             * the "next" line 2:'r' 3:'n' so filePointer need to move three
             * byte and if present filePointer is the beginning of the file
             * there is no need to move any more
             */
            if (filePointer > 0) {
                accessor.seek(filePointer += 2);
            }
            if (filePointer == 0) {
                accessor.seek(filePointer);

            lineContent = accessor.readLine();
            descLineNumber++;
            /*
             * reach the end of file when read the last line so need seek the
             * file pointer at he beginning of the last line
             */
            if (filePointer > 0) {
                accessor.seek(filePointer);
            }
        }
        return lineContent;
    }

    public long length() {
        return file.length();
    }

    public void closeFile() throws IOException, CloneNotSupportedException {
        this.accessor.close();
    }

    public static String getFileName(String datePattern,String name,String ext){
        StringBuffer fileName = new StringBuffer();
        if(name==null||ext==null){
            return null;
        }
        if(datePattern!=null){
            parser = new SimpleDateFormat(datePattern);
            String strDate = parser.format(new Date());
            fileName.append(strDate).append("_").append(name).append(".").append(ext);
        }else{
            fileName.append(name).append(".").append(ext);
        }
        return fileName.toString();
    }

    public static String read(String file){
        FileReader reader = null;
        try{
            reader = new FileReader(file);
        }catch(Exception e){
            log.error("Error", e);
        }
        StringBuffer buf = new StringBuffer();
        int c;

        try {
            while ((c = reader.read()) != -1)
               buf.append((char)c);
        } catch (IOException e) {
            log.error("Error", e);
        }

        return buf.toString();
    }

    public static void main(String[] strs) throws Exception {

    }
}
