package com.home.computerguess1a2bdemo.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.home.computerguess1a2bdemo.R;

/**
 * 自定义Dialog
 */
public class ShowPromptDialog extends Dialog {

    private TextView titleTextView, contentTextView, okTextView;
    private String title, content;
    private View.OnClickListener onOkListener;

    public ShowPromptDialog(Context context) {
        super(context);
    }

    /**
     * @param context 上下文
     * @param theme   给dialog设置的主题
     */
    public ShowPromptDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.widget_dialog_show_prompt);

        initializationView();
        initializeBusinessDetails();
    }

    private void initializationView() {
        titleTextView = findViewById(R.id.titleTextView);
        contentTextView = findViewById(R.id.contentTextView);
        okTextView =  findViewById(R.id.okTextView);
    }

    private void initializeBusinessDetails() {
        if (!TextUtils.isEmpty(title)) {
            titleTextView.setVisibility(View.VISIBLE);
            titleTextView.setText(title);
        } else {
            titleTextView.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(content)) {
            contentTextView.setVisibility(View.VISIBLE);
            contentTextView.setText(content);
        } else {
            contentTextView.setVisibility(View.GONE);
        }
        if (onOkListener != null) {
            okTextView.setVisibility(View.VISIBLE);
            okTextView.setOnClickListener(onOkListener);
        } else {
            okTextView.setVisibility(View.GONE);
        }
    }

    public void setTitleText(String title) {
        this.title = title;
    }

    public void setContentText(String content) {
        this.content = content;
    }

    public void setOnOkListener(View.OnClickListener onOkListener) {
        this.onOkListener = onOkListener;
    }
}