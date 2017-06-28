package com.example.lenovo.zyy.act;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.lenovo.zyy.R;
import com.example.lenovo.zyy.fragment.BaseFragment;
import com.example.lenovo.zyy.fragment.FaceSelectFragment;
import com.example.lenovo.zyy.fragment.ImageSelectFragment;
import com.example.lenovo.zyy.fragment.VoiceFragment;
import com.example.lenovo.zyy.message.MessageManager;
import com.example.lenovo.zyy.myadapter.MessageAdapter;
import com.example.lenovo.zyy.utils.StringUtil;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/5/8.
 */

public class MessageActivity extends BaseActivity implements EMMessageListener, View.OnClickListener, TextWatcher {
    private String name;
    private RecyclerView recyclerView;
    private ArrayList<EMMessage> messages;
    private MessageAdapter me;
    private EditText et;
    private Button tv;
    private String caogao;
    private String txt;
    private int lastPostion = 0;
    private Button b1, b2, b3, b4;
    //    图片选择Fragment
    private ImageSelectFragment imageSelectFragment;
    //    表情选择Fragment
    private FaceSelectFragment faceSelectFragment;
    //    录制音频Fragment
    private VoiceFragment voiceFragment;
    //    当前打开的Fragment
    private BaseFragment currFragment;

    private boolean isGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_message);
//        从intent中获取数据 并设置标题
        getDataFromIntent ();
        //添加消息监听     设置
        EMClient.getInstance ().chatManager ().addMessageListener (this);
//        获取消息监听
        messages = getList ();
        initView ();
        initListView ();
        setEnble ();
        setTestStr ();

    }

    private void setTestStr() {
        //草稿 设置
        et.setText (caogao);
//        设置光标
        et.setSelection (et.getText ().length ());
    }

    private void setEnble() {
        if (et.getText ().toString ().isEmpty ()) {
            tv.setEnabled (false);
        } else {
            tv.setEnabled (true);
        }
    }

    private void initView() {
        b1 = (Button) findViewById (R.id.message_b1);
        b2 = (Button) findViewById (R.id.message_b2);
        b3 = (Button) findViewById (R.id.message_b3);
        b4 = (Button) findViewById (R.id.message_b4);
        b1.setOnClickListener (this);
        b2.setOnClickListener (this);
        b3.setOnClickListener (this);
        b4.setOnClickListener (this);

        initFragment ();

        tv = (Button) findViewById (R.id.message_tv);
        et = (EditText) findViewById (R.id.message_et);
//发消息btn监听  设置
        tv.setOnClickListener (this);

//        添加 文本改变监听 处理文本改变后要做的逻辑
        et.addTextChangedListener (this);
        et.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (imageSelectFragment.isVisible ()) {
                    Acc (imageSelectFragment);
                }
                if (faceSelectFragment.isVisible ()) {
                    Acc (faceSelectFragment);
                }
                if (voiceFragment.isVisible ()) {
                    Acc (voiceFragment);
                }

            }
        });
    }

    private void Acc(BaseFragment fragment) {
        //        获取FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager ();
//                如果被添加过 则删除
        FragmentTransaction transaction = fragmentManager.beginTransaction ();
        transaction.remove (fragment);
        transaction.commit ();
//            并清空该fragment的返回栈
        fragmentManager.popBackStackImmediate ();

    }

    private void initFragment() {
        imageSelectFragment = new ImageSelectFragment ();
        faceSelectFragment = new FaceSelectFragment ();
        voiceFragment = new VoiceFragment ();
    }

    private void initListView() {
        recyclerView = (RecyclerView) findViewById (R.id.message_rv);

        me = new MessageAdapter (this, messages);

        LinearLayoutManager llm = new LinearLayoutManager (this);
//       滑动到底部
        llm.setStackFromEnd (true);

        recyclerView.setLayoutManager (llm);
        recyclerView.setAdapter (me);

    }

    private ArrayList<EMMessage> getList() {
        //获取会话对象
        EMConversation conversation =
                EMClient.getInstance ()
                        .chatManager ()
                        .getConversation (name);

//        标记所有消息已读
        conversation.markAllMessagesAsRead ();
//从消息对话中获取所有消息
        ArrayList<EMMessage> magList = (ArrayList<EMMessage>) conversation.getAllMessages ();
//      如果 会话中的消息个数为一
        if (magList.size () == 1) {
            conversation.loadMoreMsgFromDB (magList.get (0).getMsgId (), 30);
        }
        return (ArrayList<EMMessage>) conversation.getAllMessages ();
    }

    private void getDataFromIntent() {
        //ChatList传递的userName  得到
        name = getIntent ().getStringExtra ("name");
        //草稿  得到
        caogao = getIntent ().getStringExtra ("caogao");

//        EMConversation chat = EMClient.getInstance ().chatManager ().getConversation (name);
        EMGroup group = EMClient.getInstance ().groupManager ().getGroup (name);
//        isGroup = chat.isGroup ();
        if (group != null) {
            setTitle (group.getGroupName ());
        } else {
//      设置ActionBar标题
            setTitle (name);
        }

//        setTitle (name);
        //标题栏 设置标题
//        getSupportActionBar ().setTitle (name);
        //标题栏 设置返回
        getSupportActionBar ().setDisplayHomeAsUpEnabled (true);
    }

    //菜单界面  加载
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater ();
        inflater.inflate (R.menu.menu_message, menu);
