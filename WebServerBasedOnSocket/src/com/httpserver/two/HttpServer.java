package com.httpserver.two;

import java.io.*;
import java.net.Socket;

public class HttpServer extends Thread{

    /**
     * web资源根路径
     */
    public static final String ROOT = "d:/";

    /**
     * 输入流对象，读取浏览器请求
     */
    private InputStream input;

    /**
     * 输出流对象，响应内容给浏览器
     */
    private OutputStream output;

    /**
     * @description 初始化socket对象，获取对应的输入、输出流
     * @param socket
     */
    public HttpServer(Socket socket) {
        try{
            input = socket.getInputStream();
            output = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        String filePath = null;
        filePath = read();
        response(filePath);
    }

    /**
     * @description 解析资源文件路径
     * @example GET /index.html HTTP/1.1
     * @return
     */
    private String read(){
        // 测试GET /index.html与GET /favicon.ico是否是为两个socket请求
        System.out.println("CurrentThreadId："+Thread.currentThread().getName());
        /*
        // 一样会阻塞！！！
        InputStream inputStream = input;
        byte[] buff = new byte[1024];
        StringBuilder sb = new StringBuilder();
        while (inputStream.read(buff)!=-1){
            sb.append(new String(buff));
        }
        String request = sb.toString();
        System.out.println(request);
        String[] lines = request.split("\r\n");
        String[] splits = lines[0].split(" ");
        */

        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        try {
            String line = reader.readLine();
            String[] splits = line.split(" ");
            if (splits.length!=3){
                return null;
            }
            System.out.println("Request Header: "+line);

            // 看下输入流有哪些内容
            /*
            // 实际上readLine()是一个阻塞函数，当没有数据读取时，就一直会阻塞在那，而不是返回null；
            // readLine()只有在数据流发生异常或者另一端被close()掉时，才会返回null值。
            while((line=reader.readLine())!=null){
                System.out.println(line);
            }
            */

            do{
                if((line=reader.readLine())!=null){
                    System.out.println(line);
                }
            } while(reader.ready()); //当缓冲区不为空，ready()返回true

            // 返回路径
            System.out.println("返回路径："+splits[1]);
            return splits[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void response(String filePath) {
        File file = new File(ROOT+filePath);
        if(file.exists()){
            // 1、资源存在，读取资源
            try{
                BufferedReader reader = new BufferedReader(new FileReader(file));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    System.out.println("line:"+ line);
                    sb.append(line).append("\r\n");
                }
                StringBuffer result = new StringBuffer();
                result.append("HTTP /1.1 200 ok \r\n");
                result.append("Content-Type:text/html \r\n");
                result.append("Content-Length:" + file.length() + "\r\n");
                result.append("\r\n" + sb.toString());
                output.write(result.toString().getBytes());
                output.flush();
                output.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            // 2、资源不存在，提示 file not found
            StringBuffer error = new StringBuffer();
            error.append("HTTP /1.1 400 file not found \r\n");
            error.append("Content-Type:text/html \r\n");
            error.append("Content-Length:20 \r\n").append("\r\n");
            error.append("<h1 >File Not Found..</h1>");
            try {
                output.write(error.toString().getBytes());
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
