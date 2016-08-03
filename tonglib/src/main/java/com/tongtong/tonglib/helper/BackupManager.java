package com.tongtong.tonglib.helper;

import android.content.Context;
import android.os.Environment;

import com.tongtong.tonglib.util.LogUtil;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by KOHeeJin on 2016-08-02.
 */

    /*
        1. 내부 저장소
            1) 캐시(Cache) 저장 영역
               - API : File Context.getCacheDir()
               - 저장위치 : /data/data/[패키지 이름]/cache
            2) 데이터베이스(Database) 파일
               - API : File Context.getDatabasePath(String name)
               - 저장위치 : /data/data/[패키지 이름]/databases
            3) 일반 파일 저장 영역
               - API : File Context.getFilesDir()
               - 저장위치 : /data/data/[패키지 이름]/files

               - API : Context.getFileStreamPath(String name)
                       일반 파일이 저장된 공간에서 특정 이름을 가지는 파일의 경로를 반환합니다.
                       인자로 확장자를 포함한 파일 이름을 넘겨줍니다.
               - 저장위치 : /data/data/[패키지 이름]/files/[파일이름]
    */

public class BackupManager {

    private static Context mContext = null;
    private static String mSaveFilePath = null;

    public static void setContext(Context context) {
        mContext = context.getApplicationContext();

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // 외장메모리가 마운트 되어 있을 때
            mSaveFilePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            // 외장메모리가 마운트 되어 있지 않을 때
            mSaveFilePath = Environment.getDataDirectory().getAbsolutePath();
        }
    }

    public static boolean isExistBackUpFile(String fileName)
    {
        LogUtil.d("File Path : %s", mSaveFilePath + fileName);
        return new File(mSaveFilePath, fileName).exists();
    }

    public static void makeBackUpFile(String fileName, String inputText)
    {
        writeFile(fileName, inputText);
    }

    // 내부 저장소에 파일저장
    private static void writeFile(String fileName, String inputText)
    {
        try
        {
            LogUtil.d("파일 저장 위치 : %s", mSaveFilePath + fileName);

            FileOutputStream fos = new FileOutputStream(fileName);
            fos.write(inputText.getBytes());
            fos.close();
        } catch(Exception exception) {
            LogUtil.e("exception : %s", exception.getMessage());
        }
    }

    private static String readFile(String fileName)
    {
        String readText = null;

        try
        {
            FileInputStream fis  = new FileInputStream(fileName);

            byte[] data = new byte[fis.available()];
            while(fis.read(data) != -1) { }
            fis.close();

            readText = data.toString();
            // readText = new String(buffers);
        }
        catch(Exception filenotfoundexception)
        {
            LogUtil.e("Exception : %s", filenotfoundexception.getMessage());
        }

        return readText;
    }

    boolean checkExternalStorage() {
        String state = Environment.getExternalStorageState();

        // 외부메모리 상태
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // 읽기 쓰기 모두 가능
            LogUtil.d("외부메모리 읽기 쓰기 모두 가능");
            return true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
            //읽기전용
            LogUtil.d("외부메모리 읽기만 가능");
            return false;
        } else {
            // 읽기쓰기 모두 안됨
            LogUtil.d("외부메모리 읽기쓰기 모두 안됨 : "+ state);
            return false;
        }
    }

}
