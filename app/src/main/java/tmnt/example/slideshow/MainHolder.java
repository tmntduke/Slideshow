package tmnt.example.slideshow;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import tmnt.example.slideshow.Weight.Holder;

/**
 * Created by tmnt on 2017/4/15.
 */

public class MainHolder implements Holder<String> {

    private ImageView mImageView;
    private static final String TAG = "MainHolder";

    @Override
    public View createView(Context context) {
        mImageView = new ImageView(context);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        mImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data, View v) {
        Log.i(TAG, "UpdateUI: " + position);
        Log.i(TAG, "UpdateUI: "+data);
        Glide.with(context).load(data).into((ImageView) v);
    }


}
