package com.asuper.angelgroup.common.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.v4.BuildConfig;
import android.support.v4.content.FileProvider;

import com.asuper.angelgroup.common.set.GlobalParam;
import com.asuper.angelgroup.moduel.login.LoginActivity;
import com.asuper.angelgroup.moduel.login.bean.RolesBean;
import com.asuper.angelgroup.moduel.login.bean.UserManager;

import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by shubei on 2017/6/24.
 */

public class ComUtils {

    /**
     * 将一个字符串转化为输入流
     */
    public static InputStream getStringStream(String sInputString) {
        if (sInputString != null && !sInputString.trim().equals("")) {
            try {
                ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
                return tInputStringStream;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static String getStrExceptNull(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    /**
     * 保存对象
     *
     * @param ser
     * @param file
     * @throws IOException
     */
    public static boolean saveObject(Context context, Serializable ser,
                                     String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(file, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
            } catch (Exception e) {
            }
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    private static boolean isExistDataCache(Context context, String cachefile) {
        boolean exist = false;
        File data = context.getFileStreamPath(cachefile);
        if (data.exists())
            exist = true;
        return exist;
    }

    /**
     * 读取对象
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static Serializable readObject(Context context, String file) {
        if (!isExistDataCache(context, file))
            return null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = context.openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.printStackTrace();
            // 反序列化失败 - 删除缓存文件
            if (e instanceof InvalidClassException) {
                File data = context.getFileStreamPath(file);
                data.delete();
            }
        } finally {
            try {
                ois.close();
            } catch (Exception e) {
            }
            try {
                fis.close();
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 判断list是否为空
     *
     * @param list
     * @return true 空 false 不为空
     */
    public static <T> boolean isListEmpty(List<T> list) {
        if (list == null || list.size() <= 0)
            return true;
        return false;
    }

    public static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), "com.asuper.angelgroup.FileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    /***
     * 是否包含敏感词
     */
    public static boolean isHadBadWords(String inputWords) {
        if (inputWords == null) {
            return false;
        }
        for (String badWords : GlobalParam.badWordList) {
            if (inputWords.contains(badWords)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 保存用户名，避免登录再输
     */
    public static void saveCurrentUserName(Context mContext) {
        SharedPreferences spf = mContext.getSharedPreferences(LoginActivity.FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("logindate", DateTool.getCurrentDate());
        editor.putString("usertoken", GlobalParam.userToken);
        editor.commit();
    }

    /**
     * 删除用户缓存信息
     */
    public static void deleteCurrentUserName(Context mContext) {
        SharedPreferences spf = mContext.getSharedPreferences(LoginActivity.FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("logindate", "");
        editor.putString("usertoken", "");
        editor.commit();
    }

    // 删除ArrayList中重复元素
    public static void removeDuplicate(List<RolesBean.LabelsBean> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getId().equals(list.get(i).getId())) {
                    list.remove(j);
                }
            }
        }
    }

    // 方法二：通过HashSet剔除
    // 删除ArrayList中重复元素,add进去顺序就变了不考虑顺序的话可以使用
    public static void removeDuplicate1(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        System.out.println(list);
    }

    // 方法三： 删除ArrayList中重复元素，保持顺序
    // 删除ArrayList中重复元素，保持顺序
    public static void removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext();) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
    }

}
