package com.upload.main;

import java.io.*;
import java.util.UUID;

/**
 * 测试文件生成
 */
public class Test {

    public static void main(String[] args) {

        for (int i = 0; i < 2333; i ++) {

            File file = new File("E:/x/" + UUID.randomUUID() + ".txt");
            try {
                OutputStream outputStream = new FileOutputStream(file);
                outputStream.write("这是一个测试文件".getBytes());
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