//        getMenuInflater ().inflate (R.menu.menu_message, menu);
        return true;
    }

    //   标题栏在这改
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId ()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent (this);
                if (NavUtils.shouldUpRecreateTask (this, upIntent)) {
                    TaskStackBuilder.create (this)
                            .addNextIntentWithParentStack (upIntent)
                            .startActivities ();
                } else {
                    upIntent.addFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    NavUtils.navigateUpTo (this, upIntent);
                }
                onBackPressed ();
                break;
            case R.id.menu_item_1:
//                Toast.makeText(this,"R.id.menu_item_1",Toast.LENGTH_LONG).show();
                Intent intent = new Intent (MessageActivity.this, GroupInfoActivity.class);
                intent.putExtra ("groupName", name);
                startActivity (intent);
                break;
        }
        return super.onOptionsItemSelected (item);
    }

    //返回键方法
    @Override
    public void onBackPressed() {
        // 有可见的fragment 则关闭fragment
        // 没有则关闭activity
        if (closeFragment ()) return;

        //关闭activity
        setResultAndClose ();

    }

    private boolean closeFragment() {
        if (currFragment != null) {
            getSupportFragmentManager ()
                    .beginTransaction ()
                    .remove (currFragment)
                    .commit ();
            return true;
        }
        return false;
    }

    private void setResultAndClose() {
        Intent data = new Intent ();
        data.putExtra ("name", name);
        data.putExtra ("txt", txt);
//    设置  activity关闭时 返回的数据
        setResult (RESULT_OK, data);
        super.onBackPressed ();
    }

    @Override
    public void onMessageReceived(final List<EMMessage> list) {
//        MessageManager.getInstance ().getiMessageList ().refChatList ();
        runOnUiThread (new Runnable () {
            @Override
            public void run() {
                messages.addAll (list);
                me.notifyDataSetChanged ();
            }
        });
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

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


    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.message_tv:
                String str = et.getText ().toString ();
                EMMessage msg = MessageManager.getInstance ().createTxt (str, name, isGroup ? EMMessage.ChatType.GroupChat : EMMessage.ChatType.Chat);
                notifyMsg (msg);
                et.setText ("");
                break;
            case R.id.message_b1:
                openImageSelect (imageSelectFragment);
                hideSoft ();
                break;
            case R.id.message_b2:
                openImageSelect (faceSelectFragment);
                hideSoft ();
                break;
            case R.id.message_b3:
                openImageSelect (voiceFragment);
                hideSoft ();
                break;
            case R.id.message_b4:
//              拍照
//                Intent intent1 = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
//              录像
                Intent intent = new Intent (MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult (intent, 1010);
                hideSoft ();
                break;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode, resultCode, data);

        Log.e ("onActivityResult", requestCode + " - - - - - - - -  " + resultCode + " - - - - - - - -  " + data.getData ());
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1010:
                    EMMessage msg = MessageManager.getInstance ().createVideo (this, data, name,isGroup ? EMMessage.ChatType.GroupChat : EMMessage.ChatType.Chat);
                    notifyMsg (msg);
                    break;
            }
        }

    }

    private void notifyMsg(EMMessage msg) {
        // 情况草稿
        txt = "";
        // 将发送的消息添加到消息列表中
        messages.add (msg);
        me.notifyDataSetChanged ();
    }

    public void sendImg(String path) {
        EMMessage msg = MessageManager
                .getInstance ()
                .createImaage (
                        path
                        , name
                        , false
                        , isGroup ? EMMessage.ChatType.GroupChat : EMMessage.ChatType.Chat);
        notifyMsg (msg);
    }

    /**
     * 输入目录下滑
     */
    private void hideSoft() {
        InputMethodManager imm = (InputMethodManager) getSystemService (INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow (et.getWindowToken (),0);
        imm.hideSoftInputFromWindow (getWindow ().getDecorView ().getWindowToken (), 0);
    }

    /**
     * 打开关闭  选择图片fragment
     */
    private void openImageSelect(BaseFragment fragment) {
//        获取FragmentManager
        FragmentManager fragmentManager = getSupportFragmentManager ();
//        判断 选择图片fragment是否被添加过
        if (fragment.isVisible ()) {
//            如果被添加过 则删除
            FragmentTransaction transaction = fragmentManager.beginTransaction ();
            transaction.remove (fragment);
            transaction.commit ();
//            并清空该fragment的返回栈
            fragmentManager.popBackStackImmediate ();
            currFragment = null;
        } else {
//            如果没有被添加过  则替换并添加返回栈
            FragmentTransaction transaction = fragmentManager.beginTransaction ();
            transaction.replace (R.id.message_framelayout, fragment);
            transaction.commit ();
            currFragment = fragment;
        }
    }

    public void setFace(String string) {
        et.append (StringUtil.getExpressionString (this, string));
    }

    @Override
    protected void onDestroy() {
        //移除消息更新监听
        EMClient.getInstance ().chatManager ().removeMessageListener (this);
        super.onDestroy ();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        setEnble ();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        txt = s.toString ();
        setEnble ();
    }

    public void createAudio(String fileName, int audioTime) {
        EMMessage msg = MessageManager.getInstance ().createAudio (fileName, audioTime, name, isGroup ? EMMessage.ChatType.GroupChat : EMMessage.ChatType.Chat);
        notifyMsg (msg);
    }
}
