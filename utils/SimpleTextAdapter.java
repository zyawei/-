package com.miaxis.outdoorclock.utils.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @date: 2019/2/25 11:09
 * @author: zhang.yw
 * @project: MR860CameraTest
 */
public class SimpleTextAdapter<T> extends BaseAdapter {

    private final int mResource;
    private final List<T> mData;
    private final LayoutInflater mInflater;
    private final String showColumn;

    public SimpleTextAdapter(Context context, String showColumn, List<T> mData) {
        this(context, android.R.layout.simple_list_item_1, showColumn, mData);
    }


    public SimpleTextAdapter(Context context, @LayoutRes int resource, String showColumn, List<T> mData) {
        this.mData = mData;
        this.mResource = resource;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.showColumn = showColumn;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(mInflater, position, convertView, parent, mResource);
    }

    private View createViewFromResource(LayoutInflater inflater, int position, View convertView, ViewGroup parent, int resource) {
        View v;
        if (convertView == null) {
            v = inflater.inflate(resource, parent, false);
        } else {
            v = convertView;
        }
        bindView(position, v);
        return v;
    }

    protected void bindView(int position, View v) {
        TextView textView = findTextView(v);
        if (textView != null) {
            String text = getShowText(position);
            textView.setText(text);
        }
    }

    protected TextView findTextView(View rootView) {
        if (rootView instanceof TextView) {
            return (TextView) rootView;
        } else if (rootView instanceof ViewGroup) {
            ViewGroup viewGroup = ((ViewGroup) rootView);
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childView = viewGroup.getChildAt(i);
                if (childView instanceof TextView) {
                    return (TextView) childView;
                }
            }
        }
        return null;
    }

    protected String getShowText(int position) {
        T item = getItem(position);
        if (showColumn == null) {
            return item.toString();
        }
        String value = tryGetFromMethod(item, showColumn);
        if (value == null) {
            value = tryGetFromField(item, showColumn);
        }
        return value;
    }

    private static String tryGetFromField(Object item, String fieldName) {
        try {
            Class<?> aClass = item.getClass();
            Field field = aClass.getField(fieldName);
            field.setAccessible(true);
            return field.get(item).toString();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        //获取所有属性
        return null;
    }

    private static String tryGetFromMethod(Object item, String fieldName) {
        Class<?> aClass = item.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();
        String setMethodName = getSetMethodNameOfField(fieldName);
        Method method = null;
        for (Method m : declaredMethods) {
            String mName = m.getName();
            if (mName.equals(fieldName) || mName.equals(setMethodName)) {
                method = m;
                break;
            }
        }
        if (method != null) {
            try {
                method.setAccessible(true);
                Object invoke = method.invoke(item);
                return invoke.toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String getSetMethodNameOfField(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        char[] chars = str.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] = (char) (chars[0] - 32);
        }
        char[] setMethodName = new char[chars.length + 3];
        System.arraycopy("set".toCharArray(), 0, setMethodName, 0, 3);
        System.arraycopy(chars, 0, setMethodName, 3, chars.length);
        return new String(setMethodName);
    }

}
