package com.asuper.angelgroup.widget.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.asuper.angelgroup.R;
import com.asuper.angelgroup.common.tool.DevAttr;

import java.util.List;

/**
 * 选择城市dialog
 *
 * @author Lucia
 */
public class ComSelectDialog {
    private OnItemSelectLsner mOnItemSelectLsner;
    private Dialog dialog;
    private View view;
    private LayoutInflater inflater;
    private ListView listView;
    private ComSelectDialogListAdapter adapter;
    private List<String> lists;
    private TextView tv_select;

    public ComSelectDialog(Context context, List<String> lists) {
        dialog = new Dialog(context, R.style.dialog);
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.address_select_dialog, null);
        tv_select = (TextView) view.findViewById(R.id.tv_select);
        this.lists = lists;
        listView = (ListView) view.findViewById(R.id.dialog_listview);
        adapter = new ComSelectDialogListAdapter(lists, context);
        listView.setAdapter(adapter);

        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        WindowManager m = ((Activity) context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65
        if (lists.size() < 8) {
            p.height = DevAttr.dip2px(context, lists.size() * 40 + 55);
        } else {
            p.height = (int) (d.getHeight() * 0.8);
        }
        dialogWindow.setAttributes(p);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mOnItemSelectLsner != null) {
                    mOnItemSelectLsner.onSelect(position);
                }
                dismissDialog();
            }
        });
    }

    public void showDialog() {
        dialog.show();
    }

    public void dismissDialog() {
        dialog.dismiss();
    }

    public boolean isShow() {
        if (dialog.isShowing()) {
            return true;
        } else {
            return false;
        }
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setCancelable(boolean is) {
        dialog.setCancelable(is);
    }

    public interface OnItemSelectLsner {
        public void onSelect(int position);
    }

    public void setOnItemSelectLsner(OnItemSelectLsner lsner) {
        this.mOnItemSelectLsner = lsner;
    }

}
