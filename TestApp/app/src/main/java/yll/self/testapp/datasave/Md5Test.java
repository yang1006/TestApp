package yll.self.testapp.datasave;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

import yll.self.testapp.utils.LogUtil;

/**
 * Created by yll on 17/11/17.
 */

public class Md5Test {




    public void test() throws Exception{

        String filePath = Environment.getExternalStorageDirectory().getPath() + "/Download/BiBiChat_release-14.apk";

        File file = new File(filePath);
        FileInputStream is = new FileInputStream(file);

        String md5_1 = getMD5(file2byte(is));
        LogUtil.yll(""+md5_1);

//        String md5_2 = getMD5(file2byte2(filePath));
//        LogUtil.yll(""+md5_2);
    }

    public void test2() throws Exception{

        String filePath = Environment.getExternalStorageDirectory().getPath() + "/Download/BiBiChat_release-14.apk";

        File file = new File(filePath);
        FileInputStream is = new FileInputStream(file);

//        String md5_1 = getMD5(file2byte(is));
//        LogUtil.yll(""+md5_1);

        String md5_2 = getMD5(file2byte2(filePath));
        LogUtil.yll(""+md5_2);
    }


    /**
     * 判断下载的apk文件md5值是否和传入一致
     *
     * @param apkPath 下载文件路径
     * @param apkMd5 接口返回md5
     * @return
     */
    public  boolean isMatchApkMd5(String apkPath, String apkMd5) {
        if (TextUtils.isEmpty(apkMd5)){
            return true;
        }
        if (TextUtils.isEmpty(apkPath)) {
            return false;
        }
        String md5 = "";
        try {
            FileInputStream fis = new FileInputStream(apkPath);
            md5 = getMD5(file2byte(fis));//之前这里用的是阿里里面的一个方法，这样需要测试一下，加了一个自己写的方法
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return apkMd5.equalsIgnoreCase(md5);
    }

    /**
     * 转换成字节
     * @param fis
     * @return
     */
    public  byte[] file2byte(FileInputStream fis) {
        byte[] buffer = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**这个方法转换成字节 比 file2byte() 少占用很多内存*/
    public  byte[] file2byte2(String filePath){
        byte[] bytes = null;
        File file = new File(filePath);
        if (file.exists()){
            long length = file.length();
            if (length > Integer.MAX_VALUE){
                LogUtil.yll("file2byte2 文件太大");
                return null;
            }
            try {
                InputStream is = new FileInputStream(file);
                bytes = new byte[(int) length];

                int offset = 0;
                int numRead = 0;

                while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset))>=0){
                    offset += numRead;
                }
                is.close();

                if (offset < bytes.length){
                    LogUtil.yll("file2byte2 file is error");
                    return null;
                }

            } catch (Exception e) {
                LogUtil.yll("file2byte2 异常->"+e.toString());
                e.printStackTrace();
            }

        }

        return bytes;

    }

    /**获取md5字符串*/
    public String getMD5(byte[] input) {
        return bytesToHexString(MD5(input));
    }

    private String bytesToHexString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        String table = "0123456789abcdef";
        StringBuilder ret = new StringBuilder(2 * bytes.length);
        for (int i = 0; i < bytes.length; i++) {
            int b;
            b = 0x0f & (bytes[i] >> 4);
            ret.append(table.charAt(b));
            b = 0x0f & bytes[i];
            ret.append(table.charAt(b));
        }
        return ret.toString();
    }

    /** 计算给定 byte [] 串的 MD5 */
    private byte[] MD5(byte[] input) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (md != null) {
            md.update(input);
            return md.digest();
        } else
            return null;
    }

}
