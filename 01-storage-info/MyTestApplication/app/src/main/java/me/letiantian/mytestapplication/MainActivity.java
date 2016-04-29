package me.letiantian.mytestapplication;

import android.os.Environment;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button dataStorageBtn;
    private Button internalSDBtn;
    private Button externalSDBtn;

    private TextView infoTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataStorageBtn = (Button) findViewById(R.id.data_storage_btn);
        internalSDBtn = (Button) findViewById(R.id.internal_sd_btn);
        externalSDBtn = (Button) findViewById(R.id.external_sd_btn);
        infoTv = (TextView) findViewById(R.id.info_tv);

        Map<String, File> externalLocations = ExternalStorage.getAllStorageLocations();
        final File sdCard = externalLocations.get(ExternalStorage.SD_CARD);
        final File externalSdCard = externalLocations.get(ExternalStorage.EXTERNAL_SD_CARD);

        dataStorageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            infoTv.setText(getFsStorageInfo(Environment.getDataDirectory()));
            }
        });

        internalSDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sdCard != null) {
                    infoTv.setText(getFsStorageInfo(sdCard));
                } else {
                    infoTv.setText("内置SD卡不存在");
                }
            }
        });


        externalSDBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (externalSdCard != null) {
                    infoTv.setText(getFsStorageInfo(externalSdCard));
                } else {
                    infoTv.setText("外置SD卡不存在");
                }
            }
        });
    }

    private String getFsStorageInfo(File path) {
        StatFs sf = new StatFs(path.getPath());
        long blockSize = sf.getBlockSize();
        long blockCount = sf.getBlockCount();
        long availCount = sf.getAvailableBlocks();

        StringBuffer sb = new StringBuffer();
        sb.append("path: "+ path.getPath() + "\n");
        sb.append("block大小:" + blockSize+"\n");
        sb.append("block数目:" + blockCount+"\n");
        sb.append("总大小:"+blockSize*blockCount/1024/1024+"MB \n");
        sb.append("可用的block数目:"+ availCount+"\n");
        sb.append("可用大小:"+ availCount*blockSize/1024/1024+"MB\n");

        return  sb.toString();
    }

}
