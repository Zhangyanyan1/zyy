package com.example.lenovo.zyy.fragment;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.zyy.R;
import com.example.lenovo.zyy.act.MessageActivity;
import com.example.lenovo.zyy.collback.IMessageList;
import com.example.lenovo.zyy.collback.RecyclerItemClick;
import com.example.lenovo.zyy.message.MessageManager;
import com.example.lenovo.zyy.model.DeffStringBean;
import com.example.lenovo.zyy.myadapter.MyAdapter;
import com.example.lenovo.zyy.utils.SPUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lenovo on 2017/4/27.
 */

public class ChatListFragment extends BaseFragment implements IMessageList {
    private RecyclerView recyclerView;
    private MyAdapter ma;
    private ArrayList<EMConversation> list = new ArrayList<> ();
    private Map<String, EMConversation> conversations;
    private static int ITEM_D = 2;
    private Map<String, String> testStr = new ArrayMap<> ();
    private Gson gson = new Gson ();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate (R.layout.fragment_chatlist, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated (view, savedInstanceState);
        getChatList ();
        initView (view);

//        自己写的管理器
        MessageManager
                .getInstance ()
                .setiMessageList (this);

        getDeffFromSP ();
    }

    private void getDeffFromSP() {
        //拿草稿  : 访问SP得到JsonStr,并解析为理想Object
        String json = SPUtil.getChatDeff (getActivity ());
        if (!TextUtils.isEmpty (json)) {
            Type types = new TypeToken<ArrayList<DeffStringBean>> () {

            }.getType ();

            ArrayList<DeffStringBean> jsonArr = gson.fromJson (json, types);

            for (DeffStringBean d : jsonArr) {
                testStr.put (d.getKey (), d.getDeff ());
            }
        }

    }

    private void getChatList() {
        //      获取会话列表
        conversations =
                EMClient.getInstance ()
                        .chatManager ()
                        .getAllConversations ();

        Collection<EMConversation> l = conversations.values ();
        Iterator<EMConversation> it = l.iterator ();
        while (it.hasNext ()) {
            //遍历取得所需数据
            this.list.add (it.next ());
        }
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById (R.id.chatlist_rv);
        ma = new MyAdapter (getActivity (), list);
        LinearLayoutManager llm = new LinearLayoutManager (getActivity ());
//        llm.setOrientation(LinearLayoutManager.HORIZONTAL);    //变横向
//        GridLayoutManager glm=new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);  //变三列
//        StaggeredGridLayoutManager sglm=new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(glm);

        //子项更改时的动画  设置给 RecyclerView
        recyclerView.setItemAnimator (new DefaultItemAnimator ());

        //分割线 设置给 RecyclerView
        MyItemDecoration mid = new MyItemDecoration ();
        recyclerView.addItemDecoration (mid);

        MyRecyclerItemClick mric = new MyRecyclerItemClick ();
        ma.setRecyclerItemClick (mric);

        //布局管理器 设置给 RecyclerView
        recyclerView.setLayoutManager (llm);
        //适配器 设置给 RecyclerView
        recyclerView.setAdapter (ma);

    }

    /**
     * 带参数的跳转
     *
     * @param name
     */
    public void intent2Message(String name) {
        Intent intent = new Intent (getActivity (), MessageActivity.class);
        //携带数据
        intent.putExtra ("name", name);
        intent.putExtra ("caogao", testStr.get (name));
        //跳转     # getActivity().startActivityForResult()与此有差别
//        启动一个带返回值的Activity
        startActivityForResult (intent, 101);
    }

    /**
     * Activity返回是调用
     *
     * @param requestCode 请求码 用于区别此次返回是那个请求
     * @param resultCode  返回码 用于标记此次返回是成功还是失败
     * @param data        返回数据
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);
        //接收草稿、存储草稿     #SP存储，json转换     用到了GSON框架
//       判断返回码
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 101:
                    String key = data.getStringExtra ("name");
                    String value = data.getStringExtra ("txt");
                    if (TextUtils.isEmpty (value)) {
                        testStr.remove (key);
                    } else {
                        //草稿Map存入
                        testStr.put (key, value);
                    }
                    //更新ui
                    ma.setTestStr (testStr);

                    saveDeff ();

                    break;
            }
        }
    }

    private void saveDeff() {
//  把草稿Map集合转成 list集合
        List<DeffStringBean> deffs = new ArrayList<> ();
//      拿到 map集合中的 key集合
        Iterator<String> keys = testStr.keySet ().iterator ();
//       遍历 key集合
        while (keys.hasNext ()) {
//            拿到每一次的kay的值
            String keyC = keys.next ();
//            通过kay的值 从map集合中取到相应的value
//            并 设置给实体类
            DeffStringBean deffStringBean = new DeffStringBean ();
            deffStringBean.setDeff (testStr.get (keyC));
            deffStringBean.setKey (keyC);
            deffs.add (deffStringBean);
        }
//        把实体类
        String json = new Gson ().toJson (deffs);
//         把实体类
        SPUtil.setChatDeff (getActivity (), json);
    }

    /**
     * 刷新列表方法  ： 供外部调用
     */
    public void resetList() {
        list.clear ();
        getChatList ();
        ma.notifyDataSetChanged ();
    }

    @Override
    public void refChatList() {
        resetList ();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated (savedInstanceState);
    }

    class MyRecyclerItemClick implements RecyclerItemClick {

        @Override
        public void onItemClick(int index) {

            intent2Message (list.get (index).getUserName ());
        }

        @Override
        public boolean onItemLongClick(int index) {
            return false;
        }
    }

    class MyItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw (c, parent, state);
            Paint paint = new Paint ();
            paint.setColor (getResources ().getColor (R.color.color1));
            int left = parent.getPaddingLeft ();
            int right = parent.getMeasuredWidth () - parent.getPaddingRight ();
            int size = parent.getChildCount ();
            for (int i = 0; i < size; i++) {
                View child = parent.getChildAt (i);
                int top = child.getBottom ();
                int bottom = top + ITEM_D;
                //设置矩形的左。上。右。下边界坐标  以及画笔
                c.drawRect (left, top, right, bottom, paint);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets (outRect, view, parent, state);
            outRect.set (0, 0, 0, ITEM_D);
        }

    }
}
