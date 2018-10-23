package com.home.computerguess1a2bdemo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.home.computerguess1a2bdemo.databinding.ActivityMainBinding;
import com.home.computerguess1a2bdemo.widget.ShowPromptDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private List<String> stringList = new ArrayList<>(), historicalRecordList = new ArrayList<>(),
            wheelViewDataList = new ArrayList<>();
    private String number, userResponse, computerAnswer;
    private int A, B, count;
    private StringBuffer stringBuffer;
    private ShowPromptDialog showPromptDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        stringBuffer = new StringBuffer();
        initializationWheelView();
        initializationDataAndUi();

        /** 電腦第一次猜測, 從資料中隨機抽取一個 */
        count = 1;
        if (stringList.size() != 0) {
            computerAnswer = stringList.get((int) (Math.random() * stringList.size()));
        }
        activityMainBinding.computerAnswerTextView.setText("電腦第" + count + "次猜測︰" + computerAnswer);
        activityMainBinding.testTextView.setText("");
//
        /** 回報按鈕的點擊事件 */
        activityMainBinding.reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userResponse = activityMainBinding.aWheelView.getSelectedItemPosition() + "A"
                        + activityMainBinding.bWheelView.getSelectedItemPosition() + "B";
                if (userResponse.equals("4A0B")) {
                    showPromptDialog("訊息", "猜測成功！");
                } else {
                    historicalRecordList.add("第" + count + "次猜測為" + computerAnswer + "    "
                            + "回報為" + userResponse);
//
                    /** 如果資料庫中的數值與電腦猜測的數值比對AB, 然後得到的AB等於使用者的回報AB, 就表示有機會是使用者內心的答案
                     * 反之則刪除, 目的是將可能的選項範圍逐漸縮小 */
                    for (int i = 0; i < stringList.size(); i++) {
                        if (!produceAB(computerAnswer, stringList.get(i)).equals(userResponse)) {
                            stringList.remove(i);
                        }
                    }
//
                    if (stringList.size() == 0) {
                        showPromptDialog("錯誤", "您可能在回報的過程中回報了錯誤的資訊，導致本次猜測失敗。");
                    } else {
                        stringBuffer = new StringBuffer();
                        for (int i = 0; i < historicalRecordList.size(); i++) {
                            stringBuffer.append(historicalRecordList.get(i) + "\n");
                        }
                        activityMainBinding.testTextView.setText(stringBuffer);
                        computerAnswer = stringList.get((int) (Math.random() * stringList.size()));
                        count++;
                        activityMainBinding.computerAnswerTextView.setText("電腦第" + count + "次猜測︰" + computerAnswer);
                        activityMainBinding.aWheelView.setSelectedItemPosition(0, true, 1000);
                        activityMainBinding.bWheelView.setSelectedItemPosition(0, true, 1000);
                    }
                }
            }
        });
    }

    private boolean isRepeatNumber(String s) {
        String t;
        boolean b = true;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j < s.length(); j++) {
                t = s.substring(i, j);
                if (s.replace(t, "").length() < s.length() - t.length()) {
                    b = false;
                }
            }
        }
        return b;
    }

    /***
     * 比較2個字串, 並產生幾A幾B
     */
    String produceAB(String s1, String s2) {
        A = B = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                A++;
            } else if (s1.indexOf(s2.charAt(i)) != -1) {
                B++;
            }
        }
        return A + "A" + B + "B";
    }

    /**
     * 初始化WheelView
     */
    private void initializationWheelView() {
        wheelViewDataList = new ArrayList<>(1);
        for (int i = 0; i < 5; i++) {
            wheelViewDataList.add("" + i);
        }
        activityMainBinding.aWheelView.setData(wheelViewDataList);
        activityMainBinding.bWheelView.setData(wheelViewDataList);
    }

    /**
     * 顯示PromptDialog
     */
    private void showPromptDialog(String title, String content) {
        showPromptDialog = new ShowPromptDialog(MainActivity.this, R.style.ShowPromptDialog);
        WindowManager.LayoutParams lp = showPromptDialog.getWindow().getAttributes();
        lp.width = 160;  // 設置寬度
        lp.height = 90;  // 設置高度
        showPromptDialog.getWindow().setAttributes(lp);
        showPromptDialog.setTitleText(title);
        showPromptDialog.setContentText(content);
        showPromptDialog.setOnOkListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPromptDialog.dismiss();
                initializationDataAndUi();
            }
        });
        showPromptDialog.setCancelable(false);  // 設置這個對話框不能被用戶按返回鍵而取消掉
        showPromptDialog.show();
    }

    private void initializationDataAndUi() {
        historicalRecordList.clear();
        stringList = null;
        stringList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    for (int l = 0; l < 10; l++) {
                        number = "" + i + j + k + l;
                        if (isRepeatNumber(number)) {
                            stringList.add(number);
                        }
                    }
                }
            }
        }
        count = 1;
        if (stringList.size() != 0) {
            computerAnswer = stringList.get((int) (Math.random() * stringList.size()));
        }
        activityMainBinding.computerAnswerTextView.setText("電腦第" + count + "次猜測︰" + computerAnswer);
        stringBuffer = new StringBuffer();
        activityMainBinding.testTextView.setText(stringBuffer);
        activityMainBinding.aWheelView.setSelectedItemPosition(0, true, 1000);
        activityMainBinding.bWheelView.setSelectedItemPosition(0, true, 1000);
    }
}
