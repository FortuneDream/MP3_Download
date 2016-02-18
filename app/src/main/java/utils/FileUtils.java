package utils;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
    private String SDPATH;

    public String getSDPATH() {
        return SDPATH;
    }
    public FileUtils() {
        SDPATH = Environment.getExternalStorageDirectory() + "/";
    }
    //得到SDcard目录名

    public File creatSDFile(String fileName) throws IOException {
        File file = new File(SDPATH + fileName);
        return file;
    }
    //SDcard创建文件

    public File creatSDDir(String dirName) {
        File dir = new File(SDPATH + dirName);
        return dir;
    }
    //SDcard目录名

    public boolean isFileExist(String fileName){
        File file = new File(SDPATH + fileName);
        return file.exists();
    }

    public File write2SDFromInput(String path,String fileName,InputStream input){
        File file = null;
        OutputStream output = null;
        try{
            creatSDDir(path);//创建目录
            file = creatSDFile(path + fileName);//创建文件
            output = new FileOutputStream(file);
            byte buffer [] = new byte[4 * 1024];
            while((input.read(buffer)) != -1){
                output.write(buffer);
            }
            output.flush();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            try{
                output.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return file;
    }

}