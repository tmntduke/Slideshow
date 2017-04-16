package tmnt.example.slideshow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import tmnt.example.slideshow.Weight.OnItemClickListener;
import tmnt.example.slideshow.Weight.SlideshowView;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.slide)
    SlideshowView mSlide;


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.i(TAG, "onCreate: "+imageUrl().size());
        mSlide.setData(new MainHolder(),imageUrl());
        mSlide.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.i(TAG, "onItemClick: "+position);
            }
        });

    }

    public  List<String> imageUrl() {

        List<String> list = new ArrayList<>();
        list.add("http://tva1.sinaimg.cn/crop.0.0.1080.1080.1024/871462bbjw8ew5s8g3y7oj20u00u0wg5.jpg");
        list.add("http://www.qq1234.org/uploads/allimg/150121/3_150121144650_12.jpg");
        list.add("http://www.sheyou114.com/wp-content/uploads/2014/06/b2c4514a3a942b6021878fc4efb22138.jpg");
        list.add("http://img1.gtimg.com/0/3/385/38567_1200x1000_0.jpg");
        list.add("http://img4.cache.netease.com/photo/0026/2015-05-19/APVC513454A40026.jpg");


        return list;
    }
}
