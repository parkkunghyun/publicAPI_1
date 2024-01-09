package org.techtown.publicapi_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    String key = "9jLPzVx%2BGOW1xmxuT3Zg0ylW%2BAox4i8%2BjBu4xPe%2B2W2r%2BuMwhr1YhfXwmiKFqfFFmOU6aNPMjTFt4g2cECEgSw%3D%3D";
    TextView text;
    String string_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    string_data = data();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text.setText(string_data);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String data() throws IOException {
        // 각각의 정보를 넣어주기
        StringBuilder urlBuilder = new StringBuilder("http://api.odcloud.kr/api/15055082/v1/uddi:0a5f4af0-3d39-4510-b26a-e53c24ea3b1e_201909051519");

        // page
        urlBuilder.append("?" + "page=1&perPage=10");
        // service key
        urlBuilder.append("&" + URLEncoder.encode("serviceKey", "UTF-8") + "="+ key);

        URL url = new URL(urlBuilder.toString());

        // 문자열로 된 요청 url을 URL객체로 생성
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        // getResponseCode 가 200과 300사이면 정상
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300 ) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
        return sb.toString();
    }
}
