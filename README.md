### 轮播图

![slideshow](slide.png)

1. 使用ViewPager

   往ViewPager中添加的原始数据为：
   view1，view2，viewer3

   为实现轮播在view1前添加一个view3，在原来的view3后在添加一个view1

   在使用viewPager。setCurrentItem(position)设置当前显示的view

   ```java
    @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (mViewPager.getCurrentItem() == 0) {
                            mViewPager.setCurrentItem(count-2, false);
                        } else if (mViewPager.getCurrentItem() == count-1) {
                            mViewPager.setCurrentItem(1, false);
                        }
                        currentItem = mViewPager.getCurrentItem();
                        break;
                }
            }
        });
   ```

2. 添加指示器 选中时变大 未选中变小

   创建指示器
   ```java
    private void setSelector(int count) {
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setBackgroundResource(R.drawable.unselect_shape);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dotSize, dotSize);
            layoutParams.leftMargin = dotSpace / 2;
            layoutParams.rightMargin = dotSpace / 2;
            layoutParams.topMargin = dotSpace / 2;
            layoutParams.bottomMargin = dotSpace / 2;
            mLinearLayout.addView(imageView, layoutParams);
        }

        mLinearLayout.getChildAt(0).setBackgroundResource(R.drawable.select_shape);
        animatorToLarge.setTarget(mLinearLayout.getChildAt(0));
        animatorToLarge.start();
        isLarge.put(0, true);
    }
   ```

   设置指示器
   ```java
    @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
                    if (i == position - 1) {// 被选中
                        mLinearLayout.getChildAt(i).setBackgroundResource(R.drawable.select_shape);
                        if (!isLarge.get(i)) {
                            animatorToLarge.setTarget(mLinearLayout.getChildAt(i));
                            animatorToLarge.start();
                            isLarge.put(i, true);
                            Log.i(TAG, "onPageSelected: "+isLarge.size());
                        }
                    } else {// 未被选中
                        mLinearLayout.getChildAt(i).setBackgroundResource(R.drawable.unselect_shape);
                        if (isLarge.get(i)) {
                            animatorToSmall.setTarget(mLinearLayout.getChildAt(i));
                            animatorToSmall.start();
                            isLarge.put(i, false);
                            Log.i(TAG, "onPageSelected: "+isLarge.size());
                        }
                    }
                }
            }
   ```

2. 根据不同场景设置显示图片

   ImageBean封装ImageView和请求数据（url，bean，资源文件）

   实现Holder设置imageView 和显示策略

   ```java
   public interface Holder<T> {
    View createView(Context context);
    void UpdateUI(Context context, int position, T data,View v);

}
   ```

```java
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
```

3. 轮播由Rxjava interval操作符实现

   ```java
    private void autoPlay() {
        mSubscription = Observable.interval(5, 5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        int posttion = (currentItem + 1) % mViewList.size();
                        mViewPager.setCurrentItem(posttion, true);
                    }
                });
    }
   ```

4. 使用

   mSlide.setData(new MainHolder(),imageUrl());

   传入Holder和显示数据即可

