package com.upload.utils;

import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 上传文件工具类
 */
@Log4j2
public class HttpUtils {

    private static final String URL_SINGLE_FILE = "http://127.0.0.1:8080/upload/single-file";
    private static final String URL_MULTI_FILE = "http://127.0.0.1:8080/upload/multi-file";

    /**
     * 使用OKHttp进行单文件上传
     * @param file
     * @return
     */
    public String uploadSingle(File file) {

        OkHttpClient client = new OkHttpClient();
        String result = "";
        MediaType mediaType = MediaType.parse("multipart/form-data");
        RequestBody body = new MultipartBody.Builder()
                .setType(mediaType)
                .addFormDataPart("file", file.getName(), RequestBody.create(mediaType, file))
                .build();
        Request request = new Request.Builder()
                .url(URL_SINGLE_FILE)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            int statusCode = response.code();
            log.info("StatusCode:{}", statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                result = response.body().string();
            }
            return result;
        } catch (IOException e) {
            log.error("{}", e);
            return result;
        }

    }

    /**
     * 使用OKHttp调用多文件上传接口进行多文件上传
     * @param param
     * @return
     */
    public String uploadMulti(List<File> param) {

        OkHttpClient client = new OkHttpClient();
        String result = "";
        MultipartBody.Builder multipartBody = new MultipartBody.Builder();
        MediaType mediaType = MediaType.parse("multipart/form-data");
        param.forEach(file -> {
            multipartBody.addFormDataPart("file", file.getName(), RequestBody.create(mediaType, file));
        });
        RequestBody body = multipartBody
                .setType(mediaType)
                .build();
        Request request = new Request.Builder()
                .url(URL_MULTI_FILE)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            int statusCode = response.code();
            log.info("StatusCode:{}", statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                result = response.body().string();
            }
            return result;
        } catch (IOException e) {
            log.error("{}", e);
            return result;
        }

    }


    /**
     * 使用HttpClient进行单文件上传
     * @param file
     * @return
     */
    public String doPostSingle(File file) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(URL_SINGLE_FILE);
            MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
            mEntityBuilder.addBinaryBody("file", file);
            httpPost.setEntity(mEntityBuilder.build());
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            log.info("StatusCode:{}", statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();
                result = EntityUtils.toString(httpEntity);
                EntityUtils.consume(httpEntity);
            }
            return result;
        } catch (IOException e) {
            log.error("{}", e);
            return result;
        } finally {
            HttpClientUtils.closeQuietly(httpClient);
            HttpClientUtils.closeQuietly(response);
        }

    }

    /**
     * 使用HttpClient调用多文件上传接口进行多文件上传
     * @param param
     * @return
     */
    public String doPostMulti(List<File> param) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(URL_MULTI_FILE);
            MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder.create();
            param.forEach(file -> {
                mEntityBuilder.addBinaryBody("file", file);
            });
            httpPost.setEntity(mEntityBuilder.build());
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            log.info("StatusCode:{}", statusCode);
            if (statusCode == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();
                result = EntityUtils.toString(httpEntity);
                EntityUtils.consume(httpEntity);
            }
            return result;
        } catch (IOException e) {
            log.error("{}", e);
            return result;
        } finally {
            HttpClientUtils.closeQuietly(httpClient);
            HttpClientUtils.closeQuietly(response);
        }

    }

    /**
     * 使用OKHttp调用单文件上传接口进行多文件上传
     * @param param
     * @return
     */
    public List<String> uploads(List<File> param) {
        List<String> list = new ArrayList<>();
        param.forEach(file -> list.add(uploadSingle(file)));
        return list;
    }

    /**
     * 使用HttpClient调用单文件上传接口进行多文件上传
     * @param param
     * @return
     */
    public List<String> doPosts(List<File> param) {
        List<String> list = new ArrayList<>();
        param.forEach(file -> list.add(doPostSingle(file)));
        return list;
    }

}
