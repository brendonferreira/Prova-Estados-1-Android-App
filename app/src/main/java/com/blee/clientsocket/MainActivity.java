

package com.blee.clientsocket;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    RadioGroup selectedCityRadioGroup;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectedCityRadioGroup = (RadioGroup)findViewById(R.id.city_radio_group);
        output = (TextView)findViewById(R.id.output);
    }

    String getSelectedCity() {
        switch (selectedCityRadioGroup.getCheckedRadioButtonId()) {
            case R.id.brasilia:
                return "brasilia";
            case R.id.curitiba:
                return "curitiba";
            case R.id.santa_rita:
                return "santa_rita";
        }

        return "";
    }

    public void fetchCityInfo(View view) {
        String inputTemp = getSelectedCity();
        Cities objCities = new Cities(inputTemp, handler);
        objCities.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {
                String uf = msg.getData().getString("uf");
                int population = msg.getData().getInt("population");
                int foundation = msg.getData().getInt("foundation");

                String result = uf + "\nPopulação: " + population + " \nFundação:" + foundation;

                output.setText(result);
            }
        }
    };
}
