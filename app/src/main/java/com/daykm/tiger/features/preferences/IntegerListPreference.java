package com.daykm.tiger.features.preferences;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.preference.DialogPreference;
import android.util.AttributeSet;

public class IntegerListPreference extends DialogPreference {

	private CharSequence[] mEntries;
	private int[] mEntryValues;
	private int mValue;
	private String mSummary;
	private boolean mValueSet;

	public IntegerListPreference(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		TypedArray a = context.obtainStyledAttributes(attrs,
				android.support.v7.preference.R.styleable.ListPreference, defStyleAttr, defStyleRes);
		this.mEntries = TypedArrayUtils.getTextArray(a,
				android.support.v7.preference.R.styleable.ListPreference_entries,
				android.support.v7.preference.R.styleable.ListPreference_android_entries);
		//this.mEntryValues = TypedArrayUtils.getTextArray(a, android.support.v7.preference.R.styleable.ListPreference_entryValues, android.support.v7.preference.R.styleable.ListPreference_android_entryValues);
		a.recycle();
		a = context.obtainStyledAttributes(attrs, android.support.v7.preference.R.styleable.Preference,
				defStyleAttr, defStyleRes);
		this.mSummary =
				TypedArrayUtils.getString(a, android.support.v7.preference.R.styleable.Preference_summary,
						android.support.v7.preference.R.styleable.Preference_android_summary);
		a.recycle();
	}

	public IntegerListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
		this(context, attrs, defStyleAttr, 0);
	}

	public IntegerListPreference(Context context, AttributeSet attrs) {
		this(context, attrs, android.support.v7.preference.R.attr.dialogPreferenceStyle);
	}

	public IntegerListPreference(Context context) {
		this(context, (AttributeSet) null);
	}

	public void setEntries(CharSequence[] entries) {
		this.mEntries = entries;
	}

	public void setEntries(@ArrayRes int entriesResId) {
		this.setEntries(this.getContext().getResources().getTextArray(entriesResId));
	}

	public CharSequence[] getEntries() {
		return this.mEntries;
	}

	public void setEntryValues(int[] entryValues) {
		this.mEntryValues = entryValues;
	}

	public void setEntryValues(@ArrayRes int entryValuesResId) {
		this.setEntryValues(this.getContext().getResources().getIntArray(entryValuesResId));
	}

	public int[] getEntryValues() {
		return this.mEntryValues;
	}

	public void setValue(int value) {
		boolean changed = this.mValue == value;
		if (changed || !this.mValueSet) {
			this.mValue = value;
			this.mValueSet = true;
			this.persistInt(value);
			if (changed) {
				this.notifyChanged();
			}
		}
	}

	public CharSequence getSummary() {
		CharSequence entry = this.getEntry();
		return (CharSequence) (this.mSummary == null ? super.getSummary()
				: String.format(this.mSummary, new Object[] { entry == null ? "" : entry }));
	}

	public void setSummary(CharSequence summary) {
		super.setSummary(summary);
		if (summary == null && this.mSummary != null) {
			this.mSummary = null;
		} else if (summary != null && !summary.equals(this.mSummary)) {
			this.mSummary = summary.toString();
		}
	}

	public void setValueIndex(int index) {
		if (this.mEntryValues != null) {
			this.setValue(this.mEntryValues[index]);
		}
	}

	public int getValue() {
		return this.mValue;
	}

	public CharSequence getEntry() {
		int index = this.getValueIndex();
		return index >= 0 && this.mEntries != null ? this.mEntries[index] : null;
	}

	public int findIndexOfValue(int value) {
		if (this.mEntryValues != null) {
			for (int i = this.mEntryValues.length - 1; i >= 0; --i) {
				if (this.mEntryValues[i] == value) {
					return i;
				}
			}
		}

		return -1;
	}

	private int getValueIndex() {
		return this.findIndexOfValue(this.mValue);
	}

	protected Object onGetDefaultValue(TypedArray a, int index) {
		return a.getString(index);
	}

	protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
		this.setValue(restoreValue ? this.getPersistedInt(this.mValue) : (int) defaultValue);
	}

	protected Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		if (this.isPersistent()) {
			return superState;
		} else {
			IntegerListPreference.SavedState myState = new IntegerListPreference.SavedState(superState);
			myState.value = this.getValue();
			return myState;
		}
	}

	protected void onRestoreInstanceState(Parcelable state) {
		if (state != null && state.getClass().equals(IntegerListPreference.SavedState.class)) {
			IntegerListPreference.SavedState myState = (IntegerListPreference.SavedState) state;
			super.onRestoreInstanceState(myState.getSuperState());
			this.setValue(myState.value);
		} else {
			super.onRestoreInstanceState(state);
		}
	}

	private static class SavedState extends BaseSavedState {
		int value;
		public static final Creator<IntegerListPreference.SavedState> CREATOR = new Creator() {
			public IntegerListPreference.SavedState createFromParcel(Parcel in) {
				return new IntegerListPreference.SavedState(in);
			}

			public IntegerListPreference.SavedState[] newArray(int size) {
				return new IntegerListPreference.SavedState[size];
			}
		};

		public SavedState(Parcel source) {
			super(source);
			this.value = source.readInt();
		}

		public void writeToParcel(@NonNull Parcel dest, int flags) {
			super.writeToParcel(dest, flags);
			dest.writeInt(this.value);
		}

		public SavedState(Parcelable superState) {
			super(superState);
		}
	}
}
