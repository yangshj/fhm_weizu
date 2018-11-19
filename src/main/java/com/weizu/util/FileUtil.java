package com.weizu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

    /**
     * 文件复制
     * @param source 源文件路径
     * @param target 目标文件路径
     */
    public void fileCopy(String source, String target) {
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            String fileName = target.substring(0, target.lastIndexOf("/"));
            File fileDir = new File(fileName);
            if (!fileDir.exists()) {
                System.out.println("创建目录: "+fileName);
                fileDir.mkdirs();
            }
            input = new FileInputStream(new File(source));
            output = new FileOutputStream(new File(target));
            byte[] bt = new byte[1024];
            int realbyte = 0;
            while ((realbyte = input.read(bt)) > 0) {
                output.write(bt,0,realbyte);
            }
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
