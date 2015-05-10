package com.droplit.wave.format;


import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.droplit.wave.utils.PreferenceUtils;

/**
 * Highlights the text in a text field.
 */
public class PrefixHighlighter {

    /* Color used when highlighting the prefixes */
    private final int mPrefixHighlightColor;

    private ForegroundColorSpan mPrefixColorSpan;

    /**
     * @param prefixHighlightColor The color used to highlight the prefixes.
     */
    public PrefixHighlighter(final Context context) {
        mPrefixHighlightColor = PreferenceUtils.getInstance(context).getDefaultThemeColor(context);
    }

    /**
     * Sets the text on the given {@link TextView}, highlighting the word that
     * matches the given prefix.
     *
     * @param view   The {@link TextView} on which to set the text
     * @param text   The string to use as the text
     * @param prefix The prefix to look for
     */
    public void setText(final TextView view, final String text, final char[] prefix) {
        if (view == null || TextUtils.isEmpty(text) || prefix == null || prefix.length == 0) {
            return;
        }
        view.setText(apply(text, prefix));
    }

    /**
     * Returns a {@link CharSequence} which highlights the given prefix if found
     * in the given text.
     *
     * @param text   the text to which to apply the highlight
     * @param prefix the prefix to look for
     */
    public CharSequence apply(final CharSequence text, final char[] prefix) {
        final int mIndex = indexOfWordPrefix(text, prefix);
        if (mIndex != -1) {
            if (mPrefixColorSpan == null) {
                mPrefixColorSpan = new ForegroundColorSpan(mPrefixHighlightColor);
            }
            final SpannableString mResult = new SpannableString(text);
            mResult.setSpan(mPrefixColorSpan, mIndex, mIndex + prefix.length, 0);
            return mResult;
        } else {
            return text;
        }
    }

    /**
     * Finds the index of the first word that starts with the given prefix. If
     * not found, returns -1.
     *
     * @param text   the text in which to search for the prefix
     * @param prefix the text to find, in upper case letters
     */
    private int indexOfWordPrefix(final CharSequence text, final char[] prefix) {
        if (TextUtils.isEmpty(text) || prefix == null) {
            return -1;
        }

        final int mTextLength = text.length();
        final int mPrefixLength = prefix.length;

        if (mPrefixLength == 0 || mTextLength < mPrefixLength) {
            return -1;
        }

        int i = 0;
        while (i < mTextLength) {
            /* Skip non-word characters */
            while (i < mTextLength && !Character.isLetterOrDigit(text.charAt(i))) {
                i++;
            }

            if (i + mPrefixLength > mTextLength) {
                return -1;
            }

            /* Compare the prefixes */
            int j;
            for (j = 0; j < mPrefixLength; j++) {
                if (Character.toUpperCase(text.charAt(i + j)) != prefix[j]) {
                    break;
                }
            }
            if (j == mPrefixLength) {
                return i;
            }

            /* Skip this word */
            while (i < mTextLength && Character.isLetterOrDigit(text.charAt(i))) {
                i++;
            }
        }
        return -1;
    }

}