package com.example.lenovo.zyy.act;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.example.lenovo.zyy.R;
import com.example.lenovo.zyy.fragment.ChatListFragment;
import com.example.lenovo.zyy.fragment.TestFragment;
import com.example.lenovo.zyy.fragment.NewFragment;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

public class  MainActivity extends BaseActivity implements View.OnClickListener, EMMessageListener{
    private Button b1,b2,b3;
    private ViewPager viewpager;
    private ArrayList<Fragment> list=new ArrayList<>();
    private ChatListFragment chatListFragment;
    private TestFragment linkmanFragment;
    private NewFragment newFragment;
    private FragmentPagerAdapter fpa;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 //监听消息更新       添加消息监听
        EMClient.getInstance ()
                .chatManager ()
                .addMessageListener (this);
//初始化Fragment
        initView();
    }

    private void initView() {
        tabLayout= (TabLayout) findViewById (R.id.main_tab);
        viewpager= (ViewPager) findViewById(R.id.main_viewpager);
        b1= (Button) findViewById(R.id.main_b1);
        b2= (Button) findViewById(R.id.main_b2);
        b3= (Button) findViewById(R.id.main_b3);
        b1.setOnClickListener (this);
        b2.setOnClickListener (this);
        b3.setOnClickListener (this);
        getFragment();

//        fragmentPager适配器
         fpa=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

//             @Override
//             public CharSequence getPageTitle(int position) {
//                 return position+"";
//             }

             @Override
            public int getCount() {
                return list.size();
            }
        };
        viewpager.setAdapter(fpa);
//        设置屏幕以外的页面数     把tabLayout和viewPager绑定
        tabLayout.setupWithViewPager (viewpager);

        tabLayout.getTabAt (0).setIcon (R.drawable.aa);
        tabLayout.getTabAt (1).setIcon (R.drawable.bb);
        tabLayout.getTabAt (2).setIcon (R.drawable.cc);

        tabLayout.addOnTabSelectedListener (new TabLayout.OnTabSelectedListener () {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewpager.setOffscreenPageLimit (2);

//        setItem (0);
//        添加页面
        viewpager.addOnPageChangeListener (new ViewPager.OnPageChangeListener () {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getFragment() {
        newFragment=new NewFragment ();
        chatListFragment=new ChatListFragment();
        linkmanFragment=new TestFragment ();
        list.add(chatListFragment);
        list.add(linkmanFragment);
        list.add(newFragment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_b1:
                setItem (0);
                break;
            case R.id.main_b2:
                setItem (1);
                break;
            case R.id.main_b3:
                setItem (2);
                break;
        }
    }
    private void setItem(int index){
//        设置当前页面
        viewpager.setCurrentItem(index,true);
    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        runOnUiThread (new Runnable () {
            @Override
            public void run() {
                //收到消息
                chatListFragment.resetList ();
//                MessageManager.getInstance ().getiMessageList ().refChatList ();
            }
        });

    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {
        //收到透传消息

    }

    @Override
    public void onMessageReadAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageDeliveryAckReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }
}
