package com.litingzhe.justandroid.someOther.voiceRecord.activity;

import android.app.Service;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.litingzhe.justandroid.R;
import com.litingzhe.justandroid.someOther.voiceRecord.utils.VoicePlayer;
import com.litingzhe.justandroid.someOther.voiceRecord.widget.EaseVoiceRecorderView;
import com.ningcui.mylibrary.app.base.AbBaseActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: 李挺哲
 * @date: 2018/7/10   15:32
 * @description:录音.m4a及气泡录音播放
 */
public class VoiceRecordActivity extends AbBaseActivity {


    @BindView(R.id.nav_back)
    LinearLayout navBack;
    @BindView(R.id.nav_title)
    TextView navTitle;
    @BindView(R.id.nav_right_icon)
    ImageView navRightIcon;
    @BindView(R.id.nav_right_text)
    TextView navRightText;
    @BindView(R.id.nav_right)
    LinearLayout navRight;
    @BindView(R.id.voiceBtn)
    ImageButton voiceBtn;
    @BindView(R.id.tv_send_content)
    EditText tvSendContent;
    @BindView(R.id.voiceSendBtn)
    Button voiceSendBtn;
    @BindView(R.id.sendBtn)
    Button sendBtn;
    @BindView(R.id.addBtn)
    ImageButton addBtn;
    @BindView(R.id.chatFooterLayout)
    LinearLayout chatFooterLayout;
    @BindView(R.id.voice_recorder)
    EaseVoiceRecorderView voiceRecorder;
    private boolean isHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_voicerecord);
        ButterKnife.bind(this);

        navTitle.setText("录音及播放");

        voiceBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (tvSendContent.getVisibility() == View.GONE) {
                    tvSendContent.setVisibility(View.VISIBLE);
                    voiceSendBtn.setVisibility(View.GONE);
                    isHide = false;
                    voiceBtn.setBackgroundResource(R.drawable.button_selector_chat_voice);
                    if (!isHide) {
                        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
                        mInputMethodManager.hideSoftInputFromWindow(tvSendContent.getWindowToken(), 0);
                        isHide = true;
                    }
                } else {
                    tvSendContent.setVisibility(View.GONE);
                    voiceSendBtn.setVisibility(View.VISIBLE);
                    voiceBtn.setBackgroundResource(R.drawable.button_selector_chat_key);
                    InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
                    mInputMethodManager.hideSoftInputFromWindow(tvSendContent.getWindowToken(), 0);
                    isHide = true;

                }

            }
        });


        voiceSendBtn.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // 按下
                        voiceSendBtn.setText("松开取消");
                        break;
                    case MotionEvent.ACTION_UP:// 抬起
                        voiceSendBtn.setText("按住说话");

                        break;
                    default:
                        break;
                }


                voiceRecorder.onPressToSpeakBtnTouch(v, event, new EaseVoiceRecorderView.EaseVoiceRecorderCallback() {
                    @Override
                    public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {


                        Toast.makeText(mContext, "voiceFilePath" + voiceFilePath, Toast.LENGTH_SHORT).show();

                        long length = new File(voiceFilePath).length();


                        VoicePlayer voicePlayer = VoicePlayer.getInstance(mContext);

                        voicePlayer.play(voiceFilePath, new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                Toast.makeText(mContext, "播放结束", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Log.d("Tag", voiceFilePath + "------length=" + new File(voiceFilePath).length());
                    }
                });

                return false;
            }
        });

    }


    @OnClick(R.id.nav_back)
    public void onViewClicked() {

        finish();

    }
}
