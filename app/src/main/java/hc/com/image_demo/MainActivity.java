package hc.com.image_demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private TextView tv1,tv2,tv3;
    private ImageView image1,image2,image3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setImage1();
        setImage2();
        setImage3();
    }

    private void setImage3() {
        try {
            InputStream in = getAssets().open("mm.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(in);

            ByteArrayOutputStream bos =new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,10,bos);

            bitmap.recycle();

            byte [] bytes = bos.toByteArray();
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

            image3.setImageBitmap(bitmap1);
            showBitmap(bitmap1,tv3);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setImage2() {
        try {
            InputStream in = getAssets().open("mm.jpg");

            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in,null,options);
            int inSampleSize = 1;

            int width = options.outWidth;
            int height = options.outHeight;

            //400*300
            while (width/inSampleSize>400||height/inSampleSize>300){
                inSampleSize*=2;
            }

            options.inSampleSize=inSampleSize;
            options.inJustDecodeBounds=false;
            Bitmap bitmap = BitmapFactory.decodeStream(in,null,options);

            image2.setImageBitmap(bitmap);
            showBitmap(bitmap,tv2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setImage1() {
        try {
            InputStream in = getAssets().open("mm.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            image1.setImageBitmap(bitmap);

            showBitmap(bitmap,tv1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showBitmap(Bitmap bitmap, TextView tv) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int size = bitmap.getRowBytes()*bitmap.getHeight();

        String strSize = Formatter.formatFileSize(this,size);

        tv.setText(tv.getText()+"---"+width+"*"+height+"---"+size+"---"+strSize);
    }

    private void initView() {
        tv1= (TextView) findViewById(R.id.tv1);
        image1= (ImageView) findViewById(R.id.image1);
        tv2= (TextView) findViewById(R.id.tv2);
        image2= (ImageView) findViewById(R.id.image2);
        tv3= (TextView) findViewById(R.id.tv3);
        image3= (ImageView) findViewById(R.id.image3);
    }
}
