package com.barberapp.barberuser.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.barberapp.barberuser.R;
import com.barberapp.barberuser.utils.AutoScrollViewPager;
import com.barberapp.barberuser.utils.CirclePageIndicator;

public class IndicatorsActivity extends BaseActivity {
    private CustomPagerAdapter mCustomPagerAdapter;
    private AutoScrollViewPager mViewPager;
    private CirclePageIndicator circlePageIndicator;
    int[] mResources = {
            R.drawable.ic_dummy_indicator,
            R.drawable.ic_dummy_indicator,
            R.drawable.ic_dummy_indicator
    };
    private Button btnLogin,btnSkip,btnStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicators);
        mCustomPagerAdapter = new CustomPagerAdapter(this);
        mViewPager = (AutoScrollViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.startAutoScroll();
        circlePageIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        circlePageIndicator.setViewPager(mViewPager);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndicatorsActivity.this,MobileNumberActivity.class);
                startActivity(intent);
            }
        });
        btnSkip = findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndicatorsActivity.this,MainBottomNavActivity.class);
                startActivity(intent);
            }
        });
        btnStart =findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IndicatorsActivity.this,MainBottomNavActivity.class);
                startActivity(intent);
            }
        });

    }
    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageView.setImageResource(mResources[position]);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}
