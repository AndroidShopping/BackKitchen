package com.shop.backkitchen.util;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 *
 */
public class CachedPrefs {

    private SharedPreferences mPref;
    private Editor mEditor = null;
    /** 如果不需要commit,则不调用commit操作*/
    private boolean needCommit = false;

    public CachedPrefs(SharedPreferences pref) {
        mPref = pref;
        mEditor = pref.edit();
    }

    public void commit() {
        if (needCommit) {
            mEditor.commit();
            needCommit = false;
        }
    }

    public void apply() {
        if (needCommit) {
            mEditor.apply();
            needCommit = false;
        }
    }

    public void clear(){
        mEditor.clear();
        mEditor.commit();
    }

    public abstract class PrefVal<DatType> {
        private DatType mDat;
        protected String mKey;

        protected abstract DatType getValue(DatType defVal);

        protected abstract void setValue(DatType val);

        PrefVal(String key, DatType defVal) {
            mKey = key;
            mDat = getValue(defVal);
        }

        public DatType getVal() {
            return mDat;
        }

        public CachedPrefs setVal(DatType val) {
            boolean valueChanged = (mDat == null && val != null)
                    || (mDat != null && val == null)
                    || (mDat != null && !mDat.equals(val));
            if (valueChanged) {
                needCommit = true;
                mDat = val;
                setValue(val);
            }
            return CachedPrefs.this;
        }
    }

    public class BoolVal extends PrefVal<Boolean> {
        public BoolVal(String key, boolean defVal) {
            super(key, defVal);
        }

        @Override
        protected Boolean getValue(Boolean defVal) {
            return mPref.getBoolean(mKey, defVal);
        }

        @Override
        protected void setValue(Boolean val) {
            mEditor.putBoolean(mKey, val);
        }
    }

    public class IntVal extends PrefVal<Integer> {
        public IntVal(String key, int defVal) {
            super(key, defVal);
        }

        @Override
        protected Integer getValue(Integer defVal) {
            return mPref.getInt(mKey, defVal);
        }

        @Override
        protected void setValue(Integer val) {
            mEditor.putInt(mKey, val);
        }
    }

    public class StringVal extends PrefVal<String> {
        public StringVal(String key, String defVal) {
            super(key, defVal);
        }

        @Override
        protected String getValue(String defVal) {
            return mPref.getString(mKey, defVal);
        }

        @Override
        protected void setValue(String val) {
            mEditor.putString(mKey, val);
        }
    }

    public class LongVal extends PrefVal<Long> {
        public LongVal(String key, Long defVal) {
            super(key, defVal);
        }

        @Override
        protected Long getValue(Long defVal) {
            return mPref.getLong(mKey, defVal);
        }

        @Override
        protected void setValue(Long val) {
            mEditor.putLong(mKey, val);
        }
    }

}
