package com.asuper.angelgroup.moduel.login;

import android.content.Intent;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.asuper.angelgroup.BaseActivity;
import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.tool.ComUtils;


/**
 * 数据输入框页
 */
public class MeEditActivity extends BaseActivity {

    private EditText edit_info;

    private String oldData;
    private int maxLength = 20;

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_me_edit);
    }

    @Override
    public void initView() {
        setPagTitle("编辑资料");
        edit_info = (EditText) findViewById(R.id.edit_info);
    }

    @Override
    public void initData() {
        oldData = getIntent().getStringExtra("oldData");
        maxLength = getIntent().getIntExtra("maxLength", 20);
        edit_info.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        if (!TextUtils.isEmpty(oldData)) {
            edit_info.setText(oldData);
        }
        String pageTitle = getIntent().getStringExtra("pageTitle");
        if (!TextUtils.isEmpty(pageTitle)) {
            setPagTitle(pageTitle);
        }
    }

    @Override
    public void initListener() {
        area_right.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String newData = edit_info.getText().toString().trim();

                if (ComUtils.isHadBadWords(newData)) {
                    showShortToast("您输入内容非法，请检查后重新输入");
                    return;
                }

                if (TextUtils.isEmpty(newData)) {
                    showShortToast("输入不能为空");
                    return;
                }


                hideKeyboard();

                Intent backData = new Intent();
                backData.putExtra("newData", newData);
                setResult(RESULT_OK, backData);
                finish();
            }
        });
    }

}
