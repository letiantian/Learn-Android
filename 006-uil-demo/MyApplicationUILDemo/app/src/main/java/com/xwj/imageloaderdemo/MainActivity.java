package com.xwj.imageloaderdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xwj.util.Constants;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	// 点击进入ListView展示界面  
    public void onImageListClick(View view) {  
       Intent intent = new Intent(this, ImageListActivity.class);  
       intent.putExtra(Constants.IMAGES, Constants.images);  
       startActivity(intent);  
    }  
    
    public void onImageGridClick(View view) {
    	Intent intent = new Intent(this, ImageGridActivity.class);  
        intent.putExtra(Constants.IMAGES, Constants.images);  
        startActivity(intent);  
	}
    
    
    public void onImagePagerClick(View view) {
    	Intent intent = new Intent(this, ImageListActivity.class);  
        intent.putExtra(Constants.IMAGES, Constants.images);  
        startActivity(intent);  
	}
    
    
    public void onImageGalleryClick(View view) {
    	Intent intent = new Intent(this, ImageListActivity.class);  
        intent.putExtra(Constants.IMAGES, Constants.images);  
        startActivity(intent);  
	}
    
    public void onClearMemoryClick(View view) {
    	Toast.makeText(this, "清除内存缓存成功", Toast.LENGTH_SHORT).show();
    	ImageLoader.getInstance().clearMemoryCache();  // 清除内存缓存
	}
    
    public void onClearDiskClick(View view) {
    	Toast.makeText(this, "清除本地缓存成功", Toast.LENGTH_SHORT).show();
    	ImageLoader.getInstance().clearDiskCache();  // 清除本地缓存
    } 
    
}