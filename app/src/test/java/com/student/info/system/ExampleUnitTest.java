package com.student.info.system;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String test="110226:北京市平谷县\n" +
                " \n" +
                "110227:北京市怀柔县 110228:北京市密云县 110229:北京市延庆县";
        String[] split = test.split(" ");
        for (String info : split) {
            String[] split1 = info.split(":");
            System.out.println("地点："+split1[1]+"    前6位："+split1[0]);
        }
    }

    @Test
    public void getLocationInfo() throws IOException {
        File file=new File("/Users/zhaoguangxing/Desktop/StudentInfoSystem/app/src/main/assets/location.txt");
        Long length = file.length();
        byte[] fileContent=new byte[length.intValue()];

        InputStream inputStream=new FileInputStream(file);
        inputStream.read(fileContent);
        inputStream.close();
        String s = new String(fileContent, Charset.defaultCharset());
        System.out.println(s.length());

//        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("location.txt")));
//        String locationInfo = null;
//        while((locationInfo = br.readLine())!=null) {
//
//        }
//
//        String[] split = locationInfo.split(" ");
//        for (String info : split) {
//            String[] split1 = info.split(":");
//            System.out.println("地点："+split1[1]+"    前6位："+split1[0]);
//        }

    }
    @Test
    public void getCard(){
        String card="610429199702144199";
        String idCard = card.substring(6, 14);
        String year = idCard.substring(0, 4);
        String month = idCard.substring(4, 6);
        String day = idCard.substring(6, 8);
        System.out.println(idCard);
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);

    }
}
